function drv_foo()
% This test case shows
% transform integer expression to double expression
% in different situations

	% transform integer arguments to double, 
	n = round(.4*30); 
	f = sqrt(n)
	f = sqrt(100)
	disp(f)
	
	%  transform integer division to double division 
	ya = -1;
	yb = 1;
	xa = -1.5;
	xb = .5;
	dx = (xb-xa)/xa;
	% dy should be inferred as REAL, and RHS transform to (((yb - ya) + 0.0) / yb);
	dy = (yb-ya)/yb;
	cx = xa+xb*dx+i*(ya+yb*dy)
	disp(cx)

	% complex operations
	z1 = f+cx
	x3 = z1 + i;
	
end
