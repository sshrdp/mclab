function drv_foo()
% Two forms of complex number 

	x1 = 2+3i; 
	x2 = 2+3.1j; 
 
	x3 = 2 + i; 
	x4 = 2 + 3*j; 
 
	n = 10; 
	z0 = i;
	z1 = n*i;
	z2 = 4+ n*j; 


	y0 = 10;
	% The first two variables are complex
	y1 = 2 + i; 
	y2 = 1 + 3*j; 
 
 	% Addition of two complex numbers 
 	y3 = y1 + i;

	% t1, t2 are integers 
	for i=1:3
		t1 = 2*i;
		j = i;
		t2 = 4+ 3*j; 
	end
	
	% Type inference on the complex number 
	n=10
	y0 = n + y1;
	y5 = y2 + t2;
	y6 = y2;
	y7 = y1+3*i;
	y8 = y2+j;
	
	% this is an integer
	t3 = n + 2*j;
end
