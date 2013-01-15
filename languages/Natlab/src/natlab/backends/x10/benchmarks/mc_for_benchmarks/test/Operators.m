function drv_foo()
% This test case shows:
% operators supported by McFor

	m = round(.4*10); 
	n = round(.5*10); 
	p = round(.6*10); 
	A = randn(m,p)
	B = randn(p,n)
	
	
	% Matrix multiplication's result matrix is at least 'double' 
	C = A * B

end
