MODULE Mod_drv_nb1d
CONTAINS

SUBROUTINE rand1(m, n, seed1, M1)
INTEGER :: m
INTEGER :: n
DOUBLE PRECISION :: seed
DOUBLE PRECISION, DIMENSION(m, n) :: M1
INTEGER :: j
INTEGER :: i
DOUBLE PRECISION :: seed1
seed = seed1



! % making random deterministic
seed = (seed + (m * n));
M1 = 0;
DO i = 1 , m
	
	
	DO j = 1 , n
	M1(i, j) = mod(seed, 1.0);
	seed = ((seed + (M1(i, j) * sqrt(100.0))) + sqrt(2.0));
END DO
END DO


END SUBROUTINE rand1


SUBROUTINE nbody1d(n, Rx1, Ry1, Rz1, m, dT, T, Fx, Fy, Fz, Vx, Vy, Vz)
INTEGER :: n
DOUBLE PRECISION, DIMENSION(n, 1) :: Rx
DOUBLE PRECISION, DIMENSION(n, 1) :: Ry
DOUBLE PRECISION, DIMENSION(n, 1) :: Rz
DOUBLE PRECISION, DIMENSION(n, 1) :: m
DOUBLE PRECISION :: dT
DOUBLE PRECISION :: T
DOUBLE PRECISION, DIMENSION(n, 1) :: Fx
DOUBLE PRECISION, DIMENSION(n, 1) :: Fy
DOUBLE PRECISION, DIMENSION(n, 1) :: Fz
DOUBLE PRECISION, DIMENSION(n, 1) :: Vx
DOUBLE PRECISION, DIMENSION(n, 1) :: Vy
DOUBLE PRECISION, DIMENSION(n, 1) :: Vz
DOUBLE PRECISION :: t1
DOUBLE PRECISION :: G
DOUBLE PRECISION, DIMENSION(n, 1) :: M1
DOUBLE PRECISION, DIMENSION(1) :: tmpvar25
DOUBLE PRECISION, DIMENSION(1) :: tmpvar24
DOUBLE PRECISION, DIMENSION(1) :: tmpvar26
DOUBLE PRECISION, DIMENSION(n, 1) :: f
DOUBLE PRECISION, DIMENSION(n, 1) :: az
DOUBLE PRECISION, DIMENSION(n, 1) :: drx
DOUBLE PRECISION, DIMENSION(n, 1) :: ay
DOUBLE PRECISION, DIMENSION(n, 1) :: dry
DOUBLE PRECISION, DIMENSION(n, 1) :: drz
DOUBLE PRECISION, DIMENSION(n, 1) :: ax
INTEGER :: k
DOUBLE PRECISION, DIMENSION(n, 1) :: r
DOUBLE PRECISION, DIMENSION(n, 1) :: fry
DOUBLE PRECISION, DIMENSION(n, 1) :: frx
INTEGER :: III
DOUBLE PRECISION, DIMENSION(n, 1) :: frz
DOUBLE PRECISION, DIMENSION(n, 1) :: Rx1
DOUBLE PRECISION, DIMENSION(n, 1) :: Ry1
DOUBLE PRECISION, DIMENSION(n, 1) :: Rz1
INTEGER :: tmpvar28
Rx = Rx1
Ry = Ry1
Rz = Rz1

III = 0
























































! %-----------------------------------------------------------------------
! %
! %	This function M-file simulates the gravitational movement
! %	of a set of objects.
! %
! %	Invocation:
! %		>> [Fx, Fy, Fz, Vx, Vy, Vz] = ...
! %		   nbody1d(n, Rx, Ry, Rz, m, dT, T)
! %
! %		where
! %
! %		i. n is the number of objects,
! %
! %		i. Rx is the n x 1 radius vector of x-components,
! %
! %		i. Ry is the n x 1 radius vector of y-components,
! %
! %		i. Rz is the n x 1 radius vector of z-components,
! %
! %		i. m is the n x 1 vector of object masses,
! %
! %		i. dT is the time increment of evolution,
! %
! %		i. T is the total time for evolution,
! %
! %		o. Fx is the n x 1 vector of net x-component forces,
! %
! %		o. Fy is the n x 1 vector of net y-component forces,
! %
! %		o. Fz is the n x 1 vector of net z-component forces,
! %
! %		o. Vx is the n x 1 vector of x-component velocities,
! %
! %		o. Vy is the n x 1 vector of y-component velocities,
! %
! %		o. Vz is the n x 1 vector of z-component velocities.
! %
! %	Requirements:
! %		None.
! %
! %	Examples:
! %		>> [Fx, Fy, Fz, Vx, Vy, Vz] = ...
! %		   nbody1d(n, ...
! %		   rand(n, 1)*1000.23, ...
! %		   rand(n, 1)*1000.23, ...
! %		   rand(n, 1)*1000.23, ...
! %		   rand(n, 1)*345, 0.01, 20)
! %
! %	Source:
! %		Quinn's "Otter" project.
! %
! %	Author:
! %		Alexey Malishevsky (malishal@cs.orst.edu).
! %
! %-----------------------------------------------------------------------
Fx = 0;
Fy = 0;
Fz = 0;
Vx = 0;
Vy = 0;
Vz = 0;

! % Gravitational constant.
G = 1e-11;
DO tmpvar28 = 0 , ((floor(((T - 1) / dT)) + 1) - 1)
	III = III
	t1 = ((dT * tmpvar28) + 1)
	
	
	
	
	
	
	DO k = 1 , n
	
	
	! % Find the displacement vector between all particles
! % and the kth particle.
drx = (Rx - Rx(k, 1));
	dry = (Ry - Ry(k, 1));
	drz = (Rz - Rz(k, 1));
	
	
	! % Find the squared distance between all particles
! % and the kth particle, adjusting "self distances" to 1.
r = (((drx * drx) + (dry * dry)) + (drz * drz));
	r(k, 1) = 1.0;
	
	
	! % Find the product of the kth particle's mass and
! % and every object's mass, adjusting "self products" to 0.
M1 = (m * m(k, 1));
	M1(k, 1) = 0.0;
	
	! % Find the gravitational force.
f = (G * (M1 / r));
	
	! % Find the unit direction vector.
r = sqrt(r);
	drx = (drx / r);
	dry = (dry / r);
	drz = (drz / r);
	
	! % Find the resulting force.
frx = (f * drx);
	fry = (f * dry);
	frz = (f * drz);
	tmpvar24 =  (SUM(frx,1)/(0.0+SIZE(frx,1)));
	Fx(k, 1) = (tmpvar24(1) * n);
	tmpvar25 =  (SUM(fry,1)/(0.0+SIZE(fry,1)));
	Fy(k, 1) = (tmpvar25(1) * n);
	tmpvar26 =  (SUM(frz,1)/(0.0+SIZE(frz,1)));
	Fz(k, 1) = (tmpvar26(1) * n);
END DO
	
	! % Find the acceleration.
ax = (Fx / m);
	ay = (Fy / m);
	az = (Fz / m);
	
	! % Find the velocity.
Vx = (Vx + (ax * dT));
	Vy = (Vy + (ay * dT));
	Vz = (Vz + (az * dT));
	
	! % Find the radius vector.
Rx = (Rx + (Vx * dT));
	Ry = (Ry + (Vy * dT));
	Rz = (Rz + (Vz * dT));
END DO






END SUBROUTINE nbody1d

END MODULE Mod_drv_nb1d

PROGRAM drv_nb1d
USE Mod_drv_nb1d
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(6) :: output
CHARACTER(3) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
CHARACTER(10) :: arg_buffer
INTEGER, DIMENSION(1, 6) :: tmpvar8
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: tmpvar6
DOUBLE PRECISION :: timing_clock
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: tmpvar7
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: tmpvar4
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: tmpvar5
DOUBLE PRECISION :: T
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Rx
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Ry
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Vy
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Vx
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Rz
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Vz
INTEGER :: int_tmpvar
INTEGER :: seed
INTEGER :: n
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Fz
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: m
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Fx
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: Fy
DOUBLE PRECISION :: tmpvar10
DOUBLE PRECISION :: dT
DOUBLE PRECISION :: tmpvar12
DOUBLE PRECISION :: tmpvar11
DOUBLE PRECISION :: tmpvar14
DOUBLE PRECISION :: tmpvar13
DOUBLE PRECISION :: tmpvar15
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar29

timing_clock = 0.0
int_tmpvar = 0
arg_buffer = '0000000000'
DO int_tmpvar = 1 , IARGC()
	CALL GETARG(int_tmpvar, arg_buffer) 
	IF ((int_tmpvar == 1)) THEN 
	READ(arg_buffer, *) scale
END IF
END DO





! % 
! %  Driver for the N-body problem coded using 1d arrays for the
! %  displacement vectors.
! % 
seed = 1;
timing_clock = timing_clock
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;

! %floor(28*rand);
n = floor(0.5+((scale ** .4) * 30)) ;
dT = (.5 * 0.0833);
T = ((.5 * 32.4362) * sqrt((scale + 0.0)));
IF ((.NOT. ALLOCATED(tmpvar4))) THEN 
	ALLOCATE(tmpvar4(n, 1))
END IF
CALL rand1(n, 1, .1, tmpvar4)
IF ((.NOT. ALLOCATED(Rx))) THEN 
	ALLOCATE(Rx(n, 1))
END IF
Rx = (tmpvar4 * 1000.23);
IF ((.NOT. ALLOCATED(tmpvar5))) THEN 
	ALLOCATE(tmpvar5(n, 1))
END IF
CALL rand1(n, 1, .4, tmpvar5)
IF ((.NOT. ALLOCATED(Ry))) THEN 
	ALLOCATE(Ry(n, 1))
END IF
Ry = (tmpvar5 * 1000.23);
IF ((.NOT. ALLOCATED(tmpvar6))) THEN 
	ALLOCATE(tmpvar6(n, 1))
END IF
CALL rand1(n, 1, .9, tmpvar6)
IF ((.NOT. ALLOCATED(Rz))) THEN 
	ALLOCATE(Rz(n, 1))
END IF
Rz = (tmpvar6 * 1000.23);
IF ((.NOT. ALLOCATED(tmpvar7))) THEN 
	ALLOCATE(tmpvar7(n, 1))
END IF
CALL rand1(n, 1, (-.4), tmpvar7)
IF ((.NOT. ALLOCATED(m))) THEN 
	ALLOCATE(m(n, 1))
END IF
m = (tmpvar7 * 345);
IF ((.NOT. ALLOCATED(Vy))) THEN 
	ALLOCATE(Vy(n, 1))
END IF
IF ((.NOT. ALLOCATED(Vx))) THEN 
	ALLOCATE(Vx(n, 1))
END IF
IF ((.NOT. ALLOCATED(Vz))) THEN 
	ALLOCATE(Vz(n, 1))
END IF
IF ((.NOT. ALLOCATED(Fz))) THEN 
	ALLOCATE(Fz(n, 1))
END IF
IF ((.NOT. ALLOCATED(Fx))) THEN 
	ALLOCATE(Fx(n, 1))
END IF
IF ((.NOT. ALLOCATED(Fy))) THEN 
	ALLOCATE(Fy(n, 1))
END IF
CALL nbody1d(n, Rx, Ry, Rz, m, dT, T, Fx, Fy, Fz, Vx, Vy, Vz)
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar8(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar29 = MATMUL((t2 - t1), TRANSPOSE(tmpvar8))
! % Compute the running time in seconds
time = tmpvar29(1, 1);

tmpvar10 =  (SUM(Fx)/(0.0+SIZE(Fx)));
tmpvar11 =  (SUM(Fy)/(0.0+SIZE(Fy)));
tmpvar12 =  (SUM(Fz)/(0.0+SIZE(Fz)));
tmpvar13 =  (SUM(Vx)/(0.0+SIZE(Vx)));
tmpvar14 =  (SUM(Vy)/(0.0+SIZE(Vy)));
tmpvar15 =  (SUM(Vz)/(0.0+SIZE(Vz)));
! % Store the benchmark output
output = [tmpvar10, tmpvar11, tmpvar12, tmpvar13, tmpvar14, tmpvar15];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
IF (ALLOCATED(tmpvar6)) THEN 
	DEALLOCATE(tmpvar6)
END IF
IF (ALLOCATED(tmpvar7)) THEN 
	DEALLOCATE(tmpvar7)
END IF
IF (ALLOCATED(tmpvar4)) THEN 
	DEALLOCATE(tmpvar4)
END IF
IF (ALLOCATED(tmpvar5)) THEN 
	DEALLOCATE(tmpvar5)
END IF
IF (ALLOCATED(Rx)) THEN 
	DEALLOCATE(Rx)
END IF
IF (ALLOCATED(Ry)) THEN 
	DEALLOCATE(Ry)
END IF
IF (ALLOCATED(Vy)) THEN 
	DEALLOCATE(Vy)
END IF
IF (ALLOCATED(Vx)) THEN 
	DEALLOCATE(Vx)
END IF
IF (ALLOCATED(Rz)) THEN 
	DEALLOCATE(Rz)
END IF
IF (ALLOCATED(Vz)) THEN 
	DEALLOCATE(Vz)
END IF
IF (ALLOCATED(Fz)) THEN 
	DEALLOCATE(Fz)
END IF
IF (ALLOCATED(m)) THEN 
	DEALLOCATE(m)
END IF
IF (ALLOCATED(Fx)) THEN 
	DEALLOCATE(Fx)
END IF
IF (ALLOCATED(Fy)) THEN 
	DEALLOCATE(Fy)
END IF
END PROGRAM drv_nb1d

