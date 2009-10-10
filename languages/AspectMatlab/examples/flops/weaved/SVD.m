function  [Uout, Sout, Vout] = SVD(A)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'flops'))
    AM_GLOBAL.flops = flops;
  end
  AM_CVar_0 = A;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_1 = size(AM_CVar_0, 1);
  AM_GLOBAL.flops.flops_aany('size', 9, 'SVD');
% given an arbitrary mxn matrix (m >= n), computes its svd
% and returns it in U,S,V, where A = U*S*V, U,V unitary, and S diagonal,
% where s_i >= 0, and s_i >= s_i+1
% if called with less than three output arguments, will return only S
% eps is used for the tolerance, but is ignored.
% this svd algo is slightly inefficient, i.e. spread over a couple of
% functions that call each other a lot (we use the Kogbetliantz method)
% get the size of the matrix
  m = AM_CVar_1;
  AM_CVar_2 = A;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_3 = size(AM_CVar_2, 2);
  AM_GLOBAL.flops.flops_aany('size', 10, 'SVD');
  n = AM_CVar_3;
  AM_CVar_4 = m;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_5 = eye(AM_CVar_4);
  AM_GLOBAL.flops.flops_aany('eye', 11, 'SVD');
  U = AM_CVar_5;
  AM_CVar_6 = n;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_7 = eye(AM_CVar_6);
  AM_GLOBAL.flops.flops_aany('eye', 12, 'SVD');
  V = AM_CVar_7;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_8 = eps;
  AM_GLOBAL.flops.flops_aany('eps', 13, 'SVD');
  AM_CVar_9 = A;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_10 = fro(AM_CVar_9);
  AM_GLOBAL.flops.flops_aany('fro', 13, 'SVD');
  e = (AM_CVar_8 * AM_CVar_10);
    AM_CVar_225 = m;
    AM_CVar_226 = n;
  AM_GLOBAL.flops.flops_bany();
    AM_CVar_227 = eye(AM_CVar_225, AM_CVar_226);
  AM_GLOBAL.flops.flops_aany('eye', 14, 'SVD');
    AM_CVar_228 = A((~AM_CVar_227));
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_229 = AM_GLOBAL.flops.flops_aabs({AM_CVar_228}, 0, @abs, {AM_CVar_228});
  AM_GLOBAL.flops.flops_aany('abs', 14, 'SVD');
  AM_GLOBAL.flops.flops_bany();
    AM_CVar_230 = sum(AM_CVar_229);
  AM_GLOBAL.flops.flops_aany('sum', 14, 'SVD');
    AM_CVar_231 = e;
    AM_CVar_224 = (AM_CVar_230 > AM_CVar_231);
  while AM_CVar_224
    AM_CVar_11 = n;
    AM_tmpAS_i = (1 : AM_CVar_11);
    for AM_tmpFS_i = (1 : numel(AM_tmpAS_i))
      i = AM_tmpAS_i(AM_tmpFS_i);
      AM_CVar_12 = i;
      AM_CVar_13 = n;
      AM_tmpAS_j = ((AM_CVar_12 + 1) : AM_CVar_13);
      for AM_tmpFS_j = (1 : numel(AM_tmpAS_j))
        j = AM_tmpAS_j(AM_tmpFS_j);
        AM_CVar_14 = A;
        AM_CVar_15 = m;
        AM_CVar_16 = n;
        AM_CVar_17 = i;
        AM_CVar_18 = j;
        AM_GLOBAL.flops.flops_bany();
% termination condition
        [J1, J2] = jacobi(AM_CVar_14, AM_CVar_15, AM_CVar_16, AM_CVar_17, AM_CVar_18);
        AM_GLOBAL.flops.flops_aany('jacobi', 17, 'SVD');
        AM_CVar_19 = J1;
        AM_CVar_20 = A;
        AM_CVar_21 = J2;
        AM_GLOBAL.flops.flops_bany();
        AM_CVar_22 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_20, AM_CVar_21}, 0, @mtimes, {AM_CVar_20, AM_CVar_21});
        AM_GLOBAL.flops.flops_aany('mtimes', 18, 'SVD');
        AM_GLOBAL.flops.flops_bany();
        AM_CVar_23 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_19, AM_CVar_22}, 1, @mtimes, {AM_CVar_19, AM_CVar_22});
        AM_GLOBAL.flops.flops_aany('mtimes', 18, 'SVD');
        A = AM_CVar_23;
        AM_CVar_24 = U;
        AM_CVar_25 = J1;
        AM_GLOBAL.flops.flops_bany();
        AM_CVar_26 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_24, (AM_CVar_25')}, 2, @mtimes, {AM_CVar_24, (AM_CVar_25')});
        AM_GLOBAL.flops.flops_aany('mtimes', 19, 'SVD');
        U = AM_CVar_26;
        AM_CVar_27 = J2;
        AM_CVar_28 = V;
        AM_GLOBAL.flops.flops_bany();
        AM_CVar_29 = AM_GLOBAL.flops.flops_amtimes({(AM_CVar_27'), AM_CVar_28}, 3, @mtimes, {(AM_CVar_27'), AM_CVar_28});
        AM_GLOBAL.flops.flops_aany('mtimes', 20, 'SVD');
        V = AM_CVar_29;
      end
      AM_CVar_30 = n;
      AM_CVar_31 = m;
      AM_tmpAS_j = ((AM_CVar_30 + 1) : AM_CVar_31);
      for AM_tmpFS_j = (1 : numel(AM_tmpAS_j))
        j = AM_tmpAS_j(AM_tmpFS_j);
        AM_CVar_32 = A;
        AM_CVar_33 = m;
        AM_CVar_34 = n;
        AM_CVar_35 = i;
        AM_CVar_36 = j;
        AM_GLOBAL.flops.flops_bany();
        AM_CVar_37 = jacobi2(AM_CVar_32, AM_CVar_33, AM_CVar_34, AM_CVar_35, AM_CVar_36);
        AM_GLOBAL.flops.flops_aany('jacobi2', 23, 'SVD');
        J1 = AM_CVar_37;
        AM_CVar_38 = J1;
        AM_CVar_39 = A;
        AM_GLOBAL.flops.flops_bany();
        AM_CVar_40 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_38, AM_CVar_39}, 4, @mtimes, {AM_CVar_38, AM_CVar_39});
        AM_GLOBAL.flops.flops_aany('mtimes', 24, 'SVD');
        A = AM_CVar_40;
        AM_CVar_41 = U;
        AM_CVar_42 = J1;
        AM_GLOBAL.flops.flops_bany();
        AM_CVar_43 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_41, (AM_CVar_42')}, 5, @mtimes, {AM_CVar_41, (AM_CVar_42')});
        AM_GLOBAL.flops.flops_aany('mtimes', 25, 'SVD');
        U = AM_CVar_43;
      end
    end
    AM_CVar_225 = m;
    AM_CVar_226 = n;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_227 = eye(AM_CVar_225, AM_CVar_226);
    AM_GLOBAL.flops.flops_aany('eye', 14, 'SVD');
    AM_CVar_228 = A((~AM_CVar_227));
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_229 = AM_GLOBAL.flops.flops_aabs({AM_CVar_228}, 1, @abs, {AM_CVar_228});
    AM_GLOBAL.flops.flops_aany('abs', 14, 'SVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_230 = sum(AM_CVar_229);
    AM_GLOBAL.flops.flops_aany('sum', 14, 'SVD');
    AM_CVar_231 = e;
    AM_CVar_224 = (AM_CVar_230 > AM_CVar_231);
  end
  AM_CVar_44 = A;
  S = AM_CVar_44;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_45 = nargout;
  AM_GLOBAL.flops.flops_aany('nargout', 31, 'SVD');
  if (AM_CVar_45 < 3)
    AM_CVar_46 = S;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_47 = diag(AM_CVar_46);
    AM_GLOBAL.flops.flops_aany('diag', 32, 'SVD');
% check if we need less than three output arguments
    Uout = AM_CVar_47;
  else 
    AM_CVar_48 = U;
    Uout = AM_CVar_48;
    AM_CVar_49 = S;
    AM_CVar_50 = m;
    AM_CVar_51 = n;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_52 = eye(AM_CVar_50, AM_CVar_51);
    AM_GLOBAL.flops.flops_aany('eye', 34, 'SVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_53 = AM_GLOBAL.flops.flops_atimes({AM_CVar_49, AM_CVar_52}, 0, @times, {AM_CVar_49, AM_CVar_52});
    AM_GLOBAL.flops.flops_aany('times', 34, 'SVD');
    Sout = AM_CVar_53;
    AM_CVar_54 = V;
    Vout = AM_CVar_54;
  end
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
function  [J1, J2] = jacobi(A, m, n, i, j)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'flops'))
    AM_GLOBAL.flops = flops;
  end
  AM_CVar_55 = i;
  AM_CVar_56 = i;
  AM_CVar_57 = A(AM_CVar_55, AM_CVar_56);
  AM_CVar_58 = i;
  AM_CVar_59 = j;
  AM_CVar_60 = A(AM_CVar_58, AM_CVar_59);
  AM_CVar_61 = j;
  AM_CVar_62 = i;
  AM_CVar_63 = A(AM_CVar_61, AM_CVar_62);
  AM_CVar_64 = j;
  AM_CVar_65 = j;
  AM_CVar_66 = A(AM_CVar_64, AM_CVar_65);
% finds the jacobi rotation that will zero A(i,j), A(j,i), A a mxn matrix
% get little matrix out of A
  B = [AM_CVar_57, AM_CVar_60; AM_CVar_63, AM_CVar_66];
  AM_CVar_67 = B;
  AM_GLOBAL.flops.flops_bany();
% get its svd
  [U, S, V] = tinySVD(AM_CVar_67);
  AM_GLOBAL.flops.flops_aany('tinySVD', 42, 'jacobi');
  AM_CVar_68 = m;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_69 = eye(AM_CVar_68);
  AM_GLOBAL.flops.flops_aany('eye', 44, 'jacobi');
  J1 = AM_CVar_69;
  AM_CVar_70 = U(1, 1);
  J1(i, i) = AM_CVar_70;
  AM_CVar_71 = U(2, 2);
  J1(j, j) = AM_CVar_71;
  AM_CVar_72 = U(2, 1);
  J1(i, j) = AM_CVar_72;
  AM_CVar_73 = U(1, 2);
  J1(j, i) = AM_CVar_73;
  AM_CVar_74 = n;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_75 = eye(AM_CVar_74);
  AM_GLOBAL.flops.flops_aany('eye', 50, 'jacobi');
  J2 = AM_CVar_75;
  AM_CVar_76 = V(1, 1);
  J2(i, i) = AM_CVar_76;
  AM_CVar_77 = V(2, 2);
  J2(j, j) = AM_CVar_77;
  AM_CVar_78 = V(2, 1);
  J2(i, j) = AM_CVar_78;
  AM_CVar_79 = V(1, 2);
  J2(j, i) = AM_CVar_79;
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
function  [J1] = jacobi2(A, m, n, i, j)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'flops'))
    AM_GLOBAL.flops = flops;
  end
  AM_CVar_80 = i;
  AM_CVar_81 = i;
  AM_CVar_82 = A(AM_CVar_80, AM_CVar_81);
  AM_CVar_83 = j;
  AM_CVar_84 = i;
  AM_CVar_85 = A(AM_CVar_83, AM_CVar_84);
% finds the jacobi rotation that will zero A(j,i), A a mxn matrix
% get little matrix out of A
  B = [AM_CVar_82, 0; AM_CVar_85, 0];
  AM_CVar_86 = B;
  AM_GLOBAL.flops.flops_bany();
% get its svd
  [U, S, V] = tinySVD(AM_CVar_86);
  AM_GLOBAL.flops.flops_aany('tinySVD', 61, 'jacobi2');
  AM_CVar_87 = m;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_88 = eye(AM_CVar_87);
  AM_GLOBAL.flops.flops_aany('eye', 63, 'jacobi2');
  J1 = AM_CVar_88;
  AM_CVar_89 = U(1, 1);
  J1(i, i) = AM_CVar_89;
  AM_CVar_90 = U(2, 2);
  J1(j, j) = AM_CVar_90;
  AM_CVar_91 = U(2, 1);
  J1(i, j) = AM_CVar_91;
  AM_CVar_92 = U(1, 2);
  J1(j, i) = AM_CVar_92;
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
function  [Uout, Sout, Vout] = tinySVD(A)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'flops'))
    AM_GLOBAL.flops = flops;
  end
  AM_CVar_93 = A(1, 2);
  AM_CVar_94 = A(2, 1);
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_95 = AM_GLOBAL.flops.flops_aminus({AM_CVar_93, AM_CVar_94}, 0, @minus, {AM_CVar_93, AM_CVar_94});
  AM_GLOBAL.flops.flops_aany('minus', 77, 'tinySVD');
  AM_CVar_96 = A(1, 1);
  AM_CVar_97 = A(2, 2);
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_98 = AM_GLOBAL.flops.flops_aplus({AM_CVar_96, AM_CVar_97}, 0, @plus, {AM_CVar_96, AM_CVar_97});
  AM_GLOBAL.flops.flops_aany('plus', 77, 'tinySVD');
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_99 = AM_GLOBAL.flops.flops_ardivide({AM_CVar_95, AM_CVar_98}, 0, @rdivide, {AM_CVar_95, AM_CVar_98});
  AM_GLOBAL.flops.flops_aany('rdivide', 77, 'tinySVD');
% given an arbitrary 2x2 matrix, computes its svd
% and returns it in U,S,V, where A = U*S*V, U,V unitary, and S diagonal,
% where s_i >= 0, and s_i >= s_i+1
% if called with less than three output arguments, will return only S
  t = AM_CVar_99;
  AM_CVar_100 = t;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_101 = AM_GLOBAL.flops.flops_asqrt({(1 + (AM_CVar_100 ^ 2))}, 0, @sqrt, {(1 + (AM_CVar_100 ^ 2))});
  AM_GLOBAL.flops.flops_aany('sqrt', 78, 'tinySVD');
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_102 = AM_GLOBAL.flops.flops_ardivide({1, AM_CVar_101}, 1, @rdivide, {1, AM_CVar_101});
  AM_GLOBAL.flops.flops_aany('rdivide', 78, 'tinySVD');
  c = AM_CVar_102;
  AM_CVar_103 = t;
  AM_CVar_104 = c;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_105 = AM_GLOBAL.flops.flops_atimes({AM_CVar_103, AM_CVar_104}, 1, @times, {AM_CVar_103, AM_CVar_104});
  AM_GLOBAL.flops.flops_aany('times', 79, 'tinySVD');
  s = AM_CVar_105;
  AM_CVar_106 = c;
  AM_CVar_107 = s;
  AM_CVar_108 = s;
  AM_CVar_109 = c;
  R = [AM_CVar_106, (-AM_CVar_107); AM_CVar_108, AM_CVar_109];
  AM_CVar_110 = R;
  AM_CVar_111 = A;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_112 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_110, AM_CVar_111}, 6, @mtimes, {AM_CVar_110, AM_CVar_111});
  AM_GLOBAL.flops.flops_aany('mtimes', 81, 'tinySVD');
% find symmetric matrix
  M = AM_CVar_112;
  AM_CVar_113 = M;
  AM_GLOBAL.flops.flops_bany();
  [U, S, V] = tinySymmetricSVD(AM_CVar_113);
  AM_GLOBAL.flops.flops_aany('tinySymmetricSVD', 82, 'tinySVD');
  AM_CVar_114 = R;
  AM_CVar_115 = U;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_116 = AM_GLOBAL.flops.flops_amtimes({(AM_CVar_114'), AM_CVar_115}, 7, @mtimes, {(AM_CVar_114'), AM_CVar_115});
  AM_GLOBAL.flops.flops_aany('mtimes', 83, 'tinySVD');
  U = AM_CVar_116;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_117 = nargout;
  AM_GLOBAL.flops.flops_aany('nargout', 86, 'tinySVD');
  if (AM_CVar_117 < 3)
    AM_CVar_118 = S;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_119 = diag(AM_CVar_118);
    AM_GLOBAL.flops.flops_aany('diag', 87, 'tinySVD');
% check if we need less than three output arguments
    Uout = AM_CVar_119;
  else 
    AM_CVar_120 = U;
    Uout = AM_CVar_120;
    AM_CVar_121 = S;
    Sout = AM_CVar_121;
    AM_CVar_122 = V;
    Vout = AM_CVar_122;
  end
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
function  [Uout, Sout, Vout] = tinySymmetricSVD(A)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'flops'))
    AM_GLOBAL.flops = flops;
  end
  AM_CVar_123 = A(2, 1);
  if (AM_CVar_123 == 0)
    AM_CVar_124 = A;
% given a symmetric 2x2 matrix, computes its svd
% and returns it in U,S,V, where A = U*S*V, U,V unitary, and S diagonal,
% where s_i >= 0, and s_i >= s_i+1
% if called with less than three output arguments, will return only S
% case where it's already symmetric
    S = AM_CVar_124;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_125 = eye(2);
    AM_GLOBAL.flops.flops_aany('eye', 102, 'tinySymmetricSVD');
    U = AM_CVar_125;
    AM_CVar_126 = U;
    V = AM_CVar_126;
  else 
    AM_CVar_127 = A(1, 1);
% case where off diagonals are not 0
% taken directly from the notes
    w = AM_CVar_127;
    AM_CVar_128 = A(2, 1);
    y = AM_CVar_128;
    AM_CVar_129 = A(2, 2);
    z = AM_CVar_129;
    AM_CVar_130 = z;
    AM_CVar_131 = w;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_132 = AM_GLOBAL.flops.flops_aminus({AM_CVar_130, AM_CVar_131}, 1, @minus, {AM_CVar_130, AM_CVar_131});
    AM_GLOBAL.flops.flops_aany('minus', 109, 'tinySymmetricSVD');
    AM_CVar_133 = y;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_134 = AM_GLOBAL.flops.flops_atimes({2, AM_CVar_133}, 2, @times, {2, AM_CVar_133});
    AM_GLOBAL.flops.flops_aany('times', 109, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_135 = AM_GLOBAL.flops.flops_ardivide({AM_CVar_132, AM_CVar_134}, 2, @rdivide, {AM_CVar_132, AM_CVar_134});
    AM_GLOBAL.flops.flops_aany('rdivide', 109, 'tinySymmetricSVD');
    ro = AM_CVar_135;
    AM_CVar_136 = ro;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_137 = sign(AM_CVar_136);
    AM_GLOBAL.flops.flops_aany('sign', 110, 'tinySymmetricSVD');
    AM_CVar_138 = ro;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_139 = AM_GLOBAL.flops.flops_aabs({AM_CVar_138}, 2, @abs, {AM_CVar_138});
    AM_GLOBAL.flops.flops_aany('abs', 110, 'tinySymmetricSVD');
    AM_CVar_140 = ro;
    AM_CVar_141 = ro;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_142 = AM_GLOBAL.flops.flops_atimes({AM_CVar_140, AM_CVar_141}, 3, @times, {AM_CVar_140, AM_CVar_141});
    AM_GLOBAL.flops.flops_aany('times', 110, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_143 = AM_GLOBAL.flops.flops_aplus({AM_CVar_142, 1}, 1, @plus, {AM_CVar_142, 1});
    AM_GLOBAL.flops.flops_aany('plus', 110, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_144 = AM_GLOBAL.flops.flops_asqrt({AM_CVar_143}, 1, @sqrt, {AM_CVar_143});
    AM_GLOBAL.flops.flops_aany('sqrt', 110, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_145 = AM_GLOBAL.flops.flops_aplus({AM_CVar_139, AM_CVar_144}, 2, @plus, {AM_CVar_139, AM_CVar_144});
    AM_GLOBAL.flops.flops_aany('plus', 110, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_146 = AM_GLOBAL.flops.flops_ardivide({AM_CVar_137, AM_CVar_145}, 3, @rdivide, {AM_CVar_137, AM_CVar_145});
    AM_GLOBAL.flops.flops_aany('rdivide', 110, 'tinySymmetricSVD');
    t2 = AM_CVar_146;
    AM_CVar_147 = t2;
    t = AM_CVar_147;
    AM_CVar_148 = t;
    AM_CVar_149 = t;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_150 = AM_GLOBAL.flops.flops_atimes({AM_CVar_148, AM_CVar_149}, 4, @times, {AM_CVar_148, AM_CVar_149});
    AM_GLOBAL.flops.flops_aany('times', 112, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_151 = AM_GLOBAL.flops.flops_aplus({1, AM_CVar_150}, 3, @plus, {1, AM_CVar_150});
    AM_GLOBAL.flops.flops_aany('plus', 112, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_152 = AM_GLOBAL.flops.flops_asqrt({AM_CVar_151}, 2, @sqrt, {AM_CVar_151});
    AM_GLOBAL.flops.flops_aany('sqrt', 112, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_153 = AM_GLOBAL.flops.flops_ardivide({1, AM_CVar_152}, 4, @rdivide, {1, AM_CVar_152});
    AM_GLOBAL.flops.flops_aany('rdivide', 112, 'tinySymmetricSVD');
    c = AM_CVar_153;
    AM_CVar_154 = t;
    AM_CVar_155 = c;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_156 = AM_GLOBAL.flops.flops_atimes({AM_CVar_154, AM_CVar_155}, 5, @times, {AM_CVar_154, AM_CVar_155});
    AM_GLOBAL.flops.flops_aany('times', 113, 'tinySymmetricSVD');
    s = AM_CVar_156;
    AM_CVar_157 = c;
    AM_CVar_158 = s;
    AM_CVar_159 = s;
    AM_CVar_160 = c;
    U = [AM_CVar_157, (-AM_CVar_158); AM_CVar_159, AM_CVar_160];
    AM_CVar_161 = c;
    AM_CVar_162 = s;
    AM_CVar_163 = s;
    AM_CVar_164 = c;
    V = [AM_CVar_161, AM_CVar_162; (-AM_CVar_163), AM_CVar_164];
    AM_CVar_165 = U;
    AM_CVar_166 = A;
    AM_CVar_167 = V;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_168 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_166, AM_CVar_167}, 8, @mtimes, {AM_CVar_166, AM_CVar_167});
    AM_GLOBAL.flops.flops_aany('mtimes', 116, 'tinySymmetricSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_169 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_165, AM_CVar_168}, 9, @mtimes, {AM_CVar_165, AM_CVar_168});
    AM_GLOBAL.flops.flops_aany('mtimes', 116, 'tinySymmetricSVD');
    S = AM_CVar_169;
    AM_CVar_170 = U;
    U = (AM_CVar_170');
    AM_CVar_171 = V;
    V = (AM_CVar_171');
  end
  AM_CVar_172 = U;
  AM_CVar_173 = S;
  AM_CVar_174 = V;
  AM_GLOBAL.flops.flops_bany();
% make sure everything is descending etc...
  [U, S, V] = fixSVD(AM_CVar_172, AM_CVar_173, AM_CVar_174);
  AM_GLOBAL.flops.flops_aany('fixSVD', 121, 'tinySymmetricSVD');
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_175 = nargout;
  AM_GLOBAL.flops.flops_aany('nargout', 124, 'tinySymmetricSVD');
  if (AM_CVar_175 < 3)
    AM_CVar_176 = S;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_177 = diag(AM_CVar_176);
    AM_GLOBAL.flops.flops_aany('diag', 125, 'tinySymmetricSVD');
% check if we need less than three output arguments
    Uout = AM_CVar_177;
  else 
    AM_CVar_178 = U;
    Uout = AM_CVar_178;
    AM_CVar_179 = S;
    Sout = AM_CVar_179;
    AM_CVar_180 = V;
    Vout = AM_CVar_180;
  end
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
function  [U, S, V] = fixSVD(U, S, V)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'flops'))
    AM_GLOBAL.flops = flops;
  end
  AM_CVar_181 = S(1, 1);
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_182 = sign(AM_CVar_181);
  AM_GLOBAL.flops.flops_aany('sign', 136, 'fixSVD');
  AM_CVar_183 = S(2, 2);
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_184 = sign(AM_CVar_183);
  AM_GLOBAL.flops.flops_aany('sign', 136, 'fixSVD');
% takes matrizes U,S,V and returns matrizes U,S,V s.t. S is positive, and
% ordered in descending order
% this only works for 2x2 matrizes
% the diagonal matrix holding the signs of elts in S
  Z = [AM_CVar_182, 0; 0, AM_CVar_184];
  AM_CVar_185 = U;
  AM_CVar_186 = Z;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_187 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_185, AM_CVar_186}, 10, @mtimes, {AM_CVar_185, AM_CVar_186});
  AM_GLOBAL.flops.flops_aany('mtimes', 137, 'fixSVD');
  U = AM_CVar_187;
  AM_CVar_188 = Z;
  AM_CVar_189 = S;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_190 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_188, AM_CVar_189}, 11, @mtimes, {AM_CVar_188, AM_CVar_189});
  AM_GLOBAL.flops.flops_aany('mtimes', 138, 'fixSVD');
  S = AM_CVar_190;
  AM_CVar_191 = S(1, 1);
  AM_CVar_192 = S(2, 2);
  if (AM_CVar_191 < AM_CVar_192)
    P = [0, 1; 1, 0];
    AM_CVar_193 = U;
    AM_CVar_194 = P;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_195 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_193, AM_CVar_194}, 12, @mtimes, {AM_CVar_193, AM_CVar_194});
    AM_GLOBAL.flops.flops_aany('mtimes', 141, 'fixSVD');
    U = AM_CVar_195;
    AM_CVar_196 = P;
    AM_CVar_197 = S;
    AM_CVar_198 = P;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_199 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_197, AM_CVar_198}, 13, @mtimes, {AM_CVar_197, AM_CVar_198});
    AM_GLOBAL.flops.flops_aany('mtimes', 142, 'fixSVD');
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_200 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_196, AM_CVar_199}, 14, @mtimes, {AM_CVar_196, AM_CVar_199});
    AM_GLOBAL.flops.flops_aany('mtimes', 142, 'fixSVD');
    S = AM_CVar_200;
    AM_CVar_201 = P;
    AM_CVar_202 = V;
    AM_GLOBAL.flops.flops_bany();
    AM_CVar_203 = AM_GLOBAL.flops.flops_amtimes({AM_CVar_201, AM_CVar_202}, 15, @mtimes, {AM_CVar_201, AM_CVar_202});
    AM_GLOBAL.flops.flops_aany('mtimes', 143, 'fixSVD');
    V = AM_CVar_203;
  end
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
function  [f] = fro(M)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'flops'))
    AM_GLOBAL.flops = flops;
  end
  AM_CVar_204 = M;
  AM_CVar_205 = M;
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_206 = AM_GLOBAL.flops.flops_atimes({AM_CVar_204, AM_CVar_205}, 6, @times, {AM_CVar_204, AM_CVar_205});
  AM_GLOBAL.flops.flops_aany('times', 150, 'fro');
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_207 = sum(AM_CVar_206);
  AM_GLOBAL.flops.flops_aany('sum', 150, 'fro');
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_208 = sum(AM_CVar_207);
  AM_GLOBAL.flops.flops_aany('sum', 150, 'fro');
  AM_GLOBAL.flops.flops_bany();
  AM_CVar_209 = AM_GLOBAL.flops.flops_asqrt({AM_CVar_208}, 3, @sqrt, {AM_CVar_208});
  AM_GLOBAL.flops.flops_aany('sqrt', 150, 'fro');
% calculates the frobenius norm
  f = AM_CVar_209;
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
function  [s] = sign(x)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'flops'))
    AM_GLOBAL.flops = flops;
  end
  AM_CVar_210 = x;
  if (AM_CVar_210 > 0)
% we will override sign, so that sign(0) = 1
    s = 1;
  else 
    s = (-1);
  end
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
