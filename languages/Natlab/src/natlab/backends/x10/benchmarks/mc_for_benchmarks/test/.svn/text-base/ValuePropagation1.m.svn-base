function drv_foo(n)
% This program lists the cases for constant variables

    n = fix(2.2^2);    % This line should be remove, 'n' is input parameter --TODO
    
    k = 1;            % [1]
    m = 2*n;        % [2*n]
    d = 3*m+1;        % [3*2*n+1]
    e = fix(1.2^3);    % [e]
    
    for j = 1:m,        % [1, 2*n]
        A(j)=j;
    end
    j = 2.5 + j        % this definition doesn't affect the loop variable 'j'
    
    for i = 1:m,    % [1, 2*n]
        B(i)=i;
    end
    
    % size of B should be comparable, since 'm' is constant-variable
    for i = 1:d,    % [1, 3*2*n+1]
        B(i)=i;
    end

    for i=1:3:16
        C(i) = -9;
    end
    
end