function drv_foo()
% This test case shows
% Colon-expression in index

	n=floor(6.0);
	m=floor(5.0);
%	result = foo(n,m);
%	disp(result)
%end

%function result = foo(n,m)	
	U=zeros(n,m+1);
	X=ones(1,n);
	for j1 = 2:m
		U((1 : n), j1) = X';
		Y=U((1 : n), j1);
	end
	z=U(j1,j1)
	p=U(1,2)
	q=U(1,j1)
	r=U(1,:)
	s=U(:,2)
	t=U(1:n,2)
	v=U(1,2:m)
	
	result=m+n
	disp(Y)
	disp(z)
	disp(p)
	disp(q)
	disp(r)
	disp(s)
	disp(t)
	disp(v)
end
