MODULE Mod_drv_nb3d
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


SUBROUTINE nbody3d(n, R2, m, dT, T, F, V)
INTEGER :: n
DOUBLE PRECISION, DIMENSION(n, 3) :: R
DOUBLE PRECISION, DIMENSION(n, 1) :: m
DOUBLE PRECISION :: dT
DOUBLE PRECISION :: T
DOUBLE PRECISION, DIMENSION(n, 3) :: F
DOUBLE PRECISION, DIMENSION(n, 3) :: V
INTEGER, DIMENSION(n, n) :: jj
DOUBLE PRECISION, DIMENSION(n, n, 3) :: tmpvar24
DOUBLE PRECISION, DIMENSION(n, n, 3) :: tmpvar23
DOUBLE PRECISION, DIMENSION(n, n, 3) :: fr
DOUBLE PRECISION, DIMENSION(n, n) :: MM
INTEGER, DIMENSION((n-1)+1) :: kk
INTEGER, DIMENSION(1, n) :: vno
INTEGER, DIMENSION(n, 1) :: tmpvar14
INTEGER, DIMENSION(1, n) :: tmpvar16
INTEGER, DIMENSION(1, n) :: tmpvar15
INTEGER, DIMENSION(1, n) :: tmpvar18
INTEGER, DIMENSION(n, 1) :: vnt
DOUBLE PRECISION, DIMENSION(n, n, 3) :: r1
INTEGER :: III
DOUBLE PRECISION :: t1
DOUBLE PRECISION :: G
INTEGER :: JJJ
DOUBLE PRECISION, DIMENSION(n, n) :: f1
DOUBLE PRECISION, DIMENSION(n, n, 3) :: dr
INTEGER :: int_tmpvar
DOUBLE PRECISION, DIMENSION(n, 3) :: a
INTEGER, DIMENSION(n, n) :: ii
DOUBLE PRECISION, DIMENSION(n, 3) :: R2
INTEGER :: tmpvar40
DOUBLE PRECISION, DIMENSION(n, n) :: r3
DOUBLE PRECISION, DIMENSION(n, n, 3) :: r4
DOUBLE PRECISION, DIMENSION(n, n) :: r5
R = R2

III = 0
JJJ = 0
int_tmpvar = 0

































! %-----------------------------------------------------------------------
! %
! %	This function M-file simulates the gravitational movement
! %	of a set of objects.
! %
! %	Invocation:
! %		>> [F, V] = nbody3d(n, R, m, dT, T)
! %
! %		where
! %
! %		i. n is the number of objects,
! %
! %		i. R is the n x 3 matrix of radius vectors,
! %
! %		i. m is the n x 1 vector of object masses,
! %
! %		i. dT is the time increment of evolution,
! %
! %		i. T is the total time for evolution,
! %
! %		o. F is the n x 3 matrix of net forces,
! %
! %		o. V is the n x 3 matrix of velocities.
! %
! %	Requirements:
! %		None.
! %
! %	Examples:
! %		>> [F, V] = nbody3d(n, rand(n, 3)*1000.23, ...
! %		   rand(n, 1)*345, 0.01, 20)
! %
! %-----------------------------------------------------------------------
F = 0;
V = 0;

! % Gravitational constant.
G = 1e-11;
III = III
vno(1, :) = (/(III, III=1 , n)/);
vnt = TRANSPOSE(vno);

tmpvar14 = 1;
tmpvar15(1, :) = (/(III, III=1 , n)/);
! % ii = vno(ones(n, 1), :);
ii = MATMUL(tmpvar14, tmpvar15);

tmpvar16(1, :) = (/(III, III=1 , n)/);
tmpvar18 = 1;
! % jj = vnt(:, ones(n, 1));
jj = MATMUL(TRANSPOSE(tmpvar16), tmpvar18);
kk = ((/(III, III=1 , (n * n) , n)/) + (/(III, III=0 , (n - 1))/));
dr = 0;
fr = 0;
a = 0;
DO tmpvar40 = 0 , ((floor(((T - 1) / dT)) + 1) - 1)
	t1 = ((dT * tmpvar40) + 1)
	
	
	
	
	
	
	DO III = 1 , n
	DO JJJ = 1 , n
	tmpvar23(III, JJJ, :) = R(jj(III, JJJ), :);
END DO
END DO
	DO III = 1 , n
	DO JJJ = 1 , n
	tmpvar24(III, JJJ, :) = R(ii(III, JJJ), :);
END DO
END DO
	! % Find the displacement vector between all particles.
dr = (tmpvar23 - tmpvar24);
	
	
	
	! %  dr is the n x n x 3 array of displacements.
! % Find the squared distance between all particles,
! % adjusting "self distances" to 1.
r1 = (dr * dr);
	r5 = ((r1(:, :, 1) + r1(:, :, 2)) + r1(:, :, 3));
	DO int_tmpvar = 1 , ((n - 1) + 1)
	III = (floor((((kk(int_tmpvar) - 1) / n) + 0.0)) + 1)
	JJJ = (mod((kk(int_tmpvar) - 1), n) + 1)
	r5(III, JJJ) = 1.0;
END DO
	
	
	
	! %  r is the n x n squared distances matrix.
! % Find the mass products of all particle pairs,
! % adjusting "self products" to 0.0.
MM = MATMUL(m, TRANSPOSE(m));
	DO int_tmpvar = 1 , ((n - 1) + 1)
	III = (floor((((kk(int_tmpvar) - 1) / n) + 0.0)) + 1)
	JJJ = (mod((kk(int_tmpvar) - 1), n) + 1)
	MM(III, JJJ) = 0.0;
END DO
	
	
	! %  MM is the n x n mass products matrix.
! % Find the gravitational force.
f1 = (G * (MM / r5));
	
	
	! %  f is the n x n force matrix.
! % Find the unit direction vector.
r5 = sqrt(r5);
	dr(:, :, 1) = (dr(:, :, 1) / r5);
	dr(:, :, 2) = (dr(:, :, 2) / r5);
	dr(:, :, 3) = (dr(:, :, 3) / r5);
	
	! % Find the resulting force.
fr(:, :, 1) = (f1 * dr(:, :, 1));
	fr(:, :, 2) = (f1 * dr(:, :, 2));
	fr(:, :, 3) = (f1 * dr(:, :, 3));
	F = ( (SUM(fr,1)/(0.0+SIZE(fr,1))) * n);
	
	! % Find the acceleration.
a(:, 1) = (F(:, 1) / m(:, 1));
	a(:, 2) = (F(:, 2) / m(:, 1));
	a(:, 3) = (F(:, 3) / m(:, 1));
	
	! % Find the velocity.
V = (V + (a * dT));
	
	
	! %  V is the n x 3 matrix of velocities.
! % Find the radius vector.
R = (R + (V * dT));
END DO





END SUBROUTINE nbody3d

END MODULE Mod_drv_nb3d

PROGRAM drv_nb3d
USE Mod_drv_nb3d
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(2) :: output
CHARACTER(3) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: F
CHARACTER(10) :: arg_buffer
DOUBLE PRECISION :: tmpvar8
DOUBLE PRECISION :: tmpvar9
INTEGER, DIMENSION(1, 6) :: tmpvar6
DOUBLE PRECISION :: timing_clock
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: tmpvar4
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: tmpvar5
DOUBLE PRECISION :: T
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: V
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: R
INTEGER :: int_tmpvar
INTEGER :: n
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: m
DOUBLE PRECISION :: dT
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar41

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
! %  Driver for the N-body problem coded using 3d arrays for the
! %  displacement vectors.
! % 
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;

! %floor(28*rand);
n = floor(0.5+((scale ** .4) * 30)) ;
dT = (.5 * 0.0833);
T = ((.5 * 32.4362) * sqrt((scale + 0.0)));
IF ((.NOT. ALLOCATED(tmpvar4))) THEN 
	ALLOCATE(tmpvar4(n, 3))
END IF
CALL rand1(n, 3, .1, tmpvar4)
IF ((.NOT. ALLOCATED(R))) THEN 
	ALLOCATE(R(n, 3))
END IF
R = (tmpvar4 * 1000.23);
IF ((.NOT. ALLOCATED(tmpvar5))) THEN 
	ALLOCATE(tmpvar5(n, 1))
END IF
CALL rand1(n, 1, .9, tmpvar5)
IF ((.NOT. ALLOCATED(m))) THEN 
	ALLOCATE(m(n, 1))
END IF
m = (tmpvar5 * 345);
IF ((.NOT. ALLOCATED(F))) THEN 
	ALLOCATE(F(n, 3))
END IF
IF ((.NOT. ALLOCATED(V))) THEN 
	ALLOCATE(V(n, 3))
END IF
CALL nbody3d(n, R, m, dT, T, F, V)
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar6(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar41 = MATMUL((t2 - t1), TRANSPOSE(tmpvar6))
! % Compute the running time in seconds
time = tmpvar41(1, 1);

tmpvar8 =  (SUM(F)/(0.0+SIZE(F)));
tmpvar9 =  (SUM(V)/(0.0+SIZE(V)));
! % Store the benchmark output
output = [tmpvar8, tmpvar9];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
IF (ALLOCATED(F)) THEN 
	DEALLOCATE(F)
END IF
IF (ALLOCATED(tmpvar4)) THEN 
	DEALLOCATE(tmpvar4)
END IF
IF (ALLOCATED(tmpvar5)) THEN 
	DEALLOCATE(tmpvar5)
END IF
IF (ALLOCATED(V)) THEN 
	DEALLOCATE(V)
END IF
IF (ALLOCATED(R)) THEN 
	DEALLOCATE(R)
END IF
IF (ALLOCATED(m)) THEN 
	DEALLOCATE(m)
END IF
END PROGRAM drv_nb3d

