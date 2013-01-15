function drv_foo()
% This test case shows
% Column-vector and row-vector's basic operations 
% Linear-indexing on vectors

	n=floor(4.0);
	A=zeros(n,1);
	A(3)=1
	B=zeros(1,n);
	B(2)=2
	m=n-1
	A(m)=3
	B(m)=4
	
	x=1
for i=1:m+1
	A(i) = x
	x=B(n-i)
end	
	
	% automatically transform vector assignment
	A=1:n
	B=n:-1:1
	
	% automatically transform vectors to two-dimensions 
	% to accommodate the mutiplicate and transpose operations
	C=1:n
	D=n:-1:1
	E=C*D'
	disp(A)
	disp(B)
	disp(C)
	disp(D)
	disp(E)
end
