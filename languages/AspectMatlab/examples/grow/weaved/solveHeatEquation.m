function  [] = solveHeatEquation(AM_CVar_1, AM_CVar_0)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_1 = 0;
  else 
    AM_EntryPoint_1 = 1;
  end
  if (~isfield(AM_GLOBAL, 'grow'))
    AM_GLOBAL.grow = grow;
  end
  if (exist('a', 'var') ~= 1)
    a = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_1, a, 'a', 1, {});
  a = AM_CVar_1;
  if (exist('steps', 'var') ~= 1)
    steps = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_0, steps, 'steps', 1, {});
  steps = AM_CVar_0;
  if (exist('tN', 'var') ~= 1)
    tN = [];
  end
  AM_GLOBAL.grow.grow_set(3, tN, 'tN', 17, {});
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
  AM_GLOBAL.grow.grow_set(300, N, 'N', 18, {});
% set total steps
  N = 300;
  AM_CVar_11 = ((2 * pi) / (N - 1));
  if (exist('h', 'var') ~= 1)
    h = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_11, h, 'h', 19, {});
% set spacial step
  h = AM_CVar_11;
  AM_CVar_12 = ([(h : h : (2 * pi))].');
  if (exist('X', 'var') ~= 1)
    X = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_12, X, 'X', 20, {});
% set X axis points - the first point is ommitted (0)
  X = AM_CVar_12;
  AM_CVar_13 = (0 * X);
  if (exist('U0', 'var') ~= 1)
    U0 = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_13, U0, 'U0', 21, {});
% set initial condition
  U0 = AM_CVar_13;
  if (exist('U0', 'var') ~= 1)
    U0 = [];
  end
  AM_GLOBAL.grow.grow_set(1, U0, 'U0', 22, {});
  U0((round((end / 2.2)) : round((end / 1.8)))) = 1;
  AM_CVar_14 = (Dxx(N) / (h ^ 2));
  if (exist('D', 'var') ~= 1)
    D = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_14, D, 'D', 23, {});
% set second spatial derivative matrix
  D = AM_CVar_14;
  if (exist('W', 'var') ~= 1)
    W = [];
  end
  AM_GLOBAL.grow.grow_set(RungeKutta4(@F, [0, tN], U0, steps, 1), W, 'W', 27, {});
% find the solution
  W = RungeKutta4(@F, [0, tN], U0, steps, 1);
  disp('computation finished');
  if AM_EntryPoint_1
    AM_GLOBAL = [];
  end
  function  [y] = F(AM_CVar_3, AM_CVar_2)
    if (exist('t', 'var') ~= 1)
      t = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_3, t, 't', 24, {});
    t = AM_CVar_3;
    if (exist('u', 'var') ~= 1)
      u = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_2, u, 'u', 24, {});
    u = AM_CVar_2;
    AM_CVar_15 = ((a * D) * u);
    if (exist('y', 'var') ~= 1)
      y = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_15, y, 'y', 25, {});
% set rhs of ODE, i.e. Ut
    y = AM_CVar_15;
  end
end
function  [D] = Dxx(AM_CVar_4)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_1 = 0;
  else 
    AM_EntryPoint_1 = 1;
  end
  if (~isfield(AM_GLOBAL, 'grow'))
    AM_GLOBAL.grow = grow;
  end
  if (exist('N', 'var') ~= 1)
    N = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_4, N, 'N', 33, {});
  N = AM_CVar_4;
  if (exist('D', 'var') ~= 1)
    D = [];
  end
  AM_GLOBAL.grow.grow_set(toeplitz([(-2); 1; zeros((N - 3), 1)], [(-2), 1, zeros(1, (N - 3))]), D, 'D', 50, {});
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
  D = toeplitz([(-2); 1; zeros((N - 3), 1)], [(-2), 1, zeros(1, (N - 3))]);
  if (exist('D', 'var') ~= 1)
    D = [];
  end
  AM_GLOBAL.grow.grow_set(2, D, 'D', 52, {});
% set nondiagonal in first row => enforce Neumann condition
  D(end, (end - 1)) = 2;
  if AM_EntryPoint_1
    AM_GLOBAL = [];
  end
end
function  [W] = RungeKutta4(AM_CVar_10, AM_CVar_9, AM_CVar_8, AM_CVar_7, AM_CVar_6)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_1 = 0;
  else 
    AM_EntryPoint_1 = 1;
  end
  if (~isfield(AM_GLOBAL, 'grow'))
    AM_GLOBAL.grow = grow;
  end
  if (exist('f', 'var') ~= 1)
    f = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_10, f, 'f', 56, {});
  f = AM_CVar_10;
  if (exist('tspan', 'var') ~= 1)
    tspan = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_9, tspan, 'tspan', 56, {});
  tspan = AM_CVar_9;
  if (exist('alpha', 'var') ~= 1)
    alpha = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_8, alpha, 'alpha', 56, {});
  alpha = AM_CVar_8;
  if (exist('N', 'var') ~= 1)
    N = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_7, N, 'N', 56, {});
  N = AM_CVar_7;
  if (exist('o', 'var') ~= 1)
    o = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_6, o, 'o', 56, {});
  o = AM_CVar_6;
  if (exist('a', 'var') ~= 1)
    a = [];
  end
  AM_GLOBAL.grow.grow_set(tspan(1), a, 'a', 68, {});
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
  a = tspan(1);
  if (exist('b', 'var') ~= 1)
    b = [];
  end
  AM_GLOBAL.grow.grow_set(tspan(2), b, 'b', 69, {});
% this is the final time
  b = tspan(2);
  AM_CVar_16 = ((b - a) / N);
  if (exist('h', 'var') ~= 1)
    h = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_16, h, 'h', 70, {});
% step size
  h = AM_CVar_16;
  if (exist('W', 'var') ~= 1)
    W = [];
  end
  AM_GLOBAL.grow.grow_set(zeros(length(alpha), (N + 1)), W, 'W', 71, {});
% set output matrix
  W = zeros(length(alpha), (N + 1));
  if (exist('W', 'var') ~= 1)
    W = [];
  end
  AM_GLOBAL.grow.grow_set(alpha, W, 'W', 72, {1:builtin('end',W,1,2), 1});
% set initial value
  W(:, 1) = alpha;
  AM_CVar_17 = (a : h : b);
  if (exist('t', 'var') ~= 1)
    t = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_17, t, 't', 73, {});
% set times   
  t = AM_CVar_17;
  if (exist('j', 'var') ~= 1)
    j = [];
  end
  AM_GLOBAL.grow.grow_set(1, j, 'j', 75, {});
% iteration
  j = 1;
    AM_CVar_5 = (j <= N);
  while AM_CVar_5
    AM_CVar_18 = (h * feval(f, t(j), W(:, j)));
    if (exist('k1', 'var') ~= 1)
      k1 = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_18, k1, 'k1', 78, {});
% set k1, k2, k3 and k4
    k1 = AM_CVar_18;
    AM_CVar_19 = (h * feval(f, (t(j) + (h / 2)), (W(:, j) + ((1 / 2) * k1))));
    if (exist('k2', 'var') ~= 1)
      k2 = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_19, k2, 'k2', 79, {});
    k2 = AM_CVar_19;
    AM_CVar_20 = (h * feval(f, (t(j) + (h / 2)), (W(:, j) + ((1 / 2) * k2))));
    if (exist('k3', 'var') ~= 1)
      k3 = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_20, k3, 'k3', 80, {});
    k3 = AM_CVar_20;
    AM_CVar_21 = (h * feval(f, (t(j) + h), (W(:, j) + k3)));
    if (exist('k4', 'var') ~= 1)
      k4 = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_21, k4, 'k4', 81, {});
    k4 = AM_CVar_21;
    AM_CVar_22 = (W(:, j) + ((1 / 6) * (((k1 + (2 * k2)) + (2 * k3)) + k4)));
    if (exist('W', 'var') ~= 1)
      W = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_22, W, 'W', 83, {1:builtin('end',W,1,2), (j + 1)});
% Runge-Kutta 4 - step
    W(:, (j + 1)) = AM_CVar_22;
    AM_CVar_23 = (j + 1);
    if (exist('j', 'var') ~= 1)
      j = [];
    end
    AM_GLOBAL.grow.grow_set(AM_CVar_23, j, 'j', 84, {});
    j = AM_CVar_23;
    AM_CVar_5 = (j <= N);
  end
  AM_CVar_24 = (W');
  if (exist('W', 'var') ~= 1)
    W = [];
  end
  AM_GLOBAL.grow.grow_set(AM_CVar_24, W, 'W', 86, {});
  W = AM_CVar_24;
  if AM_EntryPoint_1
    AM_GLOBAL = [];
  end
end
