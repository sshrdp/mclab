global AM_GLOBAL;
if (~isempty(AM_GLOBAL))
  AM_EntryPoint_1 = 0;
else 
  AM_EntryPoint_1 = 1;
end
if (~isfield(AM_GLOBAL, 'flops'))
  AM_GLOBAL.flops = flops;
end
AM_tmpAS_n = (10 : 10 : 10);
for AM_tmpFS_n = (1 : numel(AM_tmpAS_n))
  n = AM_tmpAS_n(AM_tmpFS_n);
  if (exist('tic', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  tic
  if (exist('tic', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('tic', 4, 'Script');
  end
  if (exist('n', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_211 = n;
  if (exist('n', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('n', 5, 'Script');
  end
  if (exist('n', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_212 = n;
  if (exist('n', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('n', 5, 'Script');
  end
  if (exist('rand', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_213 = rand(AM_CVar_211, AM_CVar_212);
  if (exist('rand', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('rand', 5, 'Script');
  end
  x = AM_CVar_213;
  if (exist('x', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_214 = x;
  if (exist('x', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('x', 6, 'Script');
  end
  if (exist('SVD', 'var') ~= 1)
    AM_GLOBAL.flops.flops_beforeTrack('SVD');
  end
  if (exist('SVD', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  [u, s, v] = SVD(AM_CVar_214);
  if (exist('SVD', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('SVD', 6, 'Script');
  end
  if (exist('SVD', 'var') ~= 1)
    AM_GLOBAL.flops.flops_afterTrack();
  end
  if (exist('n', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_215 = n;
  if (exist('n', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('n', 7, 'Script');
  end
  if (exist('u', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_216 = u;
  if (exist('u', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('u', 7, 'Script');
  end
  if (exist('s', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_217 = s;
  if (exist('s', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('s', 7, 'Script');
  end
  if (exist('diag', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_218 = diag(AM_CVar_217);
  if (exist('diag', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('diag', 7, 'Script');
  end
  if (exist('diag', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_219 = diag(AM_CVar_218);
  if (exist('diag', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('diag', 7, 'Script');
  end
  if (exist('v', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_220 = v;
  if (exist('v', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('v', 7, 'Script');
  end
  if (exist('x', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_221 = x;
  if (exist('x', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('x', 7, 'Script');
  end
  if (exist('norm', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_222 = norm((((AM_CVar_216 * AM_CVar_219) * AM_CVar_220) - AM_CVar_221), 2);
  if (exist('norm', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('norm', 7, 'Script');
  end
  if (exist('toc', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  AM_CVar_223 = toc;
  if (exist('toc', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('toc', 7, 'Script');
  end
  if (exist('fprintf', 'var') ~= 1)
    AM_GLOBAL.flops.flops_bany();
  end
  fprintf('run with n=%x, delta norm=%x, time: %.3fs\n', AM_CVar_215, AM_CVar_222, AM_CVar_223);
  if (exist('fprintf', 'var') ~= 1)
    AM_GLOBAL.flops.flops_aany('fprintf', 7, 'Script');
  end
end
if AM_EntryPoint_1
  AM_GLOBAL = [];
end


