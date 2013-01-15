function drv_foo()
% Test case for renaming variable from 'nb1d'
% Inside function rand2(), one of 'M' and 'm' should be renamed
    n = floor(28*rand);
    Rx = rand(n, 1,.1);
    F = foo();
end

function D = rand(m,n,seed)
    seed = seed+m*n;
    D = zeros(m,n);
end


function F=foo()
    F = rand(2,3,4);
end

