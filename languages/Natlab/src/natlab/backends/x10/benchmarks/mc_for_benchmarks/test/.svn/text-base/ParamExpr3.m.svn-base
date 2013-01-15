function drv_foo()
% This test case shows the two popular way 
% in MATLAB to measure the eclipsed time 
% function clock, tic, toc

	tic();
	timing = toc();
	disp(timing);

	t1=clock
	t2=clock
	fprintf(1, 'Timing = %f\n', (t2-t1)*[0, 0, 86400, 3600, 60, 1]');
	
	for time = (1 : 10)
	end
	time = ((t2 - t1) * ([0, 0, 86400, 3600, 60, 1]'));
	disp(time);
end
