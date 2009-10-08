function solveHeatEquation(a,steps)
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
  tN= 3; % end of time interval
  N = 300; % set total steps
  h = 2*pi/(N-1); % set spacial step
  X = [h:h:2*pi].'; % set X axis points - the first point is ommitted (0)
  U0 = 0*X; % set initial condition
  U0(round(end/2.2):round(end/1.8)) = 1;
  D = (Dxx(N)/h^2); % set second spatial derivative matrix
  function y = F(t,u) % set rhs of ODE, i.e. Ut
    y = a*D*u;
  end
  W = RungeKutta4(@F,[0, tN],U0,steps,1); % find the solution
  disp('computation finished');
end   



function D=Dxx(N)
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
   D= toeplitz([-2;1;zeros(N-3,1)],[-2,1,zeros(1,N-3)]);
   % set nondiagonal in first row => enforce Neumann condition
   D(end,end-1) = 2;
end


function  W = RungeKutta4(f,tspan,alpha,N,o)
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
   a=tspan(1); % this is the initial time
   b=tspan(2); % this is the final time
   h=(b-a)/N; % step size
   W = zeros(length(alpha),N+1); % set output matrix
   W(1:size(W,1),1)=alpha; % set initial value
   t = a:h:b; % set times   
   % iteration
   j = 1;
   while (j <= N)
       % set k1, k2, k3 and k4
       k1=h*(feval(f, t(j), W(:,j)));
       k2=h*(feval(f, (t(j) + (h/2)), (W(:,j) + ((1/2)*(k1)))));
       k3=h*(feval(f, (t(j) + (h/2)), (W(:,j) + ((1/2)*(k2)))));
       k4=h*(feval(f, (t(j)+h), (W(:,j) + k3)));
       % Runge-Kutta 4 - step
       W(1:size(W,1),j+1)=W(:,j)+ (1/6)*(k1 + 2*k2 + 2*k3 + k4);
       j = j+1;
   end
   W=W';
end


