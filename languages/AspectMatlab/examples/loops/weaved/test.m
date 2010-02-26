function  [] = test()
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'loops'))
    AM_GLOBAL.loops = loops;
  end
  AM_CVar_9 = (1 : 2 : 99);
  AM_tmpAS_i = AM_CVar_9;
  AM_GLOBAL.loops.loops_aLoopHead(AM_CVar_9);
  for AM_tmpFS_i = (1 : numel(AM_tmpAS_i))
    i = AM_tmpAS_i(AM_tmpFS_i);
    AM_GLOBAL.loops.loops_aLoopBody(AM_tmpFS_i);
    AM_CVar_0 = AM_GLOBAL.loops.loops_aLBound(0, @lBound, {}, {}, {}, 'test.m', 4, 'test', 'lBound', {}, []);
% the following script shows some example uses for the loops aspect
    disp({'Lower Bound i: ', AM_CVar_0});
    AM_CVar_1 = AM_GLOBAL.loops.loops_aUBound(0, @uBound, {}, {}, {}, 'test.m', 5, 'test', 'uBound', {}, []);
    disp({'Upper Bound i: ', AM_CVar_1});
    AM_CVar_8 = (10 : (-1) : 1);
    AM_tmpAS_j = AM_CVar_8;
    AM_GLOBAL.loops.loops_aLoopHead(AM_CVar_8);
    for AM_tmpFS_j = (1 : numel(AM_tmpAS_j))
      j = AM_tmpAS_j(AM_tmpFS_j);
      AM_GLOBAL.loops.loops_aLoopBody(AM_tmpFS_j);
      AM_CVar_2 = AM_GLOBAL.loops.loops_aLBound(1, @lBound, {}, {}, {}, 'test.m', 8, 'test', 'lBound', {}, []);
      disp({'Lower Bound j: ', AM_CVar_2});
      AM_CVar_3 = AM_GLOBAL.loops.loops_aUBound(1, @uBound, {}, {}, {}, 'test.m', 9, 'test', 'uBound', {}, []);
      disp({'Upper Bound j: ', AM_CVar_3});
      AM_CVar_4 = AM_GLOBAL.loops.loops_aIncrement(0, @increment, {}, {}, {}, 'test.m', 10, 'test', 'increment', {}, []);
      disp({'Increment j: ', AM_CVar_4});
      AM_CVar_5 = AM_GLOBAL.loops.loops_aIteration(0, @iteration, {}, {}, {}, 'test.m', 11, 'test', 'iteration', {}, []);
      disp({'Iteration j: ', AM_CVar_5});
    end
    AM_GLOBAL.loops.loops_aLoop();
    AM_CVar_6 = AM_GLOBAL.loops.loops_aIncrement(1, @increment, {}, {}, {}, 'test.m', 14, 'test', 'increment', {}, []);
    disp({'Increment i: ', AM_CVar_6});
    AM_CVar_7 = AM_GLOBAL.loops.loops_aIteration(1, @iteration, {}, {}, {}, 'test.m', 15, 'test', 'iteration', {}, []);
    disp({'Iteration i: ', AM_CVar_7});
  end
  AM_GLOBAL.loops.loops_aLoop();
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
