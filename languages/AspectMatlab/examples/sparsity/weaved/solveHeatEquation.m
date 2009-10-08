function  [] = solveHeatEquation(AM_CVar_14, AM_CVar_13)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_1 = 0;
  else 
    AM_EntryPoint_1 = 1;
  end
  if (~isfield(AM_GLOBAL, 'sparsity'))
    AM_GLOBAL.sparsity = sparsity;
  end
  if (exist('a', 'var') ~= 1)
    a = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_14, a, 'a');
  a = AM_CVar_14;
  if (exist('steps', 'var') ~= 1)
    steps = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_13, steps, 'steps');
  steps = AM_CVar_13;
  if (exist('tN', 'var') ~= 1)
    tN = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(3, tN, 'tN');
% function solveHeatEquation(a,steps)
% solves the heat equation
%    Ut = a*Uxx,    x ? [0, 2pi], t > 0
% with boundary conditions U(0, t) = Ux(2pi, t) = 0, and initial condition 
% U(x, 0) = 0, but U(x, 0) = 10 for x around the center.
% Uses a centered-in-space finite difference method.
% Uses RK4 method, for the integration in time. Uses 300 spatial steps.
% the inputs are a is as in the equation above.
% steps is the number of iteration steps used to integrate from t=0..10.
% 
% The interesting part here is that the laplacian Dxx does not change
% during the computation and it is sparse all the way.
% The approximation of the U(x,t) is sparse initially, but during the
% computation gets more and more dense, as the rows get filled during 
% the integration.
% end of time interval
  tN = 3;
  if (exist('N', 'var') ~= 1)
    N = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(300, N, 'N');
% set total steps
  N = 300;
  AM_CVar_0 = pi;
  AM_GLOBAL.sparsity.sparsity_get(N, 'N');
  AM_CVar_1 = N;
  AM_CVar_86 = ((2 * AM_CVar_0) / (AM_CVar_1 - 1));
  if (exist('h', 'var') ~= 1)
    h = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_86, h, 'h');
% set spacial step
  h = AM_CVar_86;
  AM_GLOBAL.sparsity.sparsity_get(h, 'h');
  AM_CVar_2 = h;
  AM_CVar_3 = pi;
  AM_GLOBAL.sparsity.sparsity_get(h, 'h');
  AM_CVar_4 = h;
  AM_CVar_87 = ([(AM_CVar_2 : AM_CVar_4 : (2 * AM_CVar_3))].');
  if (exist('X', 'var') ~= 1)
    X = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_87, X, 'X');
% set X axis points - the first point is ommitted (0)
  X = AM_CVar_87;
  AM_GLOBAL.sparsity.sparsity_get(X, 'X');
  AM_CVar_5 = X;
  AM_CVar_88 = (0 * AM_CVar_5);
  if (exist('U0', 'var') ~= 1)
    U0 = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_88, U0, 'U0');
% set initial condition
  U0 = AM_CVar_88;
  if (exist('U0', 'var') ~= 1)
    U0 = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(1, U0, 'U0');
  U0((round((end / 2.2)) : round((end / 1.8)))) = 1;
  AM_GLOBAL.sparsity.sparsity_get(N, 'N');
  AM_CVar_6 = N;
  AM_CVar_7 = Dxx(AM_CVar_6);
  AM_GLOBAL.sparsity.sparsity_get(h, 'h');
  AM_CVar_8 = h;
  AM_CVar_89 = (AM_CVar_7 / (AM_CVar_8 ^ 2));
  if (exist('D', 'var') ~= 1)
    D = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_89, D, 'D');
% set second spatial derivative matrix
  D = AM_CVar_89;
  AM_GLOBAL.sparsity.sparsity_get(tN, 'tN');
  AM_CVar_9 = tN;
  AM_GLOBAL.sparsity.sparsity_get(U0, 'U0');
  AM_CVar_10 = U0;
  AM_GLOBAL.sparsity.sparsity_get(steps, 'steps');
  AM_CVar_11 = steps;
  AM_CVar_12 = RungeKutta4(@F, [0, AM_CVar_9], AM_CVar_10, AM_CVar_11, 1);
  if (exist('W', 'var') ~= 1)
    W = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_12, W, 'W');
% find the solution
  W = AM_CVar_12;
  disp('computation finished');
  if AM_EntryPoint_1
    AM_GLOBAL = [];
  end
  function  [y] = F(AM_CVar_19, AM_CVar_18)
    if (exist('t', 'var') ~= 1)
      t = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_19, t, 't');
    t = AM_CVar_19;
    if (exist('u', 'var') ~= 1)
      u = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_18, u, 'u');
    u = AM_CVar_18;
    AM_GLOBAL.sparsity.sparsity_get(a, 'a');
    AM_CVar_15 = a;
    AM_GLOBAL.sparsity.sparsity_get(D, 'D');
    AM_CVar_16 = D;
    AM_GLOBAL.sparsity.sparsity_get(u, 'u');
    AM_CVar_17 = u;
    AM_CVar_90 = ((AM_CVar_15 * AM_CVar_16) * AM_CVar_17);
    if (exist('y', 'var') ~= 1)
      y = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_90, y, 'y');
% set rhs of ODE, i.e. Ut
    y = AM_CVar_90;
  end
end
function  [D] = Dxx(AM_CVar_25)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_1 = 0;
  else 
    AM_EntryPoint_1 = 1;
  end
  if (~isfield(AM_GLOBAL, 'sparsity'))
    AM_GLOBAL.sparsity = sparsity;
  end
  if (exist('N', 'var') ~= 1)
    N = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_25, N, 'N');
  N = AM_CVar_25;
  AM_GLOBAL.sparsity.sparsity_get(N, 'N');
  AM_CVar_20 = N;
  AM_CVar_21 = zeros((AM_CVar_20 - 3), 1);
  AM_GLOBAL.sparsity.sparsity_get(N, 'N');
  AM_CVar_22 = N;
  AM_CVar_23 = zeros(1, (AM_CVar_22 - 3));
  AM_CVar_24 = toeplitz([(-2); 1; AM_CVar_21], [(-2), 1, AM_CVar_23]);
  if (exist('D', 'var') ~= 1)
    D = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_24, D, 'D');
% creates a centered-in-space finite difference differentiation matrix,
% for the second spatial derivative.
% uses mixed boundary conditions, i.e. u(0)=u'(end)=0.
% takes N, the length of the vector, returns a (N-1)x(N-1) matrix Dxx,
% with U0 being ommitted because it is 0.
% note that 
%     Uxxi = (Ui-1 - 2*Ui + Ui+1)/h^2 for i=2..N-1
% and
%     Uxx2 = (U1 - 2U2 + U3)/h^2, U1 = 0
%  => Uxx2 = (-2U2 + U3)/h^2
% also,
%     Uxx(N) = (U(N-1) - 2U(N) + U(N+1))/h^2, 
%     Ux(N) = (U(N+1)-U(N-1))/2h = 0 => U(N+1) = U(N-1)
%  => Uxx(N) = (2U(N-1) - 2U(N)/h^2
% The matrix will not be divided by h^2, since it is unknown
% create matrix with -2 as diagonal, 1 above and below diagonal
  D = AM_CVar_24;
  if (exist('D', 'var') ~= 1)
    D = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(2, D, 'D');
% set nondiagonal in first row => enforce Neumann condition
  D(end, (end - 1)) = 2;
  if AM_EntryPoint_1
    AM_GLOBAL = [];
  end
end
function  [W] = RungeKutta4(AM_CVar_85, AM_CVar_84, AM_CVar_83, AM_CVar_82, AM_CVar_81)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_1 = 0;
  else 
    AM_EntryPoint_1 = 1;
  end
  if (~isfield(AM_GLOBAL, 'sparsity'))
    AM_GLOBAL.sparsity = sparsity;
  end
  if (exist('f', 'var') ~= 1)
    f = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_85, f, 'f');
  f = AM_CVar_85;
  if (exist('tspan', 'var') ~= 1)
    tspan = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_84, tspan, 'tspan');
  tspan = AM_CVar_84;
  if (exist('alpha', 'var') ~= 1)
    alpha = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_83, alpha, 'alpha');
  alpha = AM_CVar_83;
  if (exist('N', 'var') ~= 1)
    N = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_82, N, 'N');
  N = AM_CVar_82;
  if (exist('o', 'var') ~= 1)
    o = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_81, o, 'o');
  o = AM_CVar_81;
  AM_GLOBAL.sparsity.sparsity_indexedGet(tspan, 'tspan');
  AM_CVar_26 = tspan(1);
  if (exist('a', 'var') ~= 1)
    a = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_26, a, 'a');
% function output=RungeKutta4(f,tspan,alpha,N,o)
% This is an implementation of the Runge-Kutta 4 algorithm for solving the system
%   - y'=f(t,y) 
%   - y(a)=alpha
% over tspan=[a,b].
% f is a function, alpha is a vector (or scalar).
% N=number of steps to be taken to integrate.
% Note that y may be a vector valued function (column vectors).
% The output will be in the form of a matrix
% initial set up
% this is the initial time
  a = AM_CVar_26;
  AM_GLOBAL.sparsity.sparsity_indexedGet(tspan, 'tspan');
  AM_CVar_27 = tspan(2);
  if (exist('b', 'var') ~= 1)
    b = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_27, b, 'b');
% this is the final time
  b = AM_CVar_27;
  AM_GLOBAL.sparsity.sparsity_get(b, 'b');
  AM_CVar_28 = b;
  AM_GLOBAL.sparsity.sparsity_get(a, 'a');
  AM_CVar_29 = a;
  AM_GLOBAL.sparsity.sparsity_get(N, 'N');
  AM_CVar_30 = N;
  AM_CVar_94 = ((AM_CVar_28 - AM_CVar_29) / AM_CVar_30);
  if (exist('h', 'var') ~= 1)
    h = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_94, h, 'h');
% step size
  h = AM_CVar_94;
  AM_GLOBAL.sparsity.sparsity_get(alpha, 'alpha');
  AM_CVar_31 = alpha;
  AM_CVar_32 = length(AM_CVar_31);
  AM_GLOBAL.sparsity.sparsity_get(N, 'N');
  AM_CVar_33 = N;
  AM_CVar_34 = zeros(AM_CVar_32, (AM_CVar_33 + 1));
  if (exist('W', 'var') ~= 1)
    W = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_34, W, 'W');
% set output matrix
  W = AM_CVar_34;
  AM_GLOBAL.sparsity.sparsity_get(alpha, 'alpha');
  AM_CVar_35 = alpha;
  if (exist('W', 'var') ~= 1)
    W = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_35, W, 'W');
% set initial value
  W(:, 1) = AM_CVar_35;
  AM_GLOBAL.sparsity.sparsity_get(a, 'a');
  AM_CVar_36 = a;
  AM_GLOBAL.sparsity.sparsity_get(b, 'b');
  AM_CVar_37 = b;
  AM_GLOBAL.sparsity.sparsity_get(h, 'h');
  AM_CVar_38 = h;
  AM_CVar_95 = (AM_CVar_36 : AM_CVar_38 : AM_CVar_37);
  if (exist('t', 'var') ~= 1)
    t = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_95, t, 't');
% set times   
  t = AM_CVar_95;
  if (exist('j', 'var') ~= 1)
    j = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(1, j, 'j');
% iteration
  j = 1;
  AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_92 = j;
  AM_GLOBAL.sparsity.sparsity_get(N, 'N');
    AM_CVar_93 = N;
    AM_CVar_91 = (AM_CVar_92 <= AM_CVar_93);
  while AM_CVar_91
    AM_GLOBAL.sparsity.sparsity_get(h, 'h');
    AM_CVar_39 = h;
    AM_GLOBAL.sparsity.sparsity_get(f, 'f');
    AM_CVar_40 = f;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_41 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(t, 't');
    AM_CVar_42 = t(AM_CVar_41);
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_43 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(W, 'W');
    AM_CVar_44 = W(:, AM_CVar_43);
    AM_CVar_45 = feval(AM_CVar_40, AM_CVar_42, AM_CVar_44);
    AM_CVar_96 = (AM_CVar_39 * AM_CVar_45);
    if (exist('k1', 'var') ~= 1)
      k1 = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_96, k1, 'k1');
% set k1, k2, k3 and k4
    k1 = AM_CVar_96;
    AM_GLOBAL.sparsity.sparsity_get(h, 'h');
    AM_CVar_46 = h;
    AM_GLOBAL.sparsity.sparsity_get(f, 'f');
    AM_CVar_47 = f;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_48 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(t, 't');
    AM_CVar_49 = t(AM_CVar_48);
    AM_GLOBAL.sparsity.sparsity_get(h, 'h');
    AM_CVar_50 = h;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_51 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(W, 'W');
    AM_CVar_52 = W(:, AM_CVar_51);
    AM_GLOBAL.sparsity.sparsity_get(k1, 'k1');
    AM_CVar_53 = k1;
    AM_CVar_54 = feval(AM_CVar_47, (AM_CVar_49 + (AM_CVar_50 / 2)), (AM_CVar_52 + ((1 / 2) * AM_CVar_53)));
    AM_CVar_97 = (AM_CVar_46 * AM_CVar_54);
    if (exist('k2', 'var') ~= 1)
      k2 = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_97, k2, 'k2');
    k2 = AM_CVar_97;
    AM_GLOBAL.sparsity.sparsity_get(h, 'h');
    AM_CVar_55 = h;
    AM_GLOBAL.sparsity.sparsity_get(f, 'f');
    AM_CVar_56 = f;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_57 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(t, 't');
    AM_CVar_58 = t(AM_CVar_57);
    AM_GLOBAL.sparsity.sparsity_get(h, 'h');
    AM_CVar_59 = h;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_60 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(W, 'W');
    AM_CVar_61 = W(:, AM_CVar_60);
    AM_GLOBAL.sparsity.sparsity_get(k2, 'k2');
    AM_CVar_62 = k2;
    AM_CVar_63 = feval(AM_CVar_56, (AM_CVar_58 + (AM_CVar_59 / 2)), (AM_CVar_61 + ((1 / 2) * AM_CVar_62)));
    AM_CVar_98 = (AM_CVar_55 * AM_CVar_63);
    if (exist('k3', 'var') ~= 1)
      k3 = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_98, k3, 'k3');
    k3 = AM_CVar_98;
    AM_GLOBAL.sparsity.sparsity_get(h, 'h');
    AM_CVar_64 = h;
    AM_GLOBAL.sparsity.sparsity_get(f, 'f');
    AM_CVar_65 = f;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_66 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(t, 't');
    AM_CVar_67 = t(AM_CVar_66);
    AM_GLOBAL.sparsity.sparsity_get(h, 'h');
    AM_CVar_68 = h;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_69 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(W, 'W');
    AM_CVar_70 = W(:, AM_CVar_69);
    AM_GLOBAL.sparsity.sparsity_get(k3, 'k3');
    AM_CVar_71 = k3;
    AM_CVar_72 = feval(AM_CVar_65, (AM_CVar_67 + AM_CVar_68), (AM_CVar_70 + AM_CVar_71));
    AM_CVar_99 = (AM_CVar_64 * AM_CVar_72);
    if (exist('k4', 'var') ~= 1)
      k4 = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_99, k4, 'k4');
    k4 = AM_CVar_99;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_73 = j;
    AM_GLOBAL.sparsity.sparsity_indexedGet(W, 'W');
    AM_CVar_74 = W(:, AM_CVar_73);
    AM_GLOBAL.sparsity.sparsity_get(k1, 'k1');
    AM_CVar_75 = k1;
    AM_GLOBAL.sparsity.sparsity_get(k2, 'k2');
    AM_CVar_76 = k2;
    AM_GLOBAL.sparsity.sparsity_get(k3, 'k3');
    AM_CVar_77 = k3;
    AM_GLOBAL.sparsity.sparsity_get(k4, 'k4');
    AM_CVar_78 = k4;
    AM_CVar_100 = (AM_CVar_74 + ((1 / 6) * (((AM_CVar_75 + (2 * AM_CVar_76)) + (2 * AM_CVar_77)) + AM_CVar_78)));
    if (exist('W', 'var') ~= 1)
      W = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_100, W, 'W');
% Runge-Kutta 4 - step
    W(:, (j + 1)) = AM_CVar_100;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_79 = j;
    AM_CVar_101 = (AM_CVar_79 + 1);
    if (exist('j', 'var') ~= 1)
      j = [];
    end
    AM_GLOBAL.sparsity.sparsity_set(AM_CVar_101, j, 'j');
    j = AM_CVar_101;
    AM_GLOBAL.sparsity.sparsity_get(j, 'j');
    AM_CVar_92 = j;
    AM_GLOBAL.sparsity.sparsity_get(N, 'N');
    AM_CVar_93 = N;
    AM_CVar_91 = (AM_CVar_92 <= AM_CVar_93);
  end
  AM_GLOBAL.sparsity.sparsity_get(W, 'W');
  AM_CVar_80 = W;
  AM_CVar_102 = (AM_CVar_80');
  if (exist('W', 'var') ~= 1)
    W = [];
  end
  AM_GLOBAL.sparsity.sparsity_set(AM_CVar_102, W, 'W');
  W = AM_CVar_102;
  if AM_EntryPoint_1
    AM_GLOBAL = [];
  end
end

