function drv_foo(scale, m)
% This test case shows:
% How to converts different numbers of main
% function's parameters

	N=floor(scale^3)
	set = zeros(N,floor(m))
	
	% passing n,t to a simple function call
	foo(N, N)
	
	B=bar(N,N+1);
end

function foo(n,t)
	A=zeros(n,t)
end

function B=bar(x,y)
	B=zeros(x,y)
end

