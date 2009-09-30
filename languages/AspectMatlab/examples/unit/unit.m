aspect test

properties
% allows adding units
% units are SI and SI derived units
% the unit is denoted by a vector 
% [metre kg second Ampere Kelvin candela mol]

% all acesses to functions denoted by units are overriden
% all operations are overriden
% indexing gets overriden

% uses structs using the aspect_annoted flag
noUnit = [0, 0, 0, 0, 0, 0, 0];
one = struct('aspect_annotated',true,'val',1,'unit',noUnit);
units = struct(... % defines all SI and SI derived unit names and value (may be used for printing as well)
  'm',[1, 0, 0, 0, 0, 0, 0],...
  'kg',[0, 1, 0, 0, 0, 0, 0],...
  's',[0, 0, 1, 0, 0, 0, 0],...
  'A',[0, 0, 0, 1, 0, 0, 0],...
  'K',[0, 0, 0, 0, 1, 0, 0],...
  'cd',[0, 0, 0, 0, 0, 1, 0],...
  'mol',[0, 0, 0, 0, 0, 0, 1]...
);
constants = struct(... % defines constaants or units with factor not 1
  'km',{[1, 0, 0, 0, 0, 0],1000}...
);

end

methods
function s = annotate(x)
% takes in a value and returns value that is unit-annotated for sure
% if it is annotated already, the same unit is returned
  if (isstruct(x) && isfield(x,'aspect_annotated'))
    s = x;
  else
    s = struct('aspect_annoated',true,'val',x,'unit',noUnit);
  end
end

function [a,b,c] = prepareOp(args,name)
% prepares input args a,b and output arg c for binary operation -- this
% is just common code put in a separate function
  if (length(args) ~= 2)
    error(strcat(name,' needs exactly 2 arguments'));
  end
  a = annotate(args{1});
  b = annotate(args{2});
  c = one;
end

function s = unitString(v)
% returns the unit string of a given unit vector
  s = 'm'; % todo
end

end

patterns
allCalls : call(*);
disp : call(disp);
plus : call(plus);
minus : call(minus);
mtimes : call(mtimes);
end

actions
acalls : around : allCalls(name)
% captures all calls and checks whether they are a nuit - if so, return the unit
  if (isfield(units,name))
    return struct('aspect_annoated',true,'val',1,'unit',getfield(units,name));
  elseif (isfield(constants,name))
    pair = getfield(constants,name));
    return struct('aspect_annoated',true,'val',pair{2},'unit',pair{1});    
  else
    proceed();
  end
end

adisp : around : disp(args)
% overrdes printing so that we add units
  if (length(args) ~= 1
    error('Error using disp -- need exactly one argument)');
  end
  v = args{1};
  if (~isstruct(v) && isfield(v,'aspect_annotated'))
    disp({v.val unitString(v.unit)});
  else
    disp(v);
  end
end


aplus : around : plus(args,name)
  [a,b,c] = prepareOp(args,name);
  c.val = a+b;
  if (a.unit ~= b.unit)
    error('the units of the arguments for operation + must match');
  end  
  c.unit = a.unit;
  return c;  
end
aminus : around : minus(args,name)
  [a,b,c] = prepareOp(args,name);
  c.val = a-b;
  if (a.unit ~= b.unit)
    error('the units of the arguments for operation - must match');
  end  
  c.unit = a.unit;
  return c;  
end
amtimes : around : mtimes(args,name)
  [a,b,c] = prepareOp(args,name);
  c.val = a*b;
  c.unit = a.unit+b.unit;
  return c;  
end

end