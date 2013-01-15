function drv_foo(n)
% This program lists the cases for non-constant variables, 

    % <1>: unpredictable index variable's value-range
    % Following two cases, A(j), A2(j) cannot be correct predicted,
    % because Inner-'j' is inside the loop, value cannot be predicted.
    % In McFor, after transformation adds one more definition 
    % inside the loop, and the index 'j' will become new variable
    % so, that variable is non-constant variable
    
    len=round(10);
    % Inner-'j' is inside the loop
    for j=1:len
        j = j * 2;
        A(j) = j;
    end

    % Inner-'j' is inside the loop 
    for j=1:len
        A2(j) = j;
        j = j * 2;
    end
    
    % <2>: special predictable case for index variable's value-range
    % MATLAB uses for-each loop, 
    % where 'k' changes but # of iteration (variable 'i' ) doesn't.    
    % Thus,  'i' is non-constant
    %        BTW, a non-constant index variable means its value is not constant. ?????????
    % But 'k' has two definitions, so 'k' is non-constant variable.
    % In this case, D can be predict as size k, 
    % but the allocation should be just outside loop --[TODO]
    k=round(8.2);
    for i=1:k,
        k=k-1;
        D(i) = i;
    end

    % <3> special predictable case  for index variable's value-range
    % 'n' has 2nd difinition, thus 'i' is non-constant
    n=round(10);
    for i=1:n		% 'i' is non-constant-variable
        B(i) = i;		% This case 'n' should be used for B, since B has empty size
    end
    n=fix(3.2^3);    % 'n' has 2nd difinition
    
end