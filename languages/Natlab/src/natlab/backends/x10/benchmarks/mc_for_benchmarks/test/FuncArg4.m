function drv_foo()
% This test case shows:
% The sub-function called by an argument
% which type changed after calling 
% taken from 'capr'
	N=floor(2^1);
	set = ones(N,N);
	set = seidel(set,N);

	set2 = ones(N,N);
	result = seidel2(set2,N);
end
function f=seidel(f,n)
   f(1,1) = 1.1;
end


function R=seidel2(set,n)
	% using bridge variable to avoid the change on the input parameters
   set(1,1) = 1.1;
   R = set
end

