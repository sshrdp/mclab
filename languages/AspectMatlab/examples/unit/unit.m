aspect unit

properties
% allows adding units
% units are SI and SI derived units
% the unit is denoted by a vector 
% ====[metre, kg, second, Ampere, Kelvin, candela, mol]======

% all acesses to functions denoted by units are overriden
% all operations are overriden
% indexing gets overriden

% uses structs using the aspect_annoted flag
noUnit = [0, 0, 0, 0, 0, 0, 0];
annotated = 'aspect_annotated';
one = struct('aspect_annotated',true,'val',1,'unit',[0, 0, 0, 0, 0, 0, 0]);
units = struct(... % defines all SI and SI derived unit names and value (may be used for printing as well)
  'm',  [1, 0, 0, 0, 0, 0, 0],...
  'Kg', [0, 1, 0, 0, 0, 0, 0],...
  's',  [0, 0, 1, 0, 0, 0, 0],...
  'A',  [0, 0, 0, 1, 0, 0, 0],...
  'K',  [0, 0, 0, 0, 1, 0, 0],...
  'cd', [0, 0, 0, 0, 0, 1, 0],...
  'mol',[0, 0, 0, 0, 0, 0, 1],...
  'J',  [2, 1,-2, 0, 0, 0, 0],...
  'N',  [1, 1,-2, 0, 0, 0, 0]);
constants = struct(... % defines constants or units whose factor (compared to SI units) are not 1
  'km',      {[1, 0, 0, 0, 0, 0, 0],1000},...
  'year',    {[0, 0, 1, 0, 0, 0, 0],31556926},...
  'lb',      {[0, 1, 0, 0, 0, 0, 0],0.45359237},...
  'inches',  {[1, 0, 0, 0, 0, 0, 0],0.0254},...
  'feet',    {[1, 0, 0, 0, 0, 0, 0],0.3048},...
  'G',       {[3,-1,-2, 0, 0, 0, 0], 6.6730e-11},...
  'dozen',   {[0, 0, 0, 0, 0, 0, 0],12},...
  'AU',      {[1, 0, 0, 0, 0, 0, 0],149598000*1000},...
  'c',       {[1, 0,-1, 0, 0, 0, 0],299792458},...
  'KJ',      {[2, 1,-2, 0, 0, 0, 0],1000},...
  'g',       {[0, 1, 0, 0, 0, 0, 0],0.001},...
  'L',       {[3, 0, 0, 0, 0, 0, 0],0.001},...
  'kilotons',{[0, 1, 0, 0, 0, 0, 0],1000*1000},...
  'm_earth', {[0, 1, 0, 0, 0, 0, 0],5.9742e24},...
  'r_earth', {[1, 0, 0, 0, 0, 0, 0],6378100});
end


methods
function s = annotate(this,x)
% takes in a value and returns value that is unit-annotated for sure
% if it is annotated already, the same unit is returned
  if (isstruct(x) && isfield(x,this.annotated))
    s = x;
  else
    s = struct(this.annotated,true,'val',x,'unit',this.noUnit);
  end
end

function [a,b,c] = prepareOp(this,args)
% prepares input args a,b and output arg c for binary operation -- this
% is just common code put in a separate function
  if (length(args) ~= 2)
    error(strcat('binary operation needs exactly 2 arguments'));
  end
  a = this.annotate(args{1});
  b = this.annotate(args{2});
  c = this.one;
end

% displays a unit on screen
function display(this,v)
  if ((isstruct(v)) && isfield(v, this.annotated))
    fprintf('%s:',this.unitString(v.unit));  disp(v.val);
  else
    disp(v);
  end
end
    
function s = unitString(this,v)
% returns the unit string of a given unit vector
% this is done greedily/recursively by picking the unit that most reduces the 1-norm
% of the unit vector.
  s = '';
  if (v == this.noUnit)
    return;
  end
  names = fieldnames(this.units);
  print = zeros(length(names), 1);

  % this loop picks the unit that most reduces the 1-norm of v, and adds it to 'print' until v is 0
  while (~same(v,0*v))
    newPNorm = (print * 0);
    newMNorm = (print * 0);
    for i = (1 : length(names))
      u = getfield(this.units, names{i}); % get vector for unit i
      newPNorm(i) = norm((v - u), 1); % see how much that unit reduces the unit vector v
      newMNorm(i) = norm((v + u), 1); % same but with inverted unit
    end
    [minPNorm, minPi] = min(newPNorm);
    [minMNorm, minMi] = min(newMNorm);
    if (minPNorm < minMNorm) % put the found unit into print vector
      print(minPi) = (print(minPi) + 1); % positive unit (unit^1)
      u = -getfield(this.units, names{minPi});
    else
      print(minMi) = (print(minMi) - 1); % negativ unit (unit^-1)
      u = (getfield(this.units, names{minMi}));
    end
    v = (v + u);
  end

  % put whatever is in the print vector into a string
  for i = (1 : length(print))
    if (print(i) ~= 0)
      s = strcat(s, strcat('*', names{i}));
      if (print(i) ~= 1)
        s = strcat(s, strcat('^', num2str(print(i))));
      end
    end
  end
  s = s(2:length(s)); % we know there must be one unit - replace leading '*'
end
end



patterns
disp : call(disp);
plus : call(plus(*,*));
minus : call(minus(*,*));
mtimes : call(mtimes(*,*));
mrdivide : call(mrdivide(*,*));
power : call(power(*,*));
round : call(round(*));
colon : call(colon(*,..));
allCalls : call(*());
loopheader : loophead(*);
end


actions
% captures all loop invocations for i = range, and overwrites the
% expression to be a struct-array instead of a structure with an array inside
loop : around loopheader : (newVal)
   range = this.annotate(newVal);
   % loop through range.val, and record whatever the for loop captures in a cell array
   acell = {};
   for i = (range.val)
     acell{length(acell)+1} = i;
   end
   varargout{1} = struct(this.annotated,true,'val',acell,'unit',range.unit);
end

acalls : around allCalls : (name)
% captures all calls and checks whether they are a nuit - if so, return the unit
% this advice is first so that it gets matched last
  if (isfield(this.units,name))
    varargout{1} = struct(this.annotated,true,'val',1,'unit',getfield(this.units,name));
  else
    if (isfield(this.constants,name))
      pair = getfield(this.constants,name);
      varargout{1} = struct(this.annotated, true, 'val', getfield(this.constants,{2},name), 'unit', ...
                             getfield(this.constants,{1},name));
    else
      proceed();
    end
  end
end


adisp : around disp : (args)
% overrdes printing so that we add units
  if (length(args) ~= 1)
    error('Error using disp -- need exactly one argument)');
  end
  v = args{1};
  if (isstruct(v) && isfield(v,this.annotated))
    this.display(v);
  else
    disp(v);
  end
end


aplus : around plus : (args)
% +
  [a,b,c] = this.prepareOp(args);
  c.val = a.val+b.val;
  if (a.unit ~= b.unit)
    error('the units of the arguments for operation + must match');
  end  
  c.unit = a.unit;
  varargout{1} = c;  
end

aminus : around minus : (args)
% -
  [a,b,c] = this.prepareOp(args);
  c.val = a.val-b.val;
  if (a.unit ~= b.unit)
    error('the units of the arguments for operation - must match');
  end  
  c.unit = a.unit;
  varargout{1} = c;  
end

amtimes : around mtimes : (args)
% *
  [a,b,c] = this.prepareOp(args);
  c.val = a.val*b.val;
  c.unit = a.unit+b.unit;
  varargout{1} = c;  
end

amrdivide : around mrdivide : (args)
% /
  [a,b,c] = this.prepareOp(args);
  c.val = a.val/b.val;
  c.unit = a.unit-b.unit;
  varargout{1} = c;  
end

power : around power : (args)
% .^
  [a,b,c] = this.prepareOp(args);
  c.val = a.val.^b.val;
  if (b.unit ~= this.noUnit)
    error('cannot use power with a non empty unit');
  end
  if (isscalar(b.val))
    c.val = a.val.^b.val;
    c.unit = a.unit*b.val;
  else
    if (a.unit ~= this.noUnit)
      error('cannot use power operation resulting mixed unit matrix');
    end
    c.unit = this.noUnit;
  end
  varargout{1} = c;  
end

round : around round : (args)
% round
  if (length(args) ~= 1)
    proceed();
  end
  a = this.annotate(args{1});
  a.val = round(a.val);
  varargout{1} = a;  
end

colon : around colon : (args)
% : :
  if (length(args) ~= 2 && length(args) ~= 3)
    proceed();
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
    o.val = a.val:b.val:c.val;
  else
    o.val = a.val:b.val;
  end
  varargout{1} = o;  
end

end

end