aspect grow

properties
% this aspect catches every set and records data that should be useful in
% determining which operations increase or decrease the array size.
% to that effect the size of every variable during the run of the
% program is checked. In the end, the line number of the operation at 
% which the size of each array was maximum, is printed out along with the size.


variables = struct();   % creates the mapping 'variable' -> index

changeShape = {};       % how often the dimensions of the array changed (has to exist previously)
decreaseSize = {};  % how often the size decreased (i.e. a previously nonzero element was set)
increaseSize = {};  % how often the size increased

arraySize = {};     % size of the arrayexec : execution(program);
maxSize = {};       % maximum size of the array
lineNum = {};       % at line number
arraySet  = {};     % the number of 'set' operations

nextId = 1;         % next available index
end%properties


methods
function b = sameShape(this,a,b)
% returns true if a and b have the same shape
if (ndims(a) ~= ndims(b))
  b = false;
elseif (size(a) == size(b))
  b = true;
else
  b = false;
end%if
end%sameShape

function id = getVarId(this,var,line) 
% get id of variable by string-name, update 'variables' if necessary

  % find id of variable and put it in the variables structure if not present
  if (~isfield(this.variables,var))
    this.variables = setfield(this.variables,var,this.nextId);
    id = this.nextId;
    this.nextId = this.nextId+1;
    % initialze entry <id> for all the cell arays
    this.arraySet {id} = 0;             % the number of 'set' operations
    this.changeShape{id} = 0;       % how often the dimensions of the array changed (has to exist previously)
    this.decreaseSize{id} = 0;  % how often the size decreased (i.e. a previously nonzero element was set)
    this.increaseSize{id} = 0;  % how often the size increased
    this.arraySize{id} = 0;
    this.maxSize{id} = 0;
    this.lineNum{id} = line;
  else
    id = getfield(this.variables,var); 
  end%if
end%getVarID

end%methods

patterns
arraySet : set(*);
exec : execution(program);
end%patterns

actions
message : before exec
 disp('tracking the operations that grow arrays in the following program...');
 end%actions


displayResults : after exec
% will display the results
  vars = fieldnames(this.variables);
  result = {'var','arraySet','shape changes','decrease','increase','max size','line#'};
  pm = [' ', char(0177)];
  for i=1:length(vars) %iterate over variables
     result{i+1,1} = vars{i};
     result{i+1,2} = this.arraySet{i};
     result{i+1,3} = this.changeShape{i};
     result{i+1,4} = this.decreaseSize{i};
     result{i+1,5} = this.increaseSize{i};
     result{i+1,6} = this.maxSize{i};
     result{i+1,7} = this.lineNum{i};
  end%for
  disp(result);
  end%displayResults


set : before arraySet : (newVal,obj,name,line,args)
   t = obj;
   t(args{1:numel(args)}) = newVal;
   newVal = t;
   
  % we will exit if the newval is not a matrix
  if (~isnumeric(newVal))
    return;
  end;
  
  % get id of variable by string-name, update 'variables' if necessary
  id = this.getVarId(name,line);

  % get var infor
  newSize = numel(newVal);
  oldSize = this.arraySize{id};

  this.arraySize{id} = newSize;
  
  % update the number of 'set' operations
  this.arraySet{id}  = this.arraySet{id}+1; 

  % set shape/sparsity changes
  if (~this.sameShape(newVal,obj))
    this.changeShape{id} = this.changeShape{id}+1; % how often the dimensions of the array changed (has to exist previously)
  end
  if (newSize < oldSize)
    this.decreaseSize{id} = this.decreaseSize{id}+1; % how often the size decreased
  end;
  if (newSize > oldSize)
    this.increaseSize{id} = this.increaseSize{id}+1;  % how often the size increased
    this.lineNum{id} = line;
    this.maxSize{id} = newSize;
  end
  end%set
  end%actions
  end%grow
