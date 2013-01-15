
function [time, output, valid] = drv_adpt(scale)
%
% Driver for adaptive quadrature using Simpson's rule.
%


t1 = clock;

a = -1;
b = 6;
sz_guess = 1;
tol = 4e-13;
for i = 1:scale
  [SRmat, quad, err] = adapt(a, b, sz_guess, tol);
end
t2 = clock;

% Compute the running time in seconds
time = (t2-t1)*[0, 0, 86400, 3600, 60, 1]';

% Store the benchmark output
output = {mean(SRmat(:)), mean(quad(:)), mean(err(:))};

% No validation performed
valid = 'N/A';

end