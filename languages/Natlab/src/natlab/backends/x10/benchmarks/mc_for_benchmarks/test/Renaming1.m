function drv_foo()
    n=10
    
    % this case, loop variable 't' isn't as same as outside definition.
    % so rename the loop variable 't'
    for t = (1 : n)
        A(t) = t;
    end
    t = (2.5 + t)
    
    
    for t = (1 : n)
        A(t) = t;
    end
    sum = 0;
    for i=1:10
        sum = sum + t;
        t = (2.5 + t)
    end
    if(t>4)
        t = (2.5 + t)
    end    
    
    % Case #3
    % Rename the assignment on 'i' inside the loop
    sum = 0;
    for i=1:10
        i = i * 2;
        sum = sum + i;
    end

    % Rename the assignment on 'j' inside the loop
    for j=1:n
        j = j * 2;
        A(j) = j;
    end

    % Type-#2 Rename the loop variable 'j', since this is 2nd time use
    % Rename the assignment on 'j' inside the loop
    for j=1:n
        A(j) = j;
        j = j * 2;
    end

   % This is very ambigious
    sum = 0;
    cnt = 0; 
    z = 0;
    for i=1:10
        cnt = cnt + i;
        if(i<5)
               z = z + i;
            i = i * 2;
        end
        sum = sum + i;
    end
end
