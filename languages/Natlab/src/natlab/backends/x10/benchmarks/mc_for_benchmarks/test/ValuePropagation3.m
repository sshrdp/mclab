function drv_foo(n)
% This program lists the cases for comparing the size of array  


% Case 1: When a variable's value changed in the program
% it's incorrect to compare between them 
% e.g., 'n' used for size of B,  but two loops are totally uncomparable.

    % Following cases, 'n' has 2nd difinition, so two B(i) cannot compare
    n=round(10);
    for i=1:n		% 'i' is non-constant-variable
        B(i) = i;		% This case, 'n' should be used for B, since B has empty size
    end
    n=fix(3.2^3);    % 'n' has 2nd difinition
    
    for j=1:n*2		
        C(j) = j;		% This case 'n*2' should be used for C, since C has empty size
        B(j) = j;		% For B's size isn't comparable, since 'n' is not constant-variable
    end
    
    C(3*n)=3;		% Since 'n' is non-constant, this is un-comparable, 
    				% even in this case, it should be.
 
    % However,  'm' is a constant variable
    % E [m] is a correct evaluation.
    m = n
    for i=1:m,
        E(i) = i;
    end
    for i=1:m*3,	% E should be expanded to [m*3]
        E(i) = i;
    end
    
% Case 2: when comparing size, two different variables are also un-comparable 
% e.g., E(t), E(h) are totally uncomparable

    % Here 't' is non-constant, so 'E' new size cannot compare with old ones
    for t=1:n,
        E(t) = t;
    end

    % Here 'h' is constant, but it's not comparable
    h=fix(4.0);
    E(h) = 1;
	
% Case 3: when comparing size, variable vs constant are also un-comparable 
    G1(h) = 1;
    for i=1:10,
        F1(i) = i;
        G1(i) = i;
    end
    F1(h) = 1;

    % or another case
    G2(3) = 1;
    for i=1:h,
        F2(i) = i;
        G2(i) = i;
    end
    F2(3) = 1;

% Case 4: when comparing size, constants are comparable 
    for i=1:10,
        X(i) = i;
    end
    X(12) = 0;

% Case 5: when comparing size, using same variables are comparable 
    for i=1:h,
        Z(i) = i;
    end
    Z(h+2)=2;
    Z(h-1)= -1;
    Z(2*h-1)=4;
	
end