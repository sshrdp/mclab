aspect sparsity

properties
% this aspect catches every set and records data that should be useful in
% determining which variables can safely be declared as sparse.
% to that effect the sparsity of every variable during the run of the
% program is checked, as well as how often the size of the array and the
% sparsity changes. the standard deviation on the sparsity is checked as
% well. also tracks sizes of variables (and stdev).
% these values are tracked for all variables over the run of the whole
% program, for all sets and gets


variables = struct();   % creates the mapping 'variable' -> index

sizeSum = {};           % the sum of size of variables
sizeSumSquared = {};    % the sum size of variables squared - to calculate stdev
sparsitySum = {};       % the sum of sparsity
sparsitySumSquared = {};% the sum of the sparsity squared - to calculate stdev 


changeShape = {};       % how often the dimensions of the array changed (has to exist previously)
decreaseSparsity = {};  % how often the sparsity decreased (i.e. a previously nonzero element was set)
increaseSparsity = {};  % how often the sparsity increased

arraySet  = {};             % the number of 'set' operations
arrayGet = {};          % how often the whole array is retrieved
arrayIndexedGet = {};     % how often the array is indexed into

nextId = 1;             % next available index
end


methods
function b = sameShape(this,a,b)
% returns true if a and b have the same shape
if (ndims(a) ~= ndims(b))
  b = false;
elseif (size(a) == size(b))
  b = true;
else
  b = false;
end
end

function s = stdev(this,sum,sumSquared,N)
  mean = sum/N;
  s = sqrt(sumSquared/N - mean^2);
  if (sumSquared/N < mean^2) % make numerical errors not report imaginary results
    s = 0;
  end;
end

function id = getVarId(this,var) 
% get id of variable by string-name, update 'variables' if necessary

  % find id of variable and put it in the variables structure if not present
  if (~isfield(this.variables,var))
    this.variables = setfield(this.variables,var,this.nextId);
    id = this.nextId;
    this.nextId = this.nextId+1;
    % initialze entry <id> for all the cell arays
    this.sizeSum{id} = 0;           % the sum of size of variables
    this.sizeSumSquared{id} = 0;    % the sum size of variables squared - to calculate stdev
    this.sparsitySum{id} = 0;       % the sum of sparsity
    this.sparsitySumSquared{id} = 0;% the sum of the sparsity squared - to calculate stdev 
    this.arraySet {id} = 0;             % the number of 'set' operations
    this.changeShape{id} = 0;       % how often the dimensions of the array changed (has to exist previously)
    this.decreaseSparsity{id} = 0;  % how often the sparsity decreased (i.e. a previously nonzero element was set)
    this.increaseSparsity{id} = 0;  % how often the sparsity increased
    this.arrayGet{id} = 0;          % how often the whole array is retrieved
    this.arrayIndexedGet{id} = 0;     % how often the array is indexed into
  else
    id = getfield(this.variables,var); 
  end
end


% returns sparsity
function s = getSparsity(this,val)
  if (numel(val) == 0)
    s = 1;
  else
    s = nnz(val)/numel(val);
  end
end


% given some matrix and a var id, updates the size, sparsity fields
function s = touch(this,id,value)
  sp = this.getSparsity(value);
  newSize = numel(value);
  this.sizeSum{id} = this.sizeSum{id}+newSize; % add new size
  this.sizeSumSquared{id} = this.sizeSumSquared{id}+newSize^2; % add new size squared
  this.sparsitySum{id} = this.sparsitySum{id}+sp; % add to the sum of sparsity
  this.sparsitySumSquared{id} = this.sparsitySumSquared{id}+sp^2; % add sum squared
end

end




patterns
arraySet : set(*, *.*);
arrayWholeGet : get(*());
arrayIndexedGet : get(*(..));
exec : execution(program);
end

actions
message : before exec
 disp('tracking sparsities of all variables in the following program...');
end


displayResults : after exec
% will display the results
  vars = fieldnames(this.variables);
  result = {'var','size','sparsity','arraySet','shape changes','decrease sparsity','increase sparsity','get','indexed get'};
  pm = [' ', char(0177)];
  for i=1:length(vars) %iterate over variables
     result{i+1,1} = vars{i};
     touch = this.arraySet{i}+this.arrayGet{i}+this.arrayIndexedGet{i}; % total number of acesses
     result{i+1,2} = strcat(num2str(this.sizeSum{i}/touch,'%.1f'),pm,num2str(this.stdev(this.sizeSum{i},this.sizeSumSquared{i},touch),'%.1f'));
     result{i+1,3} = strcat(num2str(this.sparsitySum{i}/touch,'%1.2f'),pm,num2str(this.stdev(this.sparsitySum{i},this.sparsitySumSquared{i},touch),'%1.2f'));
     result{i+1,4} = this.arraySet{i};
     result{i+1,5} = this.changeShape{i};
     result{i+1,6} = this.decreaseSparsity{i};
     result{i+1,7} = this.increaseSparsity{i};
     result{i+1,8} = this.arrayGet{i};
     result{i+1,9} = this.arrayIndexedGet{i};
  end
  disp(result);
end


set : before arraySet : (newVal,obj,name)
  % we will exit if the newval is not a matrix
  if (~isnumeric(newVal))
    return;
  end;
  
  % get id of variable by string-name, update 'variables' if necessary
  id = this.getVarId(name);

  % get var infor
  newSize = numel(newVal);
  sparsity = this.getSparsity(newVal);
  oldSparsity = this.getSparsity(obj);

  % update the number of 'set' operations
  this.arraySet{id}  = this.arraySet{id}+1; 

  % update tracking info
  this.touch(id,newVal);

  % set shape/sparsity changes
  if (~this.sameShape(newVal,obj))
    this.changeShape{id} = this.changeShape{id}+1; % how often the dimensions of the array changed (has to exist previously)
  end
  if (sparsity < oldSparsity)
    this.decreaseSparsity{id} = this.decreaseSparsity{id}+1; % how often the sparsity decreased
  end;
  if (sparsity > oldSparsity)
    this.increaseSparsity{id} = this.increaseSparsity{id}+1;  % how often the sparsity increased
  end
end


get : before arrayWholeGet : (obj,name)
  % we will exit if the value is not a matrix
  if (~isnumeric(obj))
    return;
  end;
  id = this.getVarId(name); % get id of variable by string-name, update 'variables' if necessary
  this.touch(id,obj);
  this.arrayGet{id} = this.arrayGet{id}+1;
end

indexedGet : before arrayIndexedGet : (obj,name)
  % we will exit if the value is not a matrix
  if (~isnumeric(obj))
    return;
  end;
  id = this.getVarId(name); % get id of variable by string-name, update 'variables' if necessary
  this.touch(id,obj);
  this.arrayIndexedGet{id} = this.arrayIndexedGet{id}+1;
end
end


end