MODULE Mod_drv_dich
CONTAINS

SUBROUTINE dirich(f1, f2, f3, f4, a, b, h, tol, max1, U)
INTEGER :: f1
INTEGER :: f2
INTEGER :: f3
INTEGER :: f4
INTEGER :: a
INTEGER :: b
DOUBLE PRECISION :: h
INTEGER :: tol
INTEGER :: max1
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: U
DOUBLE PRECISION :: relx
INTEGER, DIMENSION(:, :), ALLOCATABLE :: tmpvar8
INTEGER :: l2
INTEGER :: l1
DOUBLE PRECISION :: ave
DOUBLE PRECISION :: pi
INTEGER :: cnt
INTEGER :: n
INTEGER :: l
INTEGER :: m
INTEGER :: k
INTEGER :: k1
INTEGER :: k2
DOUBLE PRECISION :: w
DOUBLE PRECISION :: err

pi = 3.14159265359





































































! %-----------------------------------------------------------------------
! %
! %	This function M-file finds the Dirichlet solution to
! %	Laplace's equation
! %
! %			u  (x, y)+u  (x, y) = 0,
! %			 xx	   yy
! %
! %	with the boundary conditions
! %
! %		u(x, 0) = f1, u(x, b) = f2 for all 0 <= x <= a, and
! %
! %		u(0, y) = f3, u(a, y) = f4 for all 0 <= y <= b.
! %
! %	The subscripts xx and yy indicate the second partial
! %	derivatives of u(x, y) with respect to x and y respectively.
! %
! %	Invocation:
! %		>> U = dirich(f1, f2, f3, f4, a, b, h, tol, max1)
! %
! %		where
! %
! %		i. f1 is lower-edge boundary value,
! %
! %		i. f2 is the upper-edge boundary value,
! %
! %		i. f3 is the left-edge boundary value,
! %
! %		i. f4 is the right-edge boundary value,
! %
! %		i. a is the extent of the abscissa,
! %
! %		i. b is the extent of the ordinate,
! %
! %		i. h is the step size,
! %
! %		i. tol is the convergence tolerance,
! %
! %		i. max1 is the maximum number of iterations,
! %
! %		o. U is the solution matrix.
! %
! %	Requirements:
! %		None.
! %
! %	Examples:
! %		>> U = dirich(20, 180, 80, 0, 4, 4, 0.1, 0.0001, 200)
! %
! %	Source:
! %		Numerical Methods: MATLAB Programs,
! %		(c) John H. Mathews, 1995.
! %
! %		Accompanying text:
! %		Numerical Methods for Mathematics, Science and
! %		Engineering, 2nd Edition, 1992.
! %
! %		Prentice Hall, Englewood Cliffs,
! %		New Jersey, 07632, USA.
! %
! %		Also part of the FALCON project.
! %
! %	Author:
! %		John H. Mathews (mathews@fullerton.edu).
! %
! %	Date:
! %		March 1995.
! %
! %-----------------------------------------------------------------------
n = (floor((a / h)) + 1);
m = (floor((b / h)) + 1);
ave = ((((a * (f1 + f2)) + (b * (f3 + f4))) + 0.0) / ((2 * a) + (2 * b)));
IF ((.NOT. ALLOCATED(tmpvar8))) THEN 
	ALLOCATE(tmpvar8(n, m))
END IF
tmpvar8 = 1;
IF ((.NOT. ALLOCATED(U))) THEN 
	ALLOCATE(U(n, m))
END IF
U = (ave * tmpvar8);
DO l2 = 1 , m
	
	U(1, l2) = f3;
	U(n, l2) = f4;
END DO

l = l2
DO k2 = 1 , n
	
	U(k2, 1) = f1;
	U(k2, m) = f2;
END DO

k = k2
U(1, 1) = ((U(1, 2) + U(2, 1)) / 2);
U(1, m) = ((U(1, (m - 1)) + U(2, m)) / 2);
U(n, 1) = ((U((n - 1), 1) + U(n, 2)) / 2);
U(n, m) = ((U((n - 1), m) + U(n, (m - 1))) / 2);
w = (4 / (2 + sqrt((4 - ((cos((pi / (n - 1))) + cos((pi / (m - 1)))) ** 2)))));
err = 1;
cnt = 0;
DO  
	
	
	
	
	
	IF (((err > tol) .AND. (cnt <= max1))) THEN 
	err = 0;
	DO l1 = 2 , (m - 1)
	
	DO k1 = 2 , (n - 1)
	! ...
relx = ((w * ((((U(k1, (l1 + 1)) + U(k1, (l1 - 1))) + U((k1 + 1), l1)) + U((k1 - 1), l1)) &
&- (4 * U(k1, l1)))) / 4);
	U(k1, l1) = (U(k1, l1) + relx);
	IF ((err <= abs(relx))) THEN 
	err = abs(relx);
END IF
END DO
	k = k1
END DO
	
	l = l1
	cnt = (cnt + 1);
ELSE 
	EXIT
END IF
	
END DO





IF (ALLOCATED(tmpvar8)) THEN 
	DEALLOCATE(tmpvar8)
END IF
END SUBROUTINE dirich

END MODULE Mod_drv_dich

PROGRAM drv_dich
USE Mod_drv_dich
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(1) :: output
CHARACTER(3) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
CHARACTER(10) :: arg_buffer
DOUBLE PRECISION :: timing_clock
DOUBLE PRECISION :: tmpvar3
INTEGER, DIMENSION(1, 6) :: tmpvar1
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: U
INTEGER :: time1
INTEGER :: f1
INTEGER :: tol
INTEGER :: f3
INTEGER :: f2
INTEGER :: f4
INTEGER :: b
INTEGER :: int_tmpvar
INTEGER :: a
DOUBLE PRECISION :: h
INTEGER :: max1
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar44

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
! %  Driver for the Dirichlet solution to Laplace's equation.
! % 
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;
a = 4;
b = 4;
h = 0.03;
tol = (10 ** (-5));
max1 = 1000;
f1 = 20;
f2 = 180;
f3 = 80;
f4 = 0;
DO time1 = 1 , scale
	CALL dirich(f1, f2, f3, f4, a, b, h, tol, max1, U)
END DO
time = time1
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar1(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar44 = MATMUL((t2 - t1), TRANSPOSE(tmpvar1))
! % Compute the running time in seconds
time = tmpvar44(1, 1);

tmpvar3 =  (SUM(U)/(0.0+SIZE(U)));
! % Store the benchmark output
output = [tmpvar3];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
IF (ALLOCATED(U)) THEN 
	DEALLOCATE(U)
END IF
END PROGRAM drv_dich

