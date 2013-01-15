MODULE Mod_drv_capr
CONTAINS

SUBROUTINE capacitor(a, b, c, d, n, tol, rel, cap)
DOUBLE PRECISION :: a
DOUBLE PRECISION :: b
DOUBLE PRECISION :: c
DOUBLE PRECISION :: d
INTEGER :: n
DOUBLE PRECISION :: tol
DOUBLE PRECISION :: rel
DOUBLE PRECISION :: cap
INTEGER :: tmpvar8
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: mask
INTEGER :: jj
DOUBLE PRECISION :: oldcap
INTEGER :: tmpvar14
INTEGER :: tmpvar18
INTEGER :: tmpvar17
INTEGER, DIMENSION(:, :), ALLOCATABLE :: tmpvar19
INTEGER :: mb
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: f
INTEGER :: ii
INTEGER :: m
DOUBLE PRECISION :: h
INTEGER :: na
INTEGER :: iter
DOUBLE PRECISION, DIMENSION(:), ALLOCATABLE :: y
DOUBLE PRECISION, DIMENSION(:), ALLOCATABLE :: x








































! %-----------------------------------------------------------------------
! %
! %	This function M-file computes the capacitance
! %	per unit length of a coaxial pair of rectangles.
! %
! %	Invocation:
! %		>> cap = capacitor(a, b, c, d, n, tol, rel)
! %
! %		where
! %
! %		i. a is the width of the inner conductor,
! %
! %		i. b is the height of the inner conductor,
! %
! %		i. c is the width of the outer conductor,
! %
! %		i. d is the height of the outer conductor,
! %
! %		i. n is the number of points along the x-axis,
! %
! %		i. tol is the tolerance,
! %
! %		i. rel is the relaxation parameter,
! %
! %		o. cap is the capacitance per unit length.
! %
! %	Requirements:
! %		None.
! %
! %	Examples:
! %		>> cap = capacitor(1, 2, 2, 3, 50, 1e-9, 1.90)
! %
! %	Source:
! %		Computational Electromagnetics - EEK 170 course at
! %		http://www.elmagn.chalmers.se/courses/CEM/.
! %
! %-----------------------------------------------------------------------
! % Grid size.
h = ((0.5 * c) / n);
na = floor(0.5+((0.5 * a) / h)) ;
tmpvar8 = (n + 1);
IF ((.NOT. ALLOCATED(x))) THEN 
	ALLOCATE(x((((tmpvar8 - 2) + 1) + 1)))
END IF
CALL linspace(0, (0.5 * c), (n + 1), x)
m = floor(0.5+((0.5 * d) / h)) ;
mb = floor(0.5+((0.5 * b) / h)) ;
tmpvar14 = (m + 1);
IF ((.NOT. ALLOCATED(y))) THEN 
	ALLOCATE(y((((tmpvar14 - 2) + 1) + 1)))
END IF
CALL linspace(0, (0.5 * d), (m + 1), y)

IF ((.NOT. ALLOCATED(f))) THEN 
	ALLOCATE(f((n + 1), (m + 1)))
END IF
! % Initialize potential and mask array.
f = 0;
tmpvar17 = (n + 1);
tmpvar18 = (m + 1);
IF ((.NOT. ALLOCATED(tmpvar19))) THEN 
	ALLOCATE(tmpvar19(tmpvar17, tmpvar18))
END IF
tmpvar19 = 1;
IF ((.NOT. ALLOCATED(mask))) THEN 
	ALLOCATE(mask(tmpvar17, tmpvar18))
END IF
mask = (tmpvar19 * rel);
DO ii = 1 , (na + 1)
	
	
	DO jj = 1 , (mb + 1)
	mask(ii, jj) = 0;
	f(ii, jj) = 1;
END DO
END DO


oldcap = 0;
DO iter = 1 , 1000
	
	
	CALL seidel(f, mask, n, m, na, mb, f)
	CALL gauss(n, m, h, f, cap)
	IF (((abs((cap - oldcap)) / cap) < tol)) THEN 
	EXIT;
ELSE 
	oldcap = cap;
END IF
END DO


IF (ALLOCATED(mask)) THEN 
	DEALLOCATE(mask)
END IF
IF (ALLOCATED(tmpvar19)) THEN 
	DEALLOCATE(tmpvar19)
END IF
IF (ALLOCATED(f)) THEN 
	DEALLOCATE(f)
END IF
IF (ALLOCATED(y)) THEN 
	DEALLOCATE(y)
END IF
IF (ALLOCATED(x)) THEN 
	DEALLOCATE(x)
END IF
END SUBROUTINE capacitor


SUBROUTINE gauss(n, m, h, f, cap)
INTEGER :: n
INTEGER :: m
DOUBLE PRECISION :: h
DOUBLE PRECISION, DIMENSION((n + 1), (m + 1)) :: f
DOUBLE PRECISION :: cap
INTEGER :: jj
INTEGER :: ii
DOUBLE PRECISION :: q































! %-----------------------------------------------------------------------
! %
! %	This function M-file computes capacitance from the
! %	potential.
! %
! %	Invocation:
! %		>> cap = gauss(n, m, h, f)
! %
! %		where
! %
! %		i. n is the number of points along the x-axis,
! %
! %		i. m is the number of points along the height of
! %		   the outer conductor,
! %
! %		i. f is the potential array,
! %
! %		i. h is the grid size,
! %
! %		o. cap is the capacitance.
! %
! %	Requirements:
! %		None.
! %
! %	Source:
! %		Computational Electromagnetics - EEK 170 course at
! %		http://www.elmagn.chalmers.se/courses/CEM/.
! %
! %-----------------------------------------------------------------------
q = 0;
DO ii = 1 , n
	
	q = (q + ((f(ii, m) + f((ii + 1), m)) * 0.5));
END DO

DO jj = 1 , m
	
	q = (q + ((f(n, jj) + f(n, (jj + 1))) * 0.5));
END DO


! % Four quadrants.
cap = (q * 4);
cap = (cap * 8.854187);
END SUBROUTINE gauss


SUBROUTINE seidel(f1, mask, n, m, na, mb, f)
DOUBLE PRECISION, DIMENSION((n + 1), (m + 1)) :: f1
DOUBLE PRECISION, DIMENSION((n + 1), (m + 1)) :: mask
INTEGER :: n
INTEGER :: m
INTEGER :: na
INTEGER :: mb
DOUBLE PRECISION, DIMENSION((n + 1), (m + 1)) :: f
INTEGER :: jj
INTEGER :: ii2
INTEGER :: ii1
INTEGER :: ii
INTEGER :: jj2
INTEGER :: jj1
f = f1


DO ii1 = 2 , n
	
	DO jj2 = 2 , m
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	! %-----------------------------------------------------------------------
! %
! %	This function M-file makes one Seidel iteration.
! %
! %	Invocation:
! %		>> g = seidel(f, mask, n, m, na, mb)
! %
! %		where
! %
! %		i. f is the potential array,
! %
! %		i. mask is the mask array,
! %
! %		i. n is the number of points along the x-axis,
! %
! %		i. m is the number of points along the height of
! %		   the outer conductor,
! %
! %		i. na is the number of points along the width of
! %		   the inner conductor,
! %
! %		i. mb is the number of points along the height of
! %		   the inner conductor,
! %
! %		o. g is the updated potential array.
! %
! %	Requirements:
! %		None.
! %
! %	Source:
! %		Computational Electromagnetics - EEK 170 course at
! %		http://www.elmagn.chalmers.se/courses/CEM/.
! %
! %-----------------------------------------------------------------------
! ...
! ...
f(ii1, jj2) = (f(ii1, jj2) + (mask(ii1, jj2) * ((0.25 * (((f((ii1 - 1), jj2) + f((ii1 + 1)&
&, jj2)) + f(ii1, (jj2 - 1))) + f(ii1, (jj2 + 1)))) - f(ii1, jj2))));
END DO
	jj = jj2
END DO

ii = ii1

! % Symmetry on left boundary ii-1 -> ii+1.
ii = 1;
DO jj1 = 2 , m
	
	! ...
f(ii, jj1) = (f(ii, jj1) + (mask(ii, jj1) * ((0.25 * (((f((ii + 1), jj1) + f((ii + 1), jj1&
&)) + f(ii, (jj1 - 1))) + f(ii, (jj1 + 1)))) - f(ii, jj1))));
END DO

jj = jj1

! % Symmetry on lower boundary jj-1 -> jj+1.
jj = 1;
DO ii2 = 2 , n
	
	! ...
f(ii2, jj) = (f(ii2, jj) + (mask(ii2, jj) * ((0.25 * (((f((ii2 - 1), jj) + f((ii2 + 1), jj&
&)) + f(ii2, (jj + 1))) + f(ii2, (jj + 1)))) - f(ii2, jj))));
END DO

ii = ii2
END SUBROUTINE seidel


SUBROUTINE linspace(d1, d2, n, y)
INTEGER :: d1
DOUBLE PRECISION :: d2
INTEGER :: n
DOUBLE PRECISION, DIMENSION((n-2)+1+1) :: y
DOUBLE PRECISION, DIMENSION((n-2)+1) :: tmpvar28
INTEGER :: III

III = 0











III = III
tmpvar28 = (d1 + (((/(III, III=0 , (n - 2))/) * (d2 - d1)) / (n - 1)));
! %-----------------------------------------------------------------------
! %LINSPACE Linearly spaced vector.
! %   LINSPACE(x1, x2) generates a row vector of n linearly
! %   equally spaced points between x1 and x2.
! %
! %   See also LOGSPACE, :.
! %
! %   Copyright 1984-2001 The MathWorks, Inc. 
! %   $Revision: 5.11 $  $Date: 2001/04/15 12:02:30 $
! %-----------------------------------------------------------------------
y = [tmpvar28, d2];
END SUBROUTINE linspace

END MODULE Mod_drv_capr

PROGRAM drv_capr
USE Mod_drv_capr
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(1) :: output
CHARACTER(3) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
CHARACTER(10) :: arg_buffer
DOUBLE PRECISION :: cap
DOUBLE PRECISION :: timing_clock
DOUBLE PRECISION :: tmpvar4
INTEGER, DIMENSION(1, 6) :: tmpvar2
INTEGER :: time1
DOUBLE PRECISION :: tol
DOUBLE PRECISION :: d
DOUBLE PRECISION :: b
INTEGER :: int_tmpvar
DOUBLE PRECISION :: c
DOUBLE PRECISION :: a
INTEGER :: n
DOUBLE PRECISION :: rel
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar69

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
! %  Driver to compute the capacitance of a transmission line using
! %  finite difference and Gauss-Seidel iteration.
! % 
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;

! % the numbers in parentheses are "rand's" made deterministic
a = (0.3257463 * 2);
b = (8.65 * 0.04039);
c = (3.29 * 0.55982);
d = (0.727561 * 6.171);
n = floor((56.0980 * 0.36));

! % Tolerance.
tol = 1.3e-13;

! % Relaxation parameter.
rel = 0.90;
DO time1 = 1 , (scale * 10)
	CALL capacitor(a, b, c, d, n, tol, rel, cap)
END DO
time = time1
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar2(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar69 = MATMUL((t2 - t1), TRANSPOSE(tmpvar2))
! % Compute the running time in seconds
time = tmpvar69(1, 1);

tmpvar4 = cap;
! % Store the benchmark output
output = [tmpvar4];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
END PROGRAM drv_capr

