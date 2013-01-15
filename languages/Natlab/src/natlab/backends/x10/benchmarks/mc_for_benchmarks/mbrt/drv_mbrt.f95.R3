MODULE Mod_drv_mbrt
CONTAINS

SUBROUTINE mandelbrot(N, Nmax, set)
INTEGER :: N
INTEGER :: Nmax
INTEGER, DIMENSION(:, :), ALLOCATABLE :: set
DOUBLE PRECISION :: dx
DOUBLE PRECISION :: dy
INTEGER :: side
INTEGER :: tmpvar26
DOUBLE PRECISION :: xb
DOUBLE PRECISION :: xa
INTEGER :: ya
INTEGER :: yb
INTEGER :: y
INTEGER :: x







! %n=integer?
! %nmax=parameter
! %x,c are complex
! %ya,yb,xa,xb,dx,dy
! % computes mandelbrot set with N elements and Nmax iterations
side = floor(0.5+sqrt((N + 0.0))) ;
ya = (-1);
yb = 1;
xa = (-1.5);
xb = .5;
dx = ((xb - xa) / (side - 1));
dy = (((yb - ya) + 0.0) / (side - 1));
IF ((.NOT. ALLOCATED(set))) THEN 
	ALLOCATE(set(side, side))
END IF
set = 0;
DO x = 0 , (side - 1)
	
	DO y = 0 , (side - 1)
	CALL iterations(((xa + (x * dx)) + COMPLEX(0.0, (ya + (y * dy)))), Nmax, tmpvar26)
	set((y + 1), (x + 1)) = tmpvar26;
END DO
END DO

! set;
END SUBROUTINE mandelbrot


SUBROUTINE iterations(x1, max, out)
COMPLEX :: x
INTEGER :: max
INTEGER :: out
COMPLEX :: c
INTEGER :: i
COMPLEX :: x1
x = x1


c = x;
i = 0;
DO  
	
	IF (((abs(x) < 2) .AND. (i < max))) THEN 
	x = ((x * x) + c);
	i = (i + 1);
ELSE 
	EXIT
END IF
END DO

out = i;
END SUBROUTINE iterations

END MODULE Mod_drv_mbrt

PROGRAM drv_mbrt
USE Mod_drv_mbrt
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(1) :: output
CHARACTER(4) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
CHARACTER(10) :: arg_buffer
INTEGER, DIMENSION(:, :), ALLOCATABLE :: set
DOUBLE PRECISION :: tmpvar9
DOUBLE PRECISION :: timing_clock
INTEGER, DIMENSION(1, 6) :: tmpvar6
INTEGER :: N
INTEGER :: int_tmpvar
INTEGER :: Nmax
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


! % computes mandelbrot set
N = floor(0.5+(6000 * sqrt((scale + 0.0)))) ;
Nmax = floor(0.5+((10 ** 3) * sqrt((scale + 0.0)))) ;
timing_clock = timing_clock
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;
CALL mandelbrot(N, Nmax, set)
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar6(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar29 = MATMUL((t2 - t1), TRANSPOSE(tmpvar6))
! % Compute the running time in seconds
time = tmpvar29(1, 1);

tmpvar9 =  (SUM(set)/(0.0+SIZE(set)));
! % Store the benchmark output
output = [tmpvar9];
IF ((abs((( (0.0+SUM( (0.0+SUM(set,1)))) / (Nmax * N)) - 0.37429481997515)) < 0.01)) THEN 
	
	! % Validate the result
valid = 'PASS';
ELSE 
	valid = 'FAIL';
END IF

PRINT *,time;
PRINT *,output;
PRINT *,valid;
IF (ALLOCATED(set)) THEN 
	DEALLOCATE(set)
END IF
END PROGRAM drv_mbrt

