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
function b = sameShape(a,b)
% returns true if a and b have the same shape
if (ndims(a) ~= ndims(b))
  b = false;
elseif (size(a) == size(b))
  b = true;
else
  b = false;
end
end

function s = stdev(sum,sumSquared,N)
  mean = sum/N;
  s = sqrt(sumSquared/N - mean^2);
end

function id = getVarId(var) 
% get id of variable by string-name, update 'variables' if necessary

  % find id of variable and put it in the variables structure if not present
  if (~isfield(variables,var))
    variables = setfield(variables,var,nextId);
    id = nextId;
    nextId = nextId+1;
    % initialze entry <id> for all the cell arays
    sizeSum{id} = 0;           % the sum of size of variables
    sizeSumSquared{id} = 0;    % the sum size of variables squared - to calculate stdev
    sparsitySum{id} = 0;       % the sum of sparsity
    sparsitySumSquared{id} = 0;% the sum of the sparsity squared - to calculate stdev 
    sets {id} = 0;             % the number of 'set' operations
    changeShape{id} = 0;       % how often the dimensions of the array changed (has to exist previously)
    decreaseSparsity{id} = 0;  % how often the sparsity decreased (i.e. a previously nonzero element was set)
    increaseSparsity{id} = 0;  % how often the sparsity increased
    arrayGet{id} = 0;          % how often the whole array is retrieved
    arrayIndexing{id} = 0;     % how often the array is indexed into
  else
    id = getfield(variables,var);
  end
end

end




patterns
arrayset : set(*, *.*);
arrayget : get(*, *.*);
exec : execution(program);
end

actions
%message : before exec TODO
% disp('tracking');% sparsities of all variables in the following program');
% - strings don't work!
%end


displayResults : after exec
% will display the results
  vars = fieldnames(variables);
%  result = {'var','size','sparsity','sets','shape changes','decrease sparsity','increase sparsity'}; TODO
%  pm = '+-'; TODO
  for i=i:length(vars) %iterate over variables
     result{i+1,1} = vars{i};
     result{i+1,2} = strcat(num2str(sizeSum{i}/sets{i}),pm,num2str(stdev(sizeSum{i},sizeSumSquared{i},sets{i})));
     result{i+1,3} = strcat(num2str(sparsitySum{i}/sets{i}),pm,num2str(stdev(sparsitySum{i},sparsitySumSquared{i},sets{i})));
     result{i+1,4} = sets{i};
     result{i+1,5} = changeShape{i};
     result{i+1,6} = decreaseSparsity{i};
     result{i+1,7} = increaseSparsity{i};
  end
  disp(result);
end


set : before arrayset : (newVal,obj,name)
  % we will rename the context variables in case they get changed
  var = name;
  old = obj; % TODO -- what if previously didn't exist?
  new = newVal; % should be the resulting new array 
  newSize = numel(new);
  sparsity = nnz(new)/numel(new); % calculate sparsity of new variable - % of 
  oldSparsity = nnz(old)/numel(old);
  id = getVarId(var); % get id of variable by string-name, update 'variables' if necessary

  % set all the above variables
  sizeSum{id} = sizeSum{id}+newSize; % add new size
  sizeSumSquared{id} = sizeSumSquared{id}+newSize^2; % add new size squared
  sparsitySum{id} = sparsitySum{id}+sparsity; % add to the sum of sparsity
  sparsitySumSquared{id} = sparsitySumSquared{id}+sparsity^2; % add sum squared
  sets{id}  = sets{id}+1; % the number of 'set' operations
  if (~sameShape(new,old))
    changeShape{id} = changeShape{id}+1; % how often the dimensions of the array changed (has to exist previously)
  end
  if (sparsity < oldSparsity)
    decreaseSparsity{id} = decreaseSparsity{id}+1; % how often the sparsity decreased
  end;
  if (sparsity > oldSparsity)
    increaseSparsity{id} = increaseSparsity{id}+1;  % how often the sparsity increased
  end
end


get : before arrayget : (newVal,dims,obj,name)
  id = getVarId(var); % get id of variable by string-name, update 'variables' if necessary
  if (dims == {})
    arrayGet{id} = arrayGet{id}+1;
  else
    arrayIndexing{id} = arrayIndexing{id}+1;
  end
end


end



end