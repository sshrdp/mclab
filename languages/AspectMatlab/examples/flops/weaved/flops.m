classdef flops < handle
  properties 
    callSite = struct();
    call = [];
    flop = [];
    nextId = 1;
    s = [1, 0];
    record = false;
  end
  methods 
    function  [s] = stack(this)
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
% callsite -> id
% number of calls per call site
% flops per call site
% put sth in stack=> calls can modify the 'top' without error
% stack methods - stack(1) is the number of elements, which itself follow
      s = [0];
    end
    function  [stack] = push(this, stack, element)
      stack((stack(1) + 2)) = element;
      stack(1) = (stack(1) + 1);
    end
    function  [stack, element] = pop(this, stack)
      if (stack(1) == 0)
        error('trying to pop from empty stack');
      end
      element = stack((stack(1) + 1));
      stack(1) = (stack(1) - 1);
    end
    function  [id] = getId(this, name, line, op)
% given a scope (function) name (location), line number and operation, returns the
% associated index in calls and flops
      location = strcat(name, '_', num2str(line), '_', op);
      if (~isfield(this.callSite, location))
        this.callSite = setfield(this.callSite, location, this.nextId);
        this.flop(this.nextId) = 0;
        this.call(this.nextId) = 0;
        id = this.nextId;
        this.nextId = (this.nextId + 1);
      else 
        id = getfield(this.callSite, location);
      end
    end
  end
  methods 
    function  [] = flops_bany(this)
      if (~this.record)
% before any call - take care of loops on stack (if recording)
% this gets called after the beforeTrack advice, so that the tracked call can
% report information
% return if we are not recording
        return;
      end
      this.s = this.push(this.s, 0);
    end
    function  [] = flops_beforeTrack(this, name)
      fprintf('encountered call to %s, recording flops...\n', name);
% callsite -> id
      this.callSite = struct();
% number of calls per call site
      this.call = [];
% flops per call site
      this.flop = [];
      this.nextId = 1;
      this.s = this.stack();
      this.record = true;
    end
    function  [] = flops_aany(this, name, line, loc)
      if (~this.record)
% return if we are not recording
        return;
      end
% get flops and return stack    
      [this.s, f] = this.pop(this.s);
      id = this.getId(loc, line, name);
      this.call(id) = (this.call(id) + 1);
      this.flop(id) = (this.flop(id) + f);
      if (this.s(1) ~= 0)
% if the stack isn't empty, put all those flops on the previous frame    
        [this.s, fold] = this.pop(this.s);
        this.s = this.push(this.s, (f + fold));
      end
    end
    function  [] = flops_afterTrack(this)
% print info
      fprintf('finished tracking function call, here are the results:\n');
      fields = fieldnames(this.callSite);
      result = {'call site', '# of calls', 'total flops'};
      format('long');
      for i = (1 : numel(fields))
        field = fields{i};
        id = getfield(this.callSite, field);
        result{(i + 1), 1} = field;
        result{(i + 1), 2} = this.call(id);
        result{(i + 1), 3} = this.flop(id);
      end
      disp(result);
% put something in the stack so that calls can modify the 'top' without error
      this.s = this.push(this.stack(), 0);
      this.record = false;
    end
    function  [varargout] = flops_amtimes(this, args, AM_caseNum, AM_obj, AM_args)
% first perform call
      proceed(AM_caseNum, AM_obj, AM_args);
      f = ((((2 * size(args{1}, 2)) - 1) * size(args{1}, 1)) * size(args{2}, 2));
      [this.s, fold] = this.pop(this.s);
      this.s = this.push(this.s, (f + fold));
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 1
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 2
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 3
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 4
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 5
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 6
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 7
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 8
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 9
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 10
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 11
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 12
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 13
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 14
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 15
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
        end
      end
    end
    function  [varargout] = flops_aminus(this, args, AM_caseNum, AM_obj, AM_args)
% first perform call
      proceed(AM_caseNum, AM_obj, AM_args);
      f = max(numel(args{1}), numel(args{2}));
      [this.s, fold] = this.pop(this.s);
      this.s = this.push(this.s, (f + fold));
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 1
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
        end
      end
    end
    function  [varargout] = flops_atimes(this, args, AM_caseNum, AM_obj, AM_args)
% first perform call
      proceed(AM_caseNum, AM_obj, AM_args);
      f = max(numel(args{1}), numel(args{2}));
      [this.s, fold] = this.pop(this.s);
      this.s = this.push(this.s, (f + fold));
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 1
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 2
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 3
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 4
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 5
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 6
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
        end
      end
    end
    function  [varargout] = flops_aplus(this, args, AM_caseNum, AM_obj, AM_args)
% first perform call
      proceed(AM_caseNum, AM_obj, AM_args);
      f = max(numel(args{1}), numel(args{2}));
      [this.s, fold] = this.pop(this.s);
      this.s = this.push(this.s, (f + fold));
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 1
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 2
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 3
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
        end
      end
    end
    function  [varargout] = flops_ardivide(this, args, AM_caseNum, AM_obj, AM_args)
% first perform call
      proceed(AM_caseNum, AM_obj, AM_args);
      f = max(numel(args{1}), numel(args{2}));
      [this.s, fold] = this.pop(this.s);
      this.s = this.push(this.s, (f + fold));
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 1
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 2
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 3
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 4
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
        end
      end
    end
    function  [varargout] = flops_asqrt(this, args, AM_caseNum, AM_obj, AM_args)
% first perform call
      proceed(AM_caseNum, AM_obj, AM_args);
      f = numel(args{1});
      [this.s, fold] = this.pop(this.s);
      this.s = this.push(this.s, (f + fold));
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1});
          case 1
            varargout{1} = AM_obj(AM_args{1});
          case 2
            varargout{1} = AM_obj(AM_args{1});
          case 3
            varargout{1} = AM_obj(AM_args{1});
        end
      end
    end
    function  [varargout] = flops_aabs(this, args, AM_caseNum, AM_obj, AM_args)
% first perform call
      proceed(AM_caseNum, AM_obj, AM_args);
      f = numel(args{1});
      [this.s, fold] = this.pop(this.s);
      this.s = this.push(this.s, (f + fold));
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1});
          case 1
            varargout{1} = AM_obj(AM_args{1});
          case 2
            varargout{1} = AM_obj(AM_args{1});
        end
      end
    end
  end
end
