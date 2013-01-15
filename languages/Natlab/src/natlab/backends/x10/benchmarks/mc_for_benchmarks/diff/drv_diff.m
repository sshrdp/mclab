function [time, output, valid] = drv_diff(scale)
%%
%% Driver for the diffraction pattern calculator.
%%

t1 = clock;

CELLS = 2;
SLITSIZE1 = 1e-5;
SLITSIZE2 = 1e-5;
T1 = 1;
T2 = 0;
for time = 1:scale
  mag = diffraction(CELLS, SLITSIZE1, SLITSIZE2, T1, T2);
end

t2 = clock;

% Compute the running time in seconds
time = (t2-t1)*[0 0 86400 3600 60 1]';

% Store the benchmark output
output = {mean(mag(:))};

% No validation performed
valid = 'N/A';

