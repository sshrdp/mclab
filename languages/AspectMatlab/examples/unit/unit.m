aspect test

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
one = struct('aspect_annotated',true,'val',1,'unit',noUnit);
units = struct(... % defines all SI and SI derived unit names and value (may be used for printing as well)
  'm',  [1, 0, 0, 0, 0, 0, 0],...
  'kg', [0, 1, 0, 0, 0, 0, 0],...
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
  'c',       {[1, 0, s, 0, 0, 0, 0],299792458},...
  'KJ',      {[2, 1,-2, 0, 0, 0, 0],1000},...
  'g',       {[0, 1, 0, 0, 0, 0, 0],0.001},...
  'L',       {[3, 0, 0, 0, 0, 0, 0],0.001},...
  'kilotons',{[0, 1, 0, 0, 0, 0, 0],1000*1000});
end

methods
function s = annotate(this,x)
% takes in a value and returns value that is unit-annotated for sure
% if it is annotated already, the same unit is returned
  if (isstruct(x) && isfield(x,'aspect_annotated'))
    s = x;
  else
    s = struct('aspect_annoated',true,'val',x,'unit',this.noUnit);
  end
end

function [a,b,c] = prepareOp(this,args,name)
% prepares input args a,b and output arg c for binary operation -- this
% is just common code put in a separate function
  if (length(args) ~= 2)
    error(strcat(name,' needs exactly 2 arguments'));
  end
  a = this.annotate(args{1});
  b = this.annotate(args{2});
  c = this.one;
end

function s = unitString(this,v)
% returns the unit string of a given unit vector
% this is done greedily/recursively by picking the unit that most reduces the 1-norm
% of the unit vector.
  s = '';
  if (v==this.noUnit)
    return;
  end
  names = fieldnames(this.units)
  print = zeros(length(this.names),1);
  findPrintVector();
  
  for i = 1:length(print)
    if (print(i)~= 0)
      s = strcat(s,strcat('*',fieldnames(i)));
      if (print(i) ~= 1)
         s = strcat(s,strcat('^',num2str(print(i))));
      end
    end
  end

  % picks the unit that most reduces the 1-norm of v, and adds it to 'print' until v is 0
  function findPrintVector
    if (v == 0*v); return; end;
    newPNorm = print*0;
    newMNorm = print*0;
    for i = 1:length(names)
      u = gefield(this.units,names{i}); % get vector for unit i
      newPNorm(i) = norm(v-u,1); % see how much that unit reduces the unit vector v
      newMNorm(i) = norm(v+u,1); % same but with inverted unit
    end
    [minPNorm,minPi] = min(newNorm);
    [minMNorm,minMi] = min(newNorm);
    if (minPNorm < minMNorm)
      print(minPi) = print(minPi)+1;
      u = getfield(this.units,names{i});
    else
      print(minMi) = print(minMi)-1;
      u = -getfield(this.units,names{i});
    end
    v = v+u;
  end
end
end



patterns
allCalls : call(*);
disp : call(disp);
plus : call(plus);
minus : call(minus);
mtimes : call(mtimes);
mrdivide : call(mrdivide);
power : call(power);
end


actions
acalls : around allCalls : (name)
% captures all calls and checks whether they are a nuit - if so, return the unit
  if (isfield(this.units,name))
    AM_retValue = struct('aspect_annoated',true,'val',1,'unit',getfield(this.units,name));
  elseif (isfield(this.constants,name))
    pair = getfield(this.constants,name);
    AM_retValue = struct('aspect_annoated',true,'val',pair{2},'unit',pair{1});    
  else
    proceed();
  end
end

adisp : around disp : (args)
% overrdes printing so that we add units
  if (length(args) ~= 1)
    error('Error using disp -- need exactly one argument)');
  end
  v = args{1};
  if (~isstruct(v) && isfield(v,'aspect_annotated'))
    disp({v.val, this.unitString(v.unit)});
  else
    disp(v);
  end
end


aplus : around plus : (args)
% +
  [a,b,c] = this.prepareOp(args,name);
  c.val = a+b;
  if (a.unit ~= b.unit)
    error('the units of the arguments for operation + must match');
  end  
  c.unit = a.unit;
  AM_retValue = c;  
end

aminus : around minus : (args)
% -
  [a,b,c] = this.prepareOp(args,name);
  c.val = a-b;
  if (a.unit ~= b.unit)
    error('the units of the arguments for operation - must match');
  end  
  c.unit = a.unit;
  AM_retValue = c;  
end

amtimes : around mtimes : (args)
% *
  [a,b,c] = this.prepareOp(args,name);
  c.val = a*b;
  c.unit = a.unit+b.unit;
  AM_retValue = c;  
end

amrdivide : around mrdivide : (args)
% /
  [a,b,c] = this.prepareOp(args,name);
  c.val = a/b;
  c.unit = a.unit-b.unit;
  AM_retValue = c;  
end

power : around power : (args)
% .^
  [a,b,c] = this.prepareOp(args,name);
  c.val = a.^b;
  if (b.unit ~= noUnit)
    error('cannot use power with a non empty unit');
  end
  if (isscalar(b.val))
    c.val = a.val.^b.val;
    c.unit = a.unit*a.val;
  else
    if (a.unit ~= noUnit)
      error('cannot use power operation resulting mixed unit matrix');
    end
    c.unit = noUnit;
  end
  AM_retValue = c;  
end

end

end