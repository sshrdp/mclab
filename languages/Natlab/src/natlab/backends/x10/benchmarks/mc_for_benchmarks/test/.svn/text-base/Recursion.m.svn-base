function drv_foo()
% Recursive Functions 
	fprintf('fib1(10)=%d, %d\n', fib1(10), fib1(9));
	
	[f1, f2] = fib2(10);
	fprintf('fib2(10)=%d, %d\n', f1, f2);

	fprintf('Factorial(10)=%d\n',Factorial(10));
end

function y = fib1(n)
	if(n==0 || n==1)
		y = n;
	else
		y = fib1(n-1) + fib1(n-2);
	end
end

function [y, y1] = fib2(n)
% y = fib(n); y1=fib(n-1)
	if(n==0 || n==1)
		y = n;
		y1 = 0;
	else
		[y1, y] = fib2(n-1);
		y = y + y1;
	end
end

function y=Factorial(n)
	if(n==1)
		y = 1;
	else
		y = n * Factorial(n-1);
	end
end