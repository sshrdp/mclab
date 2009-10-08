aspect trackSparsity

properties
% this aspect catches every set and records data that should be useful in
% determining which variables can safely be declared as sparse.
% to that effect the sparsity of every variable during the run of the
% program is checked, as well as how often the size of the array and the
% sparsity changes. the standard deviation on the sparsity is checked as
% well. also track sizes of variables (and stdev)
% these values are tracked for all variables over the run of the whole
% program


variables = struct();   % creates the mapping 'variable' -> index
sizeSum = {};           % the sum of size of variables
sizeSumSquared = {};    % the sum size of variables squared - to calculate stdev
sparsitySum = {};       % the sum of sparsity
sparsitySumSquared = {};% the sum of the sparsity squared - to calculate stdev 
sets  = {};             % the number of 'set' operations
changeShape = {};       % how often the dimensions of the array changed (has to exist previously)
decreaseSparsity = {};  % how often the sparsity decreased (i.e. a previously nonzero element was set)
increaseSparsity = {};  % how often the sparsity increased
arrayGet = {};          % how often the whole array is retrieved
arrayIndexing = {};     % how often the array is indexed into
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
    this.sets {id} = 0;             % the number of 'set' operations
    this.changeShape{id} = 0;       % how often the dimensions of the array changed (has to exist previously)
    this.decreaseSparsity{id} = 0;  % how often the sparsity decreased (i.e. a previously nonzero element was set)
    this.increaseSparsity{id} = 0;  % how often the sparsity increased
    this.arrayGet{id} = 0;          % how often the whole array is retrieved
    this.arrayIndexing{id} = 0;     % how often the array is indexed into
  else
    id = getfield(this.variables,var); 
  end
end

end




patterns
arrayset : set(*, *.*);
arrayget : get(*);

arrayIndizedGet : get(*(..));
arrayWholeGet : get(*());

exec : execution(program);
end

actions
message : before exec
 disp('tracking sparsities of all variables in the following program');
end


displayResults : after exec
% will display the results
  vars = fieldnames(this.variables);
  result = {'var','size','sparsity','sets','shape changes','decrease sparsity','increase sparsity','get','indexed get'};
  pm = '+-';
  for i=1:length(vars) %iterate over variables
     result{i+1,1} = vars{i};
     result{i+1,2} = strcat(num2str(this.sizeSum{i}/this.sets{i}),pm,num2str(this.stdev(this.sizeSum{i},this.sizeSumSquared{i},this.sets{i})));
     result{i+1,3} = strcat(num2str(this.sparsitySum{i}/this.sets{i}),pm,num2str(this.stdev(this.sparsitySum{i},this.sparsitySumSquared{i},this.sets{i})));
     result{i+1,4} = this.sets{i};
     result{i+1,5} = this.changeShape{i};
     result{i+1,6} = this.decreaseSparsity{i};
     result{i+1,7} = this.increaseSparsity{i};
     result{i+1,8} = this.arrayGet{i};
     result{i+1,9} = this.arrayIndexing{i};
  end
  disp(result);
end


set : before arrayset : (newVal,obj,name)
  % we will exit if the newval is not a matrix
  if (~isnumeric(newVal)) 
    return
  end;

  % we will rename the context variables in case they get changed
  var = name;
  old = obj; % TODO -- what if previously didn't exist?
  new = newVal; % should be the resulting new array 
  newSize = numel(new);
  sparsity = nnz(new)/numel(new); % calculate sparsity of new variable - % of 
  oldSparsity = nnz(old)/numel(old);
  id = this.getVarId(var); % get id of variable by string-name, update 'variables' if necessary

  % set all the above variables
  this.sizeSum{id} = this.sizeSum{id}+newSize; % add new size
  this.sizeSumSquared{id} = this.sizeSumSquared{id}+newSize^2; % add new size squared
  this.sparsitySum{id} = this.sparsitySum{id}+sparsity; % add to the sum of sparsity
  this.sparsitySumSquared{id} = this.sparsitySumSquared{id}+sparsity^2; % add sum squared
  this.sets{id}  = this.sets{id}+1; % the number of 'set' operations
  if (~this.sameShape(new,old))
    this.changeShape{id} = this.changeShape{id}+1; % how often the dimensions of the array changed (has to exist previously)
  end
  if (sparsity < oldSparsity)
    this.decreaseSparsity{id} = this.decreaseSparsity{id}+1; % how often the sparsity decreased
  end;
  if (sparsity > oldSparsity)
    this.increaseSparsity{id} = this.increaseSparsity{id}+1;  % how often the sparsity increased
  end
end


get : before arrayget : (args,name)
  id = this.getVarId(this,name); % get id of variable by string-name, update 'variables' if necessary
  if (length(args) == 0)
    this.arrayGet{id} = this.arrayGet{id}+1;
  else
    this.arrayIndexing{id} = this.arrayIndexing{id}+1;
  end
end


end
end