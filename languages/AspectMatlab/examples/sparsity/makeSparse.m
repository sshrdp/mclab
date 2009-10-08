%%
% this aspect makes all variables listed in the patterns sparse by
% replaxing any assignment 
% var = exp;
% with
% var = sparse(exp);
% make sure that all variables thus changed are matrizes.

aspect test

properties
count = 0;
end

methods
end

% list of variables forced to be sparse
patterns
arrayseta : set(*, *.a);
end

% TODO - how to collect 'set's together
actions
countseta : before arrayseta : (newVal)

% newVal = sparse(newVal)

end
end

end


