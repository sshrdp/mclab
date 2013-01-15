function [time, output, valid] = drv_crni(scale)
%%
%% Driver for the Crank-Nicholson solution to the
%% one-dimensional heat equation.
%%

t1 = clock;

a = 2.5; % a = rand*3;
b = 1.5; % b = 1.5;
c = 5; % c = 2;
m = 2300; %321; % n = floor(rand*1389);
n = 2300; %321; % n = floor(rand*529);

for time=1:scale
  U = crnich(a, b, c, n, m);
end

t2 = clock;

% Compute the running time in seconds
time = (t2-t1)*[0 0 86400 3600 60 1]';

% Store the benchmark output
output = {mean(U(:))};

% No validation performed
valid = 'N/A';

