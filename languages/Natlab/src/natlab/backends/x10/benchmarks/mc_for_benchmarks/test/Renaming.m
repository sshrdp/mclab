function drv_foo()
% Test case for renaming variable from 'nb1d'
% Inside function rand2(), one of 'M' and 'm' should be renamed
    n = floor(28*rand);
    Rx = rand2(n, 1,.1);
    
end

function M = rand2(m,n,seed)
    seed = seed+m*n;
    M = zeros(m,n);
    for i = 1:m
        for j = 1:n
            M(i,j) = mod(seed,1);
            seed = seed+M(i,j)*sqrt(100)+sqrt(2);
        end 
    end
end
