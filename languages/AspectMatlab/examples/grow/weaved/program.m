function  [] = program()
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'grow'))
    AM_GLOBAL.grow = grow;
  end
  AM_GLOBAL.grow.grow_message();
  solveHeatEquation(.01, 500);
  AM_GLOBAL.grow.grow_displayResults();
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
