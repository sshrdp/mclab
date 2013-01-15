function [time, output, valid] = drv_fdtd(scale)
%%
%% Driver for 3D FDTD of a hexahedral cavity with conducting walls.
%%

t1 = clock;

% Parameter initialization.
Lx = .05; Ly = .04; Lz = .03; % Cavity dimensions in meters.
Nx = 25; Ny = 20; Nz = 15; % Number of cells in each direction.

% Because norm isn't currently supported,
% nrm = norm([Nx/Lx Ny/Ly Nz/Lz]) is plugged in.
nrm = 866.0254;

Nt = scale*200; % Number of time steps.

[Ex, Ey, Ez, Hx, Hy, Hz, Ets] = fdtd(Lx, Ly, Lz, Nx, Ny, Nz, nrm, Nt);

t2 = clock;

% Compute the running time in seconds
time = (t2-t1)*[0 0 86400 3600 60 1]';

% Store the benchmark output
output = {mean(Ex(:)) mean(Ey(:)) mean(Ez(:)) mean(Hx(:)) mean(Hy(:)) mean(Hz(:)) mean(Ets(:))};

% No validation performed
valid = 'N/A';

