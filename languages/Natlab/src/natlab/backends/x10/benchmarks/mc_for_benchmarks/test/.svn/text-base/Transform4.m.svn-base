function drv_foo()
% This test case shows
% Function transform to Subroutine, calling side also transform
% in different situations

	% In this case "Removing Temporary" transformation will remove original function call, cause problem
	x=1
	y=1
       set(y+1,x+1) = iterations(xa+x*dx+i*(ya+y*dy),Nmax);


	% Transform Complex()
	% xa+x*dx+i*(ya+y*dy)
	
	% -----------------------------------------------------------
	% in following linear-indexing case, B(i), it's difficult ?? i=16
	% Transformation should skip following 
	% linear indexing
	B = zeros(4,4);
	for i=1:3:16
		B(i) = -9;
	end
	disp(B)
	
	
	
end


function out = iterations(x,max)
  c = x;
  i = 0;
  while(abs(x) < 2 & i < max)
    x = x*x + c;
    i = i+1;
  end
  out = i;
end




function drv_foo()

s1 = ['Once upon a midnight dreary']
s2 = ['while I pondered weak and weary']

n1 = size(s1, 2);
n2 = size(s2, 2);

D = zeros(n1+1, n2+1);

% This example show, using temporary variable as index variables are very important
A = zeros(floor(2.5), floor(3.1))
end
