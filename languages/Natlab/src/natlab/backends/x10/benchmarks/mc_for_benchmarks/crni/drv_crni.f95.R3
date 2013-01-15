MODULE Mod_drv_crni
CONTAINS

SUBROUTINE crnich(a, b, c, n, m, U)
DOUBLE PRECISION :: a
DOUBLE PRECISION :: b
INTEGER :: c
INTEGER :: n
INTEGER :: m
DOUBLE PRECISION, DIMENSION(n, m) :: U
DOUBLE PRECISION, DIMENSION(n, 1) :: tmpvar25
INTEGER :: tmpvar14
INTEGER, DIMENSION(1, n) :: tmpvar13
INTEGER :: tmpvar16
INTEGER :: i3
DOUBLE PRECISION :: pi
INTEGER :: i2
INTEGER :: i1
INTEGER :: j1
DOUBLE PRECISION, DIMENSION(1, n) :: X
DOUBLE PRECISION :: s2
DOUBLE PRECISION :: s1
DOUBLE PRECISION :: k
DOUBLE PRECISION :: h
INTEGER, DIMENSION(1, (n-1)) :: Va
INTEGER, DIMENSION(1, (n-1)) :: Vc
DOUBLE PRECISION :: r
DOUBLE PRECISION, DIMENSION(n) :: Vb
DOUBLE PRECISION, DIMENSION(1, n) :: Vd

pi = 3.14159265359












































































! %-----------------------------------------------------------------------
! %
! %	This function M-file finds the Crank-Nicholson solution
! %	to the one-dimensional heat equation
! %
! %				    2
! %			u (x, t) = c u  (x, t).
! %			 t	      xx
! %
! %	The function u(x, t) denotes the temperature in a
! %	one-dimensional metal rod as a function of both the
! %	displacement x and the time t. The expression c^2 is the
! %	thermal conductivity constant. The subscript t indicates
! %	the partial derivative of u(x, t) with respect to time.
! %	The subscript xx indicates the second partial derivative of
! %	u(x, t) with respect to displacement.
! %
! %	The one-dimensional heat equation can be solved under a
! %	variety of boundary conditions. This program considers
! %	the following:
! %
! %		u(x, 0) = sin(pi*x)+sin(3*pi*x) for 0 < x < a,
! %
! %		u(0, t) = 0, and u(a, t) = 0 for 0 <= t <= b,
! %
! %	where a is the length of the rod, and b is the time duration.
! %
! %	For a concise background on the one-dimensional heat equation,
! %	see "Modelling: Derivation of the Heat Equation" at
! %	http://www-solar.mcs.st-and.ac.uk/~alan/MT2003/PDE/node20.html.
! %
! %	Invocation:
! %		>> U = crnich(a, b, c, n, m)
! %
! %		where
! %
! %		i. a is the length of the metal rod,
! %
! %		i. b is the time duration,
! %
! %		i. c is the square root of the thermal
! %		   conductivity constant in the heat equation,
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
! %		>> U = crnich(2.5, 1.5, 2, 321, 321)
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
r = (((c ** 2) * k) / (h ** 2));
s1 = (2 + (2 / r));
s2 = ((2 / r) - 2);
U = 0;
DO i3 = 2 , (n - 1)
	
	
	! % u(x, 0) = sin(pi*x)+sin(3*pi*x).
U(i3, 1) = (sin(((pi * h) * (i3 - 1))) + sin((((3 * pi) * h) * (i3 - 1))));
END DO

i1 = i3
tmpvar13 = 1;
Vd = (s1 * tmpvar13);
Vd(1, 1) = 1;
Vd(1, n) = 1;
tmpvar14 = (n - 1);
Va = (-1);
Va(1, (n - 1)) = 0;
tmpvar16 = (n - 1);
Vc = (-1);
Vc(1, 1) = 0;
Vb(1) = 0;
Vb(n) = 0;
DO j1 = 2 , m
	
	
	
	DO i2 = 2 , (n - 1)
	Vb(i2) = ((U((i2 - 1), (j1 - 1)) + U((i2 + 1), (j1 - 1))) + (s2 * U(i2, (j1 - 1))));
END DO
	i1 = i2
	CALL tridiagonal(Va, Vd, Vc, Vb, n, X)
	tmpvar25 = TRANSPOSE(X);
	U(1 : n, j1) = tmpvar25(:, 1);
END DO



END SUBROUTINE crnich


SUBROUTINE tridiagonal(A, D1, C, B1, n, X)
INTEGER, DIMENSION(1, (n - 1)) :: A
DOUBLE PRECISION, DIMENSION(1, n) :: D
INTEGER, DIMENSION(1, (n - 1)) :: C
DOUBLE PRECISION, DIMENSION(n) :: B
INTEGER :: n
DOUBLE PRECISION, DIMENSION(n) :: X
DOUBLE PRECISION :: mult
INTEGER :: k
INTEGER :: k1
INTEGER :: k2
DOUBLE PRECISION, DIMENSION(1, n) :: D1
DOUBLE PRECISION, DIMENSION(n) :: B1


D = D1
B = B1
DO k2 = 2 , n
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	! %-----------------------------------------------------------------------
! %
! %	This function M-file finds the solution of a tridiagonal
! %	linear system. It is assumed that D and B have dimension
! %	n, and that A and C have dimension n-1.
! %
! %	Invocation:
! %		>> X = tridiagonal(A, D, C, B)
! %
! %		where
! %
! %		i. A is a subdiagonal row vector,
! %
! %		i. D is a diagonal row vector,
! %
! %		i. C is the superdiagonal row vector,
! %
! %		i. B is the right-hand side row vector,
! %
! %		o. X is the solution row vector.
! %
! %	Requirements:
! %		None.
! %
! %	Examples:
! %		>> X = tridiagonal([1, 2, 3], [1, 2, 3, 4], ...
! %		[1, 2, 3], [1, 2, 3, 4])
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
! %	Author:
! %		John H. Mathews (mathews@fullerton.edu).
! %
! %	Date:
! %		March 1995.
! %
! %-----------------------------------------------------------------------
! % n = size(B, 2);
mult = (A(1, (k2 - 1)) / D(1, (k2 - 1)));
	D(1, k2) = (D(1, k2) - (mult * C(1, (k2 - 1))));
	B(k2) = (B(k2) - (mult * B((k2 - 1))));
END DO
k = k2
X(n) = (B(n) / D(1, n));
DO k1 = (n - 1) , 1 , (-1)
	
	X(k1) = ((B(k1) - (C(1, k1) * X((k1 + 1)))) / D(1, k1));
END DO

k = k1
END SUBROUTINE tridiagonal

END MODULE Mod_drv_crni

PROGRAM drv_crni
USE Mod_drv_crni
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
INTEGER :: c
DOUBLE PRECISION :: a
INTEGER :: n
INTEGER :: m
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar43

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
! %  Driver for the Crank-Nicholson solution to the
! %  one-dimensional heat equation.
! % 
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;

! % a = rand*3;
a = 2.5;

! % b = 1.5;
b = 1.5;

! % c = 2;
c = 5;

! %321; % n = floor(rand*1389);
m = 2300;

! %321; % n = floor(rand*529);
n = 2300;
DO time1 = 1 , scale
	IF ((.NOT. ALLOCATED(U))) THEN 
	ALLOCATE(U(n, m))
END IF
	CALL crnich(a, b, c, n, m, U)
END DO
time = time1
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar1(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar43 = MATMUL((t2 - t1), TRANSPOSE(tmpvar1))
! % Compute the running time in seconds
time = tmpvar43(1, 1);

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
END PROGRAM drv_crni

