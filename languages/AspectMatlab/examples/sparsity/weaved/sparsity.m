classdef sparsity < handle
  properties 
    variables = struct();
    sizeSum = {};
    sizeSumSquared = {};
    sparsitySum = {};
    sparsitySumSquared = {};
    changeShape = {};
    decreaseSparsity = {};
    increaseSparsity = {};
    arraySet = {};
    arrayGet = {};
    arrayIndexedGet = {};
    nextId = 1;
  end
  methods 
    function  [b] = sameShape(this, a, b)
      if (ndims(a) ~= ndims(b))
% this aspect catches every set and records data that should be useful in
% determining which variables can safely be declared as sparse.
% to that effect the sparsity of every variable during the run of the
% program is checked, as well as how often the size of the array and the
% sparsity changes. the standard deviation on the sparsity is checked as
% well. also tracks sizes of variables (and stdev).
% these values are tracked for all variables over the run of the whole
% program, for all sets and gets
% creates the mapping 'variable' -> index
% the sum of size of variables
% the sum size of variables squared - to calculate stdev
% the sum of sparsity
% the sum of the sparsity squared - to calculate stdev 
% how often the dimensions of the array changed (has to exist previously)
% how often the sparsity decreased (i.e. a previously nonzero element was set)
% how often the sparsity increased
% the number of 'set' operations
% how often the whole array is retrieved
% how often the array is indexed into
% next available index
% returns true if a and b have the same shape
        b = false;
      elseif (size(a) == size(b))
        b = true;
      else 
        b = false;
      end
    end
    function  [s] = stdev(this, sum, sumSquared, N)
      mean = (sum / N);
      s = sqrt(((sumSquared / N) - (mean ^ 2)));
      if ((sumSquared / N) < (mean ^ 2))
% make numerical errors not report imaginary results
        s = 0;
      end
    end
    function  [id] = getVarId(this, var)
      if (~isfield(this.variables, var))
% get id of variable by string-name, update 'variables' if necessary
% find id of variable and put it in the variables structure if not present
        this.variables = setfield(this.variables, var, this.nextId);
        id = this.nextId;
        this.nextId = (this.nextId + 1);
% initialze entry <id> for all the cell arays
% the sum of size of variables
        this.sizeSum{id} = 0;
% the sum size of variables squared - to calculate stdev
        this.sizeSumSquared{id} = 0;
% the sum of sparsity
        this.sparsitySum{id} = 0;
% the sum of the sparsity squared - to calculate stdev 
        this.sparsitySumSquared{id} = 0;
% the number of 'set' operations
        this.arraySet{id} = 0;
% how often the dimensions of the array changed (has to exist previously)
        this.changeShape{id} = 0;
% how often the sparsity decreased (i.e. a previously nonzero element was set)
        this.decreaseSparsity{id} = 0;
% how often the sparsity increased
        this.increaseSparsity{id} = 0;
% how often the whole array is retrieved
        this.arrayGet{id} = 0;
% how often the array is indexed into
        this.arrayIndexedGet{id} = 0;
      else 
        id = getfield(this.variables, var);
      end
    end
    function  [s] = getSparsity(this, val)
      if (numel(val) == 0)
% returns sparsity
        s = 1;
      else 
        s = (nnz(val) / numel(val));
      end
    end
    function  [s] = touch(this, id, value)
% given some matrix and a var id, updates the size, sparsity fields
      sp = this.getSparsity(value);
      newSize = numel(value);
% add new size
      this.sizeSum{id} = (this.sizeSum{id} + newSize);
% add new size squared
      this.sizeSumSquared{id} = (this.sizeSumSquared{id} + (newSize ^ 2));
% add to the sum of sparsity
      this.sparsitySum{id} = (this.sparsitySum{id} + sp);
% add sum squared
      this.sparsitySumSquared{id} = (this.sparsitySumSquared{id} + (sp ^ 2));
    end
  end
  methods 
    function  [] = sparsity_message(this)
      disp('tracking sparsities of all variables in the following program...');
    end
    function  [] = sparsity_displayResults(this)
% will display the results
      vars = fieldnames(this.variables);
      result = {'var', 'size', 'sparsity', 'arraySet', 'shape changes', 'decrease sparsity', 'increase sparsity', 'get', 'indexed get'};
      pm = [' ', char(0177)];
      for i = (1 : length(vars))
%iterate over variables
        result{(i + 1), 1} = vars{i};
% total number of acesses
        touch = ((this.arraySet{i} + this.arrayGet{i}) + this.arrayIndexedGet{i});
        result{(i + 1), 2} = strcat(num2str((this.sizeSum{i} / touch), '%.1f'), pm, num2str(this.stdev(this.sizeSum{i}, this.sizeSumSquared{i}, touch), '%.1f'));
        result{(i + 1), 3} = strcat(num2str((this.sparsitySum{i} / touch), '%1.2f'), pm, num2str(this.stdev(this.sparsitySum{i}, this.sparsitySumSquared{i}, touch), '%1.2f'));
        result{(i + 1), 4} = this.arraySet{i};
        result{(i + 1), 5} = this.changeShape{i};
        result{(i + 1), 6} = this.decreaseSparsity{i};
        result{(i + 1), 7} = this.increaseSparsity{i};
        result{(i + 1), 8} = this.arrayGet{i};
        result{(i + 1), 9} = this.arrayIndexedGet{i};
      end
      disp(result);
    end
    function  [] = sparsity_set(this, newVal, obj, name)
      if (~isnumeric(newVal))
% we will exit if the newval is not a matrix
        return;
      end
% get id of variable by string-name, update 'variables' if necessary
      id = this.getVarId(name);
% get var infor
      newSize = numel(newVal);
      sparsity = this.getSparsity(newVal);
      oldSparsity = this.getSparsity(obj);
% update the number of 'set' operations
      this.arraySet{id} = (this.arraySet{id} + 1);
% update tracking info
      this.touch(id, newVal);
      if (~this.sameShape(newVal, obj))
% set shape/sparsity changes
% how often the dimensions of the array changed (has to exist previously)
        this.changeShape{id} = (this.changeShape{id} + 1);
      end
      if (sparsity < oldSparsity)
% how often the sparsity decreased
        this.decreaseSparsity{id} = (this.decreaseSparsity{id} + 1);
      end
      if (sparsity > oldSparsity)
% how often the sparsity increased
        this.increaseSparsity{id} = (this.increaseSparsity{id} + 1);
      end
    end
    function  [] = sparsity_get(this, obj, name)
      if (~isnumeric(obj))
% we will exit if the value is not a matrix
        return;
      end
% get id of variable by string-name, update 'variables' if necessary
      id = this.getVarId(name);
      this.touch(id, obj);
      this.arrayGet{id} = (this.arrayGet{id} + 1);
    end
    function  [] = sparsity_indexedGet(this, obj, name)
      if (~isnumeric(obj))
% we will exit if the value is not a matrix
        return;
      end
% get id of variable by string-name, update 'variables' if necessary
      id = this.getVarId(name);
      this.touch(id, obj);
      this.arrayIndexedGet{id} = (this.arrayIndexedGet{id} + 1);
    end
  end
end
