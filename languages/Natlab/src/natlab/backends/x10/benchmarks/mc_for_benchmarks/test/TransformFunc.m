function drv_foo()
% This test case shows
% When function is transformed into subroutine, 
% caller side also needs proper transformation.
% And dynamic allocation code must correctly put 
% in front of the first use of that variable

	n=floor(2.5)
	A = 1: n
	
	B = foo(A,n)
	
	C = zeros(n, 3)
	C(:, 1) =foo(A, n)

end

function B=foo(A, n)
	B=A 
end