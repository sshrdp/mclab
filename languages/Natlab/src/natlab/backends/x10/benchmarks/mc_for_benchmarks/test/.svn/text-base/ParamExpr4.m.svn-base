function drv_foo()
% This test case shows:
% two cases of Array-indexing

	n = round(4*1.0); 
%	A=foo(n)
%function A=foo(n)
	R = zeros(n, 3);
	ii = ones(n, n);
	jj = ones(n, n);
	dr = zeros(n, n, 3);

	% (1) array indexing, on RHS
	dr(:) = R(jj, :)-R(ii, :);

	kk = (1:n);
	rr = zeros(n, n);

	% (2) array indexing, on LHS
	rr(kk)=1.0;
	
	disp(size(dr))
	
	A=rr
%end
	disp(mean(A(:)))
	disp(mean(A))

	% it's necessary to convert sum() into double 
	disp(sum(A(:)))
	disp(sum(A))
	z = sum(A(:))/size(A)
	disp(z)
end

