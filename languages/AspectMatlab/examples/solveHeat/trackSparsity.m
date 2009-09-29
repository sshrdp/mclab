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
elementSets = {};       % the number of set operations considering all elements
changeShape = {};       % how often the dimensions of the array changed (has to exist previously)
decreaseSparsity = {};  % how often the sparsity decreased (i.e. a previously nonzero element was set)
increaseSparsity = {};  % how often the sparsity increased
end





patterns
arrayset : set(*, *.*);
exec : execution(program);
end

actions
%message : before exec
% disp('tracking');% sparsities of all variables in the following program');
% - strings don't work!
%end


displayResults : after exec
 disp('stuff');
end



set : before arrayset : (newVal,dims,obj,name)
% we will retname the context variables in case they get changed
dims = indizes; 
var = name;
oldVal = obj;

% find name of variable (with scope) and put it in 


% set number of 'set' operations

% check whether variable existed before

% check whether sparsity changes

% collect size, size squared

% collect sparsity, sparsity squared


end
end


end