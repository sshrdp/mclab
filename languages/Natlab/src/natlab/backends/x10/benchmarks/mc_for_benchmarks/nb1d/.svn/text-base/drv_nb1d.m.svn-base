function [time, output, valid] = drv_nb1d(scale)
%%
%% Driver for the N-body problem coded using 1d arrays for the
%% displacement vectors.
%%

seed = 1;
t1 = clock;

n = round(scale^.4*30); %floor(28*rand);
dT = (.5)*0.0833;
T = (.5)*32.4362*sqrt(scale);

Rx = rand(n, 1,.1)*1000.23;
Ry = rand(n, 1,.4)*1000.23;
Rz = rand(n, 1,.9)*1000.23;

m = rand(n, 1,-.4)*345;

[Fx, Fy, Fz, Vx, Vy, Vz] = nbody1d(n, Rx, Ry, Rz, m, dT, T);

t2 = clock;

% Compute the running time in seconds
time = (t2-t1)*[0 0 86400 3600 60 1]';

% Store the benchmark output
output = {mean(Fx(:)) mean(Fy(:)) mean(Fz(:)) mean(Vx(:)) mean(Vy(:)) mean(Vz(:))};

% No validation performed
valid = 'N/A';

end

% making random deterministic
function M = rand(m,n,seed)
    seed = seed+m*n;
    M = zeros(m,n);
    for i = 1:m
        for j = 1:n
            M(i,j) = mod(seed,1);
            seed = seed+M(i,j)*sqrt(100)+sqrt(2);
        end 
    end
end

