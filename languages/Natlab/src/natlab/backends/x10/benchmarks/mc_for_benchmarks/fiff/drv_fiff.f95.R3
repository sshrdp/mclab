MODULE Mod_drv_fiff
CONTAINS

SUBROUTINE finediff(a, b, c, n, m, U)
DOUBLE PRECISION :: a
DOUBLE PRECISION :: b
DOUBLE PRECISION :: c
INTEGER :: n
INTEGER :: m
DOUBLE PRECISION, DIMENSION(n, m) :: U
DOUBLE PRECISION :: pi
INTEGER :: i3
INTEGER :: i2
INTEGER :: i1
INTEGER :: j1
DOUBLE PRECISION :: s2
DOUBLE PRECISION :: s1
DOUBLE PRECISION :: k
DOUBLE PRECISION :: h
DOUBLE PRECISION :: r22
DOUBLE PRECISION :: r
DOUBLE PRECISION :: r2

pi = 3.14159265359


































































! %-----------------------------------------------------------------------
! %
! %	This function M-file finds the finite-difference solution
! %	to the wave equation
! %
! %			     2
! %		u  (x, t) = c u  (x, t),
! %		 tt	       xx
! %
! %	with the boundary conditions
! %
! %		u(0, t) = 0, u(a, t) = 0 for all 0 <= t <= b,
! %
! %		u(x, 0) = sin(pi*x)+sin(2*pi*x), for all 0 < x < a,
! %
! %		u (x, 0) = 0 for all 0 < x < a.
! %		 t
! %
! %	The subscripts t, tt and xx denote the first and second
! %	partial derivatives of u(x, t) with respect to t and x.
! %
! %	Invocation:
! %		>> U = finediff(a, b, c, n, m)
! %
! %		where
! %
! %		i. a is the displacement,
! %
! %		i. b is the time duration,
! %
! %		i. c is the square root of the
! %		   constant in the wave equation,
! %
! %		i. n is the number of grid points over [0, a],
! %
! %		i. m is the number of grid points over [0, b],
! %
! %		o. U is the solution matrix.
! %
! %	Requirements:
! %		None.
! %
! %	Examples:
! %		>> U = finediff(2.5, 1.5, 0.5, 451, 451)
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
h = (a / (n - 1));
k = (b / (m - 1));
r = ((c * k) / h);
r2 = (r ** 2);
r22 = ((r ** 2) / 2);
s1 = (1 - (r ** 2));
s2 = (2 - (2 * (r ** 2)));
U = 0;
DO i3 = 2 , (n - 1)
	
	U(i3, 1) = (sin(((pi * h) * (i3 - 1))) + sin((((2 * pi) * h) * (i3 - 1))));
	! ...
! ...
U(i3, 2) = ((s1 * (sin(((pi * h) * (i3 - 1))) + sin((((2 * pi) * h) * (i3 - 1))))) + (r22 &
&* (((sin(((pi * h) * i3)) + sin((((2 * pi) * h) * i3))) + sin(((pi * h) * (i3 - 2)))) + si&
&n((((2 * pi) * h) * (i3 - 2))))));
END DO

i1 = i3
DO j1 = 3 , m
	
	
	DO i2 = 2 , (n - 1)
	! ...
U(i2, j1) = (((s2 * U(i2, (j1 - 1))) + (r2 * (U((i2 - 1), (j1 - 1)) + U((i2 + 1), (j1 - 1)&
&)))) - U(i2, (j1 - 2)));
END DO
	i1 = i2
END DO


END SUBROUTINE finediff

END MODULE Mod_drv_fiff

PROGRAM drv_fiff
USE Mod_drv_fiff
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
DOUBLE PRECISION :: b
INTEGER :: int_tmpvar
DOUBLE PRECISION :: c
DOUBLE PRECISION :: a
INTEGER :: n
INTEGER :: m
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar48

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
! %  Driver for finite-difference solution to the wave equation.
! % 
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;
a = 2.5;
b = 1.5;
c = 0.5;
n = 3500;
m = 3500;
DO time1 = 1 , scale
	IF ((.NOT. ALLOCATED(U))) THEN 
	ALLOCATE(U(n, m))
END IF
	CALL finediff(a, b, c, n, m, U)
END DO
time = time1
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar1(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar48 = MATMUL((t2 - t1), TRANSPOSE(tmpvar1))
! % Compute the running time in seconds
time = tmpvar48(1, 1);

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
END PROGRAM drv_fiff

