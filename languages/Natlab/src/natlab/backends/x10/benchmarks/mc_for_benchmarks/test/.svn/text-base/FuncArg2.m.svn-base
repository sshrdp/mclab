function drv_foo(scale)
% This test case shows:
% Solving conflict of variable's names in case-insensitive Fortran
% function's parameters
	n = floor(scale)
	N=floor(scale^3)
	set = zeros(N,n)
	
	% passing them to a simple function call
	M = foo(N, n, scale)
	F = seidel(set, N,n)
end

% Because there may have many defintion on same variable, i.e.'M' 									
% Everytime after renaming, it needs to exit that function, 
% and rebuild code-nodes and  def/use boxes 
% then redo building symbol table.

function M = foo(n,m,seed)
    seed = seed+m*n;
    M = zeros(m,n);
    for i = 1:m
        for j = 1:n
            M(i,j) = mod(seed,1.0);
            seed = seed+M(i,j)*sqrt(100)+sqrt(2);
        end 
    end
end

% From 'capr',
% assume the return 'f' is same as input 'f'
function f = seidel(f, n, m)
	f=2
end