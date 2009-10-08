function  [] = program()
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'sparsity'))
    AM_GLOBAL.sparsity = sparsity;
  end
  AM_GLOBAL.sparsity.sparsity_message();
  solveHeatEquation(.01, 500);
  AM_GLOBAL.sparsity.sparsity_displayResults();
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end

