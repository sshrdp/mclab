classdef grow < handle
  properties 
    variables = struct();
    changeShape = {};
    decreaseSize = {};
    increaseSize = {};
    arraySize = {};
    maxSize = {};
    lineNum = {};
    arraySet = {};
    nextId = 1;
  end
  methods 
    function  [b] = sameShape(this, a, b)
      if (ndims(a) ~= ndims(b))
% this aspect catches every set and records data that should be useful in
% determining which operations increase or decrease the array size.
% to that effect the size of every variable during the run of the
% program is checked. In the end, the line number of the operation at 
% which the size of each array was maximum, is printed out along with the size.
% creates the mapping 'variable' -> index
% how often the dimensions of the array changed (has to exist previously)
% how often the size decreased (i.e. a previously nonzero element was set)
% how often the size increased
% size of the array
% maximum size of the array
% at line number
% the number of 'set' operations
% next available index
% returns true if a and b have the same shape
        b = false;
      elseif (size(a) == size(b))
        b = true;
      else 
        b = false;
      end
    end
    function  [id] = getVarId(this, var, line)
      if (~isfield(this.variables, var))
% get id of variable by string-name, update 'variables' if necessary
% find id of variable and put it in the variables structure if not present
        this.variables = setfield(this.variables, var, this.nextId);
        id = this.nextId;
        this.nextId = (this.nextId + 1);
% initialze entry <id> for all the cell arays
% the number of 'set' operations
        this.arraySet{id} = 0;
% how often the dimensions of the array changed (has to exist previously)
        this.changeShape{id} = 0;
% how often the size decreased (i.e. a previously nonzero element was set)
        this.decreaseSize{id} = 0;
% how often the size increased
        this.increaseSize{id} = 0;
        this.arraySize{id} = 0;
        this.maxSize{id} = 0;
        this.lineNum{id} = line;
      else 
        id = getfield(this.variables, var);
      end
    end
  end
  methods 
    function  [] = grow_message(this)
      disp('tracking the operations that grow arrays in the following program...');
    end
    function  [] = grow_displayResults(this)
% will display the results
      vars = fieldnames(this.variables);
      result = {'var', 'arraySet', 'shape changes', 'decrease', 'increase', 'max size', 'line#'};
      pm = [' ', char(0177)];
      for i = (1 : length(vars))
%iterate over variables
        result{(i + 1), 1} = vars{i};
        result{(i + 1), 2} = this.arraySet{i};
        result{(i + 1), 3} = this.changeShape{i};
        result{(i + 1), 4} = this.decreaseSize{i};
        result{(i + 1), 5} = this.increaseSize{i};
        result{(i + 1), 6} = this.maxSize{i};
        result{(i + 1), 7} = this.lineNum{i};
      end
      disp(result);
    end
    function  [] = grow_set(this, newVal, obj, name, line, args)
      t = obj;
      t(args{(1 : numel(args))}) = newVal;
      newVal = t;
      if (~isnumeric(newVal))
% we will exit if the newval is not a matrix
        return;
      end
% get id of variable by string-name, update 'variables' if necessary
      id = this.getVarId(name, line);
% get var infor
      newSize = numel(newVal);
      oldSize = this.arraySize{id};
      this.arraySize{id} = newSize;
% update the number of 'set' operations
      this.arraySet{id} = (this.arraySet{id} + 1);
      if (~this.sameShape(newVal, obj))
% set shape/sparsity changes
% how often the dimensions of the array changed (has to exist previously)
        this.changeShape{id} = (this.changeShape{id} + 1);
      end
      if (newSize < oldSize)
% how often the size decreased
        this.decreaseSize{id} = (this.decreaseSize{id} + 1);
      end
      if (newSize > oldSize)
% how often the size increased
        this.increaseSize{id} = (this.increaseSize{id} + 1);
        this.lineNum{id} = line;
        this.maxSize{id} = newSize;
      end
    end
  end
end