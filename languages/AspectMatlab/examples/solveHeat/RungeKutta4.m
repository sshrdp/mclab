% function output=RungeKutta4(f,tspan,alpha,N,o)
% This is an implementation of the Runge-Kutta 4 algorithm for solving the system
%   - y'=f(t,y) 
%   - y(a)=alpha
% over tspan=[a,b].
% f is a function, alpha is a vector (or scalar).
% N=number of steps to be taken to integrate.
% Note that y may be a vector valued function (column vectors).
% The output will be in the form of a matrix. The first column is the vector
% of times (to,t1,..,tN), and the other columns are the values of y at ti.
%
%
% Kathryn Pelham, Anton Dubrau
% 08.03.08 - 09.09.09
function [t W] = RungeKutta4(f,tspan,alpha,N,o)
   % initial set up
   a=tspan(1); % this is the initial time
   b=tspan(2); % this is the final time
   h=(b-a)/N; % step size
   W = zeros(length(alpha),N+1); % set output matrix
   W(:,1)=alpha; % set initial value
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
       W(:,j+1)=W(:,j)+ (1/6)*(k1 + 2*k2 + 2*k3 + k4);
       j = j+1;
   end
   t=t';
   W=W';
end


