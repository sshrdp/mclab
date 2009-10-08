classdef unit < handle
  properties 
    noUnit = [0, 0, 0, 0, 0, 0, 0];
    annotated = 'aspect_annotated';
    one = struct('aspect_annotated', true, 'val', 1, 'unit', [0, 0, 0, 0, 0, 0, 0]);
    units = struct('m', [1, 0, 0, 0, 0, 0, 0], 'Kg', [0, 1, 0, 0, 0, 0, 0], 's', [0, 0, 1, 0, 0, 0, 0], 'A', [0, 0, 0, 1, 0, 0, 0], 'K', [0, 0, 0, 0, 1, 0, 0], 'cd', [0, 0, 0, 0, 0, 1, 0], 'mol', [0, 0, 0, 0, 0, 0, 1], 'J', [2, 1, (-2), 0, 0, 0, 0], 'N', [1, 1, (-2), 0, 0, 0, 0]);
    constants = struct('km', {[1, 0, 0, 0, 0, 0, 0], 1000}, 'year', {[0, 0, 1, 0, 0, 0, 0], 31556926}, 'lb', {[0, 1, 0, 0, 0, 0, 0], 0.45359237}, 'inches', {[1, 0, 0, 0, 0, 0, 0], 0.0254}, 'feet', {[1, 0, 0, 0, 0, 0, 0], 0.3048}, 'G', {[3, (-1), (-2), 0, 0, 0, 0], 6.6730e-11}, 'dozen', {[0, 0, 0, 0, 0, 0, 0], 12}, 'AU', {[1, 0, 0, 0, 0, 0, 0], (149598000 * 1000)}, 'c', {[1, 0, (-1), 0, 0, 0, 0], 299792458}, 'KJ', {[2, 1, (-2), 0, 0, 0, 0], 1000}, 'g', {[0, 1, 0, 0, 0, 0, 0], 0.001}, 'L', {[3, 0, 0, 0, 0, 0, 0], 0.001}, 'kilotons', {[0, 1, 0, 0, 0, 0, 0], (1000 * 1000)}, 'm_earth', {[0, 1, 0, 0, 0, 0, 0], 5.9742e24}, 'r_earth', {[1, 0, 0, 0, 0, 0, 0], 6378100});
  end
  methods 
    function  [s] = annotate(this, x)
      if (isstruct(x) && isfield(x, this.annotated))
% allows adding units
% units are SI and SI derived units
% the unit is denoted by a vector 
% ====[metre, kg, second, Ampere, Kelvin, candela, mol]======
% all acesses to functions denoted by units are overriden
% all operations are overriden
% indexing gets overriden
% uses structs using the aspect_annoted flag
... % defines all SI and SI derived unit names and value (may be used for printing as well)
...
...
...
...
...
...
...
...
... % defines constants or units whose factor (compared to SI units) are not 1
...
...
...
...
...
...
...
...
...
...
...
...
...
...
% takes in a value and returns value that is unit-annotated for sure
% if it is annotated already, the same unit is returned
        s = x;
      else 
        s = struct(this.annotated, true, 'val', x, 'unit', this.noUnit);
      end
    end
    function  [a, b, c] = prepareOp(this, args)
      if (length(args) ~= 2)
% prepares input args a,b and output arg c for binary operation -- this
% is just common code put in a separate function
        error(strcat('binary operation needs exactly 2 arguments'));
      end
      a = this.annotate(args{1});
      b = this.annotate(args{2});
      c = this.one;
    end
    function  [] = display(this, v)
      if (isstruct(v) && isfield(v, this.annotated))
% displays a unit on screen
        fprintf('%s:', this.unitString(v.unit));
        disp(v.val);
      else 
        disp(v);
      end
    end
    function  [s] = unitString(this, v)
% returns the unit string of a given unit vector
% this is done greedily/recursively by picking the unit that most reduces the 1-norm
% of the unit vector.
      s = '';
      if (v == this.noUnit)
        return;
      end
      names = fieldnames(this.units);
      print = zeros(length(names), 1);
      while (~same(v, (0 * v)))
% this loop picks the unit that most reduces the 1-norm of v, and adds it to 'print' until v is 0
        newPNorm = (print * 0);
        newMNorm = (print * 0);
        for i = (1 : length(names))
% get vector for unit i
          u = getfield(this.units, names{i});
% see how much that unit reduces the unit vector v
          newPNorm(i) = norm((v - u), 1);
% same but with inverted unit
          newMNorm(i) = norm((v + u), 1);
        end
        [minPNorm, minPi] = min(newPNorm);
        [minMNorm, minMi] = min(newMNorm);
        if (minPNorm < minMNorm)
% put the found unit into print vector
% positive unit (unit^1)
          print(minPi) = (print(minPi) + 1);
          u = (-getfield(this.units, names{minPi}));
        else 
% negativ unit (unit^-1)
          print(minMi) = (print(minMi) - 1);
          u = getfield(this.units, names{minMi});
        end
        v = (v + u);
      end
      for i = (1 : length(print))
        if (print(i) ~= 0)
% put whatever is in the print vector into a string
          s = strcat(s, strcat('*', names{i}));
          if (print(i) ~= 1)
            s = strcat(s, strcat('^', num2str(print(i))));
          end
        end
      end
% we know there must be one unit - replace leading '*'
      s = s((2 : length(s)));
    end
  end
  methods 
    function  [varargout] = unit_loop(this, newVal, AM_caseNum, AM_obj, AM_args)
% captures all loop invocations for i = range, and overwrites the
% expression to be a struct-array instead of a structure with an array inside
      range = this.annotate(newVal);
% loop through range.val, and record whatever the for loop captures in a cell array
      acell = {};
      for i = range.val
        acell{(length(acell) + 1)} = i;
      end
      varargout{1} = struct(this.annotated, true, 'val', acell, 'unit', range.unit);
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_args{1};
        end
      end
    end
    function  [varargout] = unit_acalls(this, name, AM_caseNum, AM_obj, AM_args)
      if isfield(this.units, name)
% captures all calls and checks whether they are a nuit - if so, return the unit
% this advice is first so that it gets matched last
        varargout{1} = struct(this.annotated, true, 'val', 1, 'unit', getfield(this.units, name));
      else 
        if isfield(this.constants, name)
          pair = getfield(this.constants, name);
...
          varargout{1} = struct(this.annotated, true, 'val', getfield(this.constants, {2}, name), 'unit', getfield(this.constants, {1}, name));
        else 
          proceed(AM_caseNum, AM_obj, AM_args);
        end
      end
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj();
          case 1
            varargout{1} = AM_obj();
          case 2
            varargout{1} = AM_obj();
          case 3
            varargout{1} = AM_obj();
          case 4
            varargout{1} = AM_obj();
          case 5
            varargout{1} = AM_obj();
          case 6
            varargout{1} = AM_obj();
          case 7
            varargout{1} = AM_obj();
          case 8
            varargout{1} = AM_obj();
          case 9
            varargout{1} = AM_obj();
          case 10
            varargout{1} = AM_obj();
          case 11
            varargout{1} = AM_obj();
          case 12
            varargout{1} = AM_obj();
          case 13
            varargout{1} = AM_obj();
          case 14
            varargout{1} = AM_obj();
          case 15
            varargout{1} = AM_obj();
          case 16
            varargout{1} = AM_obj();
          case 17
            varargout{1} = AM_obj();
          case 18
            varargout{1} = AM_obj();
          case 19
            varargout{1} = AM_obj();
          case 20
            varargout{1} = AM_obj();
          case 21
            varargout{1} = AM_obj();
          case 22
            varargout{1} = AM_obj();
          case 23
            varargout{1} = AM_obj();
          case 24
            varargout{1} = AM_obj();
        end
      end
    end
    function  [varargout] = unit_adisp(this, args, AM_caseNum, AM_obj, AM_args)
      if (length(args) ~= 1)
% overrdes printing so that we add units
        error('Error using disp -- need exactly one argument)');
      end
      v = args{1};
      if (isstruct(v) && isfield(v, this.annotated))
        this.display(v);
      else 
        disp(v);
      end
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            AM_obj(AM_args{1});
          case 1
            AM_obj(AM_args{1});
          case 2
            AM_obj(AM_args{1});
          case 3
            AM_obj(AM_args{1});
          case 4
            AM_obj(AM_args{1});
          case 5
            AM_obj(AM_args{1});
          case 6
            AM_obj(AM_args{1});
        end
      end
    end
    function  [varargout] = unit_aplus(this, args, AM_caseNum, AM_obj, AM_args)
% +
      [a, b, c] = this.prepareOp(args);
      c.val = (a.val + b.val);
      if (a.unit ~= b.unit)
        error('the units of the arguments for operation + must match');
      end
      c.unit = a.unit;
      varargout{1} = c;
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
    function  [varargout] = unit_aminus(this, args, AM_caseNum, AM_obj, AM_args)
% -
      [a, b, c] = this.prepareOp(args);
      c.val = (a.val - b.val);
      if (a.unit ~= b.unit)
        error('the units of the arguments for operation - must match');
      end
      c.unit = a.unit;
      varargout{1} = c;
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 1
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 2
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
        end
      end
    end
    function  [varargout] = unit_amtimes(this, args, AM_caseNum, AM_obj, AM_args)
% *
      [a, b, c] = this.prepareOp(args);
      c.val = (a.val * b.val);
      c.unit = (a.unit + b.unit);
      varargout{1} = c;
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
          case 16
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 17
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 18
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 19
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 20
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 21
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 22
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 23
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
        end
      end
    end
    function  [varargout] = unit_amrdivide(this, args, AM_caseNum, AM_obj, AM_args)
% /
      [a, b, c] = this.prepareOp(args);
      c.val = (a.val / b.val);
      c.unit = (a.unit - b.unit);
      varargout{1} = c;
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
        end
      end
    end
    function  [varargout] = unit_power(this, args, AM_caseNum, AM_obj, AM_args)
% .^
      [a, b, c] = this.prepareOp(args);
      c.val = (a.val .^ b.val);
      if (b.unit ~= this.noUnit)
        error('cannot use power with a non empty unit');
      end
      if isscalar(b.val)
        c.val = (a.val .^ b.val);
        c.unit = (a.unit * b.val);
      else 
        if (a.unit ~= this.noUnit)
          error('cannot use power operation resulting mixed unit matrix');
        end
        c.unit = this.noUnit;
      end
      varargout{1} = c;
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 1
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
          case 2
            varargout{1} = AM_obj(AM_args{1}, AM_args{2});
        end
      end
    end
    function  [varargout] = unit_round(this, args, AM_caseNum, AM_obj, AM_args)
      if (length(args) ~= 1)
% round
        proceed(AM_caseNum, AM_obj, AM_args);
      end
      a = this.annotate(args{1});
      a.val = round(a.val);
      varargout{1} = a;
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1});
        end
      end
    end
    function  [varargout] = unit_colon(this, args, AM_caseNum, AM_obj, AM_args)
      if ((length(args) ~= 2) && (length(args) ~= 3))
% : :
        proceed(AM_caseNum, AM_obj, AM_args);
      end
      a = this.annotate(args{1});
      b = this.annotate(args{2});
      c = this.one;
      o = this.one;
      o.unit = a.unit;
      if (b.unit ~= a.unit)
        error('error in colon: the units need to be the same');
      end
      if (length(args) == 3)
        c = this.annotate(args{3});
        if (c.unit ~= a.unit)
          error('error in colon: the units need to be the same');
        end
        o.val = (a.val : b.val : c.val);
      else 
        o.val = (a.val : b.val);
      end
      varargout{1} = o;
      function  [] = proceed(AM_caseNum, AM_obj, AM_args)
        switch AM_caseNum
          case 0
            varargout{1} = AM_obj(AM_args{1}, AM_args{2}, AM_args{3});
        end
      end
    end
  end
end
