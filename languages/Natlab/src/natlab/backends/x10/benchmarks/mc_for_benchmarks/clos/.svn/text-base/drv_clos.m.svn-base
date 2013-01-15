function [time, output, valid] = drv_clos(scale)
%%
%% Driver for the transitive closure of a directed graph.
%%

t1 = clock;

N = 450;

for time = 1:scale
  [t2, B] = closure(N);
end

t3 = clock;

% Compute the running time in seconds
time = (t3-t1)*[0 0 86400 3600 60 1]';

% Store the benchmark output
output = {mean(B(:))};

% No validation performed
valid = 'N/A';

