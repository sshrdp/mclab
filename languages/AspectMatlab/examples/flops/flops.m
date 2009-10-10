aspect flops
% catches 
% mul, plus, minus, mtimes, time, plus, sqrt, rdivide, abs
%
% and records the number of flops
% - in total
% - for every call
% - the number of calls
% per call site, and records the data recursively
%
% uses a stack
% before any call, creates a new 'stack frame' with number of flops of operation
% after any call, destroys stack frame, puts the flops of that stackframe
% on the new top, and updates call site info
%
% for builtin functions we use an around that adds the flops to the top
% of the stack, with a proceed
%
% this aspect gives detailed flops infor for every call of
%    'SVD'
% but that behaviour can be overriden by simply changing the 'tracking' pointcut


properties
callSite = struct(); % callsite -> id
call = []; % number of calls per call site
flop = []; % flops per call site
nextId = 1;
s = [1, 0]; % put sth in stack=> calls can modify the 'top' without error
record = false;
end

methods
  % stack methods - stack(1) is the number of elements, which itself follow
  function s=stack(this)
    s=[0];
  end
  function stack=push(this,stack,element)
    stack(stack(1)+2) = element;
    stack(1) = stack(1)+1;
  end
  function [stack,element]=pop(this,stack)
    if (stack(1) == 0)
      error('trying to pop from empty stack');
    end
    element = stack(stack(1)+1);
    stack(1) = stack(1)-1;
  end
  

  % given a scope (function) name (location), line number and operation, returns the
  % associated index in calls and flops
  function id = getId(this,name,line,op)
    location = strcat(name,'_',num2str(line),'_',op);
    if (~isfield(this.callSite,location))
      this.callSite=setfield(this.callSite,location,this.nextId);
      this.flop(this.nextId) = 0;
      this.call(this.nextId) = 0;
      id = this.nextId;
      this.nextId = this.nextId + 1;
    else
      id = getfield(this.callSite,location);
    end
  end
end


patterns
tracking: call(SVD);

pminus  : call(minus  (*,*));
pmtimes : call(mtimes (*,*));
ptimes  : call(times  (*,*));
pplus   : call(plus   (*,*));
psqrt   : call(sqrt   (*));
prdivide: call(rdivide(*,*));
pabs    : call(abs    (*));

any    : call(*);
end

actions
% before tracked call set up vars
beforeTrack : before tracking : (name)
  fprintf('encountered call to %s, recording flops...\n',name);
  this.callSite = struct(); % callsite -> id
  this.call = []; % number of calls per call site
  this.flop = []; % flops per call site
  this.nextId = 1;
  this.s = this.stack();
  this.record = true;
end

% before any call - take care of loops on stack (if recording)
% this gets called after the beforeTrack advice, so that the tracked call can
% report information
bany : before any
  if (~this.record)
    return; % return if we are not recording
  end
  this.s = this.push(this.s,0);
end

% after tracked call print out results
afterTrack : after tracking
  % print info
  fprintf('finished tracking function call, here are the results:\n');
  fields = fieldnames((this.callSite));
  result = {'call site','# of calls','total flops'};
  format('long');
  for i = 1:numel(fields);
    field = fields{i};
    id = getfield(this.callSite, field);
    result{i+1,1} = field;
    result{i+1,2} = this.call(id);
    result{i+1,3} = this.flop(id);
  end
  disp(result);
  % put something in the stack so that calls can modify the 'top' without error
  this.s = this.push(this.stack(),0);
  this.record = false;
end

% after call - store info and put flops on previous 'stack frame'
% 'aany' should get called first, because a call to the tracking function should still list
% said call with the corresponding flops information
aany : after any : (name,line,loc);
  if (~this.record)
    return; % return if we are not recording
  end
  [this.s,f] = this.pop(this.s); % get flops and return stack    
  id = this.getId(loc,line,name);
  this.call(id) = this.call(id) + 1;
  this.flop(id) = this.flop(id) + f;
  % if the stack isn't empty, put all those flops on the previous frame    
      if (this.s(1) ~= 0)
        [this.s, fold] = this.pop(this.s);
        this.s = this.push(this.s,f + fold);
      end
 end





%the operations
% we assume matrix multiplication of A:mxn, B:nxk takes (2n-1)*k*m operations
amtimes : around pmtimes : (args)
  proceed(); % first perform call
  f = (2*size(args{1},2) - 1)*size(args{1},1)*size(args{2},2);
  [this.s,fold] = this.pop(this.s);
  this.s = this.push(this.s,f+fold);
end


% binary element-wise operations
aminus : around pminus : (args)
  proceed(); % first perform call
  f = max(numel(args{1}),numel(args{2}));
  [this.s,fold] = this.pop(this.s);
  this.s = this.push(this.s,f+fold);
end
atimes : around ptimes : (args)
  proceed(); % first perform call
  f = max(numel(args{1}),numel(args{2}));
  [this.s,fold] = this.pop(this.s);
  this.s = this.push(this.s,f+fold);
end
aplus : around pplus : (args)
  proceed(); % first perform call
  f = max(numel(args{1}),numel(args{2}));
  [this.s,fold] = this.pop(this.s);
  this.s = this.push(this.s,f+fold);
end
ardivide : around prdivide : (args)
  proceed(); % first perform call
  f = max(numel(args{1}),numel(args{2}));
  [this.s,fold] = this.pop(this.s);
  this.s = this.push(this.s,f+fold);
end

% unary element wise operations
asqrt : around psqrt : (args)
  proceed(); % first perform call
  f = (numel(args{1}));
  [this.s,fold] = this.pop(this.s);
  this.s = this.push(this.s,f+fold);
end
aabs : around pabs : (args)
  proceed(); % first perform call
  f = (numel(args{1}));
  [this.s,fold] = this.pop(this.s);
  this.s = this.push(this.s,f+fold);
end

end


end