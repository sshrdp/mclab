function drv_foo()
% This test case shows:
% The calling sequence from 
% drv_foo()->foo()->iterations()

	N=floor(100^1)
	set = foo(N)
	disp(size(set))
end
function set=foo(side)
  for x=0:side-1
     for y=0:side-1
        set(y+1,x+1) =  iterations(x,y);
     end
  end
	B = seidel(set, side);
end
function out = iterations(x,max)
  out = x + max;
end

% The 2nd 'f' will be renamed
function f = seidel(f, n)
    f(1,1)=0;
end
