function [time, output, valid] = drv_fiff(scale)
%%
%% Driver for finite-difference solution to the wave equation.
%%

t1 = clock;

a = 2.5;
b = 1.5;
c = 0.5;
n = 3500;
m = 3500;

for time=1:scale
  U = finediff(a, b, c, n, m);
end

t2 = clock;

% Compute the running time in seconds
time = (t2-t1)*[0 0 86400 3600 60 1]';

% Store the benchmark output
output = {mean(U(:))};

% No validation performed
valid = 'N/A';

