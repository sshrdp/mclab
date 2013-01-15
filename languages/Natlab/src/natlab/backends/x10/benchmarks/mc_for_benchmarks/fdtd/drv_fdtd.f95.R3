MODULE Mod_drv_fdtd
CONTAINS

SUBROUTINE fdtd(Lx, Ly, Lz, Nx, Ny, Nz, nrm, Nt, Ex, Ey, Ez, Hx, Hy, Hz, Ets)
DOUBLE PRECISION :: Lx
DOUBLE PRECISION :: Ly
DOUBLE PRECISION :: Lz
INTEGER :: Nx
INTEGER :: Ny
INTEGER :: Nz
DOUBLE PRECISION :: nrm
INTEGER :: Nt
DOUBLE PRECISION, DIMENSION(Nx, (Ny+1), (Nz+1)) :: Ex
DOUBLE PRECISION, DIMENSION((Nx+1), Ny, (Nz+1)) :: Ey
DOUBLE PRECISION, DIMENSION((Nx+1), (Ny+1), Nz) :: Ez
DOUBLE PRECISION, DIMENSION((Nx+1), Ny, Nz) :: Hx
DOUBLE PRECISION, DIMENSION(Nx, (Ny+1), Nz) :: Hy
DOUBLE PRECISION, DIMENSION(Nx, Ny, (Nz+1)) :: Hz
DOUBLE PRECISION, DIMENSION(Nt, 3) :: Ets
DOUBLE PRECISION :: Cx
DOUBLE PRECISION :: Cy
DOUBLE PRECISION :: Cz
DOUBLE PRECISION :: tmpvar60
DOUBLE PRECISION :: eps0
DOUBLE PRECISION :: mu0
DOUBLE PRECISION :: tmpvar58
DOUBLE PRECISION :: Dt
DOUBLE PRECISION :: tmpvar59
DOUBLE PRECISION :: pi
INTEGER :: n
INTEGER :: c0

pi = 3.14159265359





























































! ...
! %-----------------------------------------------------------------------
! %
! %	This function M-file applies the Finite Difference
! %	Time Domain (FDTD) technique on a hexahedral cavity
! %	with conducting walls. FDTD is a powerful tool for
! %	transient electromagnetic analysis. 
! %
! %	Invocation:
! %		>> [Ex, Ey, Ez, Hx, Hy, Hz, Ets] = ...
! %		   fdtd(Lx, Ly, Lz, Nx, Ny, Nz)
! %
! %		where
! %
! %		i. Lx is the cavity dimension in meters along the
! %		   x-axis,
! %
! %		i. Ly is the cavity dimension in meters along the
! %		   y-axis,
! %
! %		i. Lz is the cavity dimension in meters along the
! %		   z-axis,
! %
! %		i. Nx is the number of cells along the x-axis,
! %
! %		i. Ny is the number of cells along the y-axis,
! %
! %		i. Nz is the number of cells along the z-axis,
! %
! %		i. nrm is norm([Nx/Lx Ny/Ly Nz/Lz]),
! %
! %		i. Nt is the number of time steps,
! %
! %		o. Ex is the x-component of the electric field.
! %
! %		o. Ey is the y-component of the electric field.
! %
! %		o. Ez is the z-component of the electric field.
! %
! %		o. Hx is the x-component of the magnetic field.
! %
! %		o. Hy is the y-component of the magnetic field.
! %
! %		o. Hz is the z-component of the magnetic field.
! %
! %		o. Ets is the time signal array.
! %
! %	Requirements:
! %		None.
! %
! %	Examples:
! %		>> [Ex, Ey, Ez, Hx, Hy, Hz, Ets] = ...
! %		   fdtd(.05, .04, .03, 25, 20, 15)
! %
! %	Source:
! %		Computational Electromagnetics - EEK 170 course at
! %		http://www.elmagn.chalmers.se/courses/CEM/.
! %
! %-----------------------------------------------------------------------
! % Physical constants.
! % Permittivity of vacuum.
eps0 = 8.8541878e-12;

! % Permeability of vacuum.
mu0 = (4e-7 * pi);

! % Speed of light in vacuum.
c0 = 299792458;
Cx = (Nx / Lx);
Cy = (Ny / Ly);

! % Inverse cell dimensions.
Cz = (Nz / Lz);

! % Time step.
Dt = (1 / (c0 * nrm));

! % Allocate field arrays.
Ex = 0;
Ey = 0;
Ez = 0;
Hx = 0;
Hy = 0;
Hz = 0;

! % Allocate time signals.
Ets = 0;

! % Initialize fields (near but not on the boundary).
Ex(1, 2, 2) = 1;
Ey(2, 1, 2) = 2;
Ez(2, 2, 1) = 3;
DO n = 1 , Nt
	
	
	
	
	
	
	
	
	
	! % Time stepping.
! % Update H everywhere.
! ...
Hx = (Hx + ((Dt / mu0) * (((Ey(:, :, 2 : (Nz + 1)) - Ey(:, :, 1 : Nz)) * Cz) - ((Ez(:, 2 :&
& (Ny + 1), :) - Ez(:, 1 : Ny, :)) * Cy))));
	! ...
Hy = (Hy + ((Dt / mu0) * (((Ez(2 : (Nx + 1), :, :) - Ez(1 : Nx, :, :)) * Cx) - ((Ex(:, :, &
&2 : (Nz + 1)) - Ex(:, :, 1 : Nz)) * Cz))));
	! ...
Hz = (Hz + ((Dt / mu0) * (((Ex(:, 2 : (Ny + 1), :) - Ex(:, 1 : Ny, :)) * Cy) - ((Ey(2 : (N&
&x + 1), :, :) - Ey(1 : Nx, :, :)) * Cx))));
	
	! % Update E everywhere except on boundary.
! ...
! ...
Ex(:, 2 : Ny, 2 : Nz) = (Ex(:, 2 : Ny, 2 : Nz) + ((Dt / eps0) * (((Hz(:, 2 : Ny, 2 : Nz) -&
& Hz(:, 1 : (Ny - 1), 2 : Nz)) * Cy) - ((Hy(:, 2 : Ny, 2 : Nz) - Hy(:, 2 : Ny, 1 : (Nz - 1)&
&)) * Cz))));
	! ...
! ...
Ey(2 : Nx, :, 2 : Nz) = (Ey(2 : Nx, :, 2 : Nz) + ((Dt / eps0) * (((Hx(2 : Nx, :, 2 : Nz) -&
& Hx(2 : Nx, :, 1 : (Nz - 1))) * Cz) - ((Hz(2 : Nx, :, 2 : Nz) - Hz(1 : (Nx - 1), :, 2 : Nz&
&)) * Cx))));
	! ...
! ...
Ez(2 : Nx, 2 : Ny, :) = (Ez(2 : Nx, 2 : Ny, :) + ((Dt / eps0) * (((Hy(2 : Nx, 2 : Ny, :) -&
& Hy(1 : (Nx - 1), 2 : Ny, :)) * Cx) - ((Hx(2 : Nx, 2 : Ny, :) - Hx(2 : Nx, 1 : (Ny - 1), :&
&)) * Cy))));
	
	tmpvar58 = Ex(4, 4, 4);
	tmpvar59 = Ey(4, 4, 4);
	tmpvar60 = Ez(4, 4, 4);
	! % Sample the electric field at chosen points.
Ets(n, :) = [tmpvar58, tmpvar59, tmpvar60];
END DO







END SUBROUTINE fdtd

END MODULE Mod_drv_fdtd

PROGRAM drv_fdtd
USE Mod_drv_fdtd
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(7) :: output
CHARACTER(3) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
CHARACTER(10) :: arg_buffer
DOUBLE PRECISION :: tmpvar8
DOUBLE PRECISION :: tmpvar9
DOUBLE PRECISION :: tmpvar6
DOUBLE PRECISION :: timing_clock
DOUBLE PRECISION, DIMENSION(:, :, :), ALLOCATABLE :: Ey
DOUBLE PRECISION :: tmpvar7
DOUBLE PRECISION, DIMENSION(:, :, :), ALLOCATABLE :: Ez
DOUBLE PRECISION :: tmpvar4
DOUBLE PRECISION :: tmpvar5
DOUBLE PRECISION :: tmpvar3
INTEGER, DIMENSION(1, 6) :: tmpvar1
DOUBLE PRECISION, DIMENSION(:, :, :), ALLOCATABLE :: Ex
DOUBLE PRECISION :: nrm
INTEGER :: Nz
DOUBLE PRECISION :: Ly
INTEGER :: int_tmpvar
DOUBLE PRECISION :: Lx
INTEGER :: Ny
DOUBLE PRECISION :: Lz
INTEGER :: Nx
INTEGER :: Nt
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Ets
DOUBLE PRECISION, DIMENSION(:, :, :), ALLOCATABLE :: Hy
DOUBLE PRECISION, DIMENSION(:, :, :), ALLOCATABLE :: Hx
DOUBLE PRECISION, DIMENSION(:, :, :), ALLOCATABLE :: Hz
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar62

timing_clock = 0.0
int_tmpvar = 0
arg_buffer = '0000000000'
DO int_tmpvar = 1 , IARGC()
	CALL GETARG(int_tmpvar, arg_buffer) 
	IF ((int_tmpvar == 1)) THEN 
	READ(arg_buffer, *) scale
END IF
END DO




timing_clock = timing_clock
! % 
! %  Driver for 3D FDTD of a hexahedral cavity with conducting walls.
! % 
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;

! % Parameter initialization.
Lx = .05;
Ly = .04;

! % Cavity dimensions in meters.
Lz = .03;
Nx = 25;
Ny = 20;

! % Number of cells in each direction.
Nz = 15;


! % Because norm isn't currently supported,
! % nrm = norm([Nx/Lx Ny/Ly Nz/Lz]) is plugged in.
nrm = 866.0254;

! % Number of time steps.
Nt = (scale * 200);
IF ((.NOT. ALLOCATED(Ey))) THEN 
	ALLOCATE(Ey((Nx + 1), Ny, (Nz + 1)))
END IF
IF ((.NOT. ALLOCATED(Ez))) THEN 
	ALLOCATE(Ez((Nx + 1), (Ny + 1), Nz))
END IF
IF ((.NOT. ALLOCATED(Ex))) THEN 
	ALLOCATE(Ex(Nx, (Ny + 1), (Nz + 1)))
END IF
IF ((.NOT. ALLOCATED(Ets))) THEN 
	ALLOCATE(Ets(Nt, 3))
END IF
IF ((.NOT. ALLOCATED(Hy))) THEN 
	ALLOCATE(Hy(Nx, (Ny + 1), Nz))
END IF
IF ((.NOT. ALLOCATED(Hx))) THEN 
	ALLOCATE(Hx((Nx + 1), Ny, Nz))
END IF
IF ((.NOT. ALLOCATED(Hz))) THEN 
	ALLOCATE(Hz(Nx, Ny, (Nz + 1)))
END IF
CALL fdtd(Lx, Ly, Lz, Nx, Ny, Nz, nrm, Nt, Ex, Ey, Ez, Hx, Hy, Hz, Ets)
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar1(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar62 = MATMUL((t2 - t1), TRANSPOSE(tmpvar1))
! % Compute the running time in seconds
time = tmpvar62(1, 1);

tmpvar3 =  (SUM(Ex)/(0.0+SIZE(Ex)));
tmpvar4 =  (SUM(Ey)/(0.0+SIZE(Ey)));
tmpvar5 =  (SUM(Ez)/(0.0+SIZE(Ez)));
tmpvar6 =  (SUM(Hx)/(0.0+SIZE(Hx)));
tmpvar7 =  (SUM(Hy)/(0.0+SIZE(Hy)));
tmpvar8 =  (SUM(Hz)/(0.0+SIZE(Hz)));
tmpvar9 =  (SUM(Ets)/(0.0+SIZE(Ets)));
! % Store the benchmark output
output = [tmpvar3, tmpvar4, tmpvar5, tmpvar6, tmpvar7, tmpvar8, tmpvar9];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
IF (ALLOCATED(Ey)) THEN 
	DEALLOCATE(Ey)
END IF
IF (ALLOCATED(Ez)) THEN 
	DEALLOCATE(Ez)
END IF
IF (ALLOCATED(Ex)) THEN 
	DEALLOCATE(Ex)
END IF
IF (ALLOCATED(Ets)) THEN 
	DEALLOCATE(Ets)
END IF
IF (ALLOCATED(Hy)) THEN 
	DEALLOCATE(Hy)
END IF
IF (ALLOCATED(Hx)) THEN 
	DEALLOCATE(Hx)
END IF
IF (ALLOCATED(Hz)) THEN 
	DEALLOCATE(Hz)
END IF
END PROGRAM drv_fdtd

