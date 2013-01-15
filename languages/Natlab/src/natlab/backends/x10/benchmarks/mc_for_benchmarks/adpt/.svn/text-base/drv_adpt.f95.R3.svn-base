MODULE Mod_drv_adpt
CONTAINS

SUBROUTINE adapt(a1, b1, sz_guess, tol1, SRmat, quad, err)
DOUBLE PRECISION :: a
DOUBLE PRECISION :: b
INTEGER :: sz_guess
DOUBLE PRECISION :: tol
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: SRmat
DOUBLE PRECISION :: quad
DOUBLE PRECISION :: err
DOUBLE PRECISION, DIMENSION(6) :: SR1vec
DOUBLE PRECISION :: tol2
INTEGER :: done
DOUBLE PRECISION, DIMENSION(6) :: SRvec
DOUBLE PRECISION, DIMENSION(6) :: SR0vec
DOUBLE PRECISION :: Fb
DOUBLE PRECISION :: Fc
DOUBLE PRECISION :: a0
DOUBLE PRECISION :: Fa
INTEGER :: state
DOUBLE PRECISION :: S
DOUBLE PRECISION :: b0
INTEGER :: iterating
DOUBLE PRECISION :: c
DOUBLE PRECISION, DIMENSION(6) :: SR2vec
INTEGER :: n
INTEGER :: l
INTEGER :: m
DOUBLE PRECISION :: h
DOUBLE PRECISION :: tol0
DOUBLE PRECISION :: c0
INTEGER :: p
DOUBLE PRECISION :: tol1
INTEGER :: a1
INTEGER :: b1
INTEGER :: SRmat_d1
INTEGER :: SRmat_d1max
INTEGER :: SRmat_d2
INTEGER :: SRmat_d2max
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: SRmat_tmp
IF (ALLOCATED(SRmat)) THEN 
	IF ((.NOT. ALLOCATED(SRmat_tmp))) THEN 
	ALLOCATE(SRmat_tmp(sz_guess, 6))
END IF
	SRmat_d1 = SIZE(SRmat, 1)
	SRmat_d2 = SIZE(SRmat, 2)
END IF
tol = tol1
a = a1
b = b1























































IF ((.NOT. ALLOCATED(SRmat))) THEN 
	ALLOCATE(SRmat(sz_guess, 6))
END IF
! %-----------------------------------------------------------------------
! %
! %	This function M-file finds the adaptive quadrature using
! %	Simpson's rule.
! %
! %	This MATLAB program is intended as a pedagogical example.
! %
! %	Invocation:
! %		>> [SRmat, quad, err] = adapt(a, b, sz_guess, tol)
! %
! %		where
! %
! %		i. a is the left endpoint of [a, b],
! %
! %		i. b is the right endpoint of [a, b],
! %
! %		i. sz_guess is the number of rows in SRmat,
! %
! %		i. tol is the convergence tolerance,
! %
! %		o. SRmat is the matrix of adaptive Simpson
! %		   quadrature values,
! %
! %		o. quad is the adaptive Simpson quadrature,
! %
! %		o. err is the error estimate.
! %
! %	Requirements:
! %		a <= b.
! %
! %	Examples:
! %		>> [SRmat, quad, err] = adapt(-1, 6, 1, 1.0000e-12)
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
SRmat = 0;
iterating = 0;
done = 1;

! % The step size.
h = (((b - a) + 0.0) / 2);

! % The midpoint in the interval.
c = (((a + b) + 0.0) / 2);
Fa = ((13 * (a - (a ** 2))) * exp((((-3) * a) / 2)));
Fc = ((13 * (c - (c ** 2))) * exp((((-3) * c) / 2)));
Fb = ((13 * (b - (b ** 2))) * exp((((-3) * b) / 2)));

! % Simpson's rule.
S = ((h * ((Fa + (4 * Fc)) + Fb)) / 3);
SRvec = [a, b, S, S, tol, tol];
SRmat(1, 1 : 6) = SRvec;
m = 1;
state = iterating;
DO  
	
	
	
	
	
	
	
	
	
	
	
	IF ((state == iterating)) THEN 
	n = m;
	DO l = n , 1 , (-1)
	p = l;
	SR0vec = SRmat(p, :);
	err = SR0vec(5);
	tol = SR0vec(6);
	IF ((tol <= err)) THEN 
	state = done;
	SR1vec = SR0vec;
	SR2vec = SR0vec;
	
	! % Left endpoint.
a = SR0vec(1);
	
	! % Right endpoint.
b = SR0vec(2);
	
	! % Midpoint.
c = ((a + b) / 2);
	err = SR0vec(5);
	tol = SR0vec(6);
	tol2 = (tol / 2);
	a0 = a;
	b0 = c;
	tol0 = tol2;
	h = ((b0 - a0) / 2);
	c0 = ((a0 + b0) / 2);
	Fa = ((13 * (a0 - (a0 ** 2))) * exp((((-3) * a0) / 2)));
	Fc = ((13 * (c0 - (c0 ** 2))) * exp((((-3) * c0) / 2)));
	Fb = ((13 * (b0 - (b0 ** 2))) * exp((((-3) * b0) / 2)));
	
	! % Simpson's rule.
S = ((h * ((Fa + (4 * Fc)) + Fb)) / 3);
	SR1vec = [a0, b0, S, S, tol0, tol0];
	a0 = c;
	b0 = b;
	tol0 = tol2;
	h = ((b0 - a0) / 2);
	c0 = ((a0 + b0) / 2);
	Fa = ((13 * (a0 - (a0 ** 2))) * exp((((-3) * a0) / 2)));
	Fc = ((13 * (c0 - (c0 ** 2))) * exp((((-3) * c0) / 2)));
	Fb = ((13 * (b0 - (b0 ** 2))) * exp((((-3) * b0) / 2)));
	
	! % Simpson's rule.
S = ((h * ((Fa + (4 * Fc)) + Fb)) / 3);
	SR2vec = [a0, b0, S, S, tol0, tol0];
	err = (abs(((SR0vec(3) - SR1vec(3)) - SR2vec(3))) / 10);
	IF ((err < tol)) THEN 
	IF (((p > SRmat_d1) .OR. (SRmat_d2 > SRmat_d2))) THEN 
	IF (ALLOCATED(SRmat_tmp)) THEN 
	DEALLOCATE(SRmat_tmp)
END IF
	ALLOCATE(SRmat_tmp(SRmat_d1, SRmat_d2))
	SRmat_tmp = SRmat
	IF (ALLOCATED(SRmat)) THEN 
	DEALLOCATE(SRmat)
END IF
	SRmat_d1max = MAX(SRmat_d1, p)
	SRmat_d2max = MAX(SRmat_d2, SRmat_d2)
	ALLOCATE(SRmat(SRmat_d1max, SRmat_d2max))
	SRmat(1 : SRmat_d1, 1 : SRmat_d2) = SRmat_tmp(1 : SRmat_d1, 1 : SRmat_d2)
	SRmat_d1 = SRmat_d1max
	SRmat_d2 = SRmat_d2max
END IF
	SRmat(p, :) = SR0vec;
	IF (((p > SRmat_d1) .OR. (4 > SRmat_d2))) THEN 
	IF (ALLOCATED(SRmat_tmp)) THEN 
	DEALLOCATE(SRmat_tmp)
END IF
	ALLOCATE(SRmat_tmp(SRmat_d1, SRmat_d2))
	SRmat_tmp = SRmat
	IF (ALLOCATED(SRmat)) THEN 
	DEALLOCATE(SRmat)
END IF
	SRmat_d1max = MAX(SRmat_d1, p)
	SRmat_d2max = MAX(SRmat_d2, 4)
	ALLOCATE(SRmat(SRmat_d1max, SRmat_d2max))
	SRmat(1 : SRmat_d1, 1 : SRmat_d2) = SRmat_tmp(1 : SRmat_d1, 1 : SRmat_d2)
	SRmat_d1 = SRmat_d1max
	SRmat_d2 = SRmat_d2max
END IF
	SRmat(p, 4) = (SR1vec(3) + SR2vec(3));
	IF (((p > SRmat_d1) .OR. (5 > SRmat_d2))) THEN 
	IF (ALLOCATED(SRmat_tmp)) THEN 
	DEALLOCATE(SRmat_tmp)
END IF
	ALLOCATE(SRmat_tmp(SRmat_d1, SRmat_d2))
	SRmat_tmp = SRmat
	IF (ALLOCATED(SRmat)) THEN 
	DEALLOCATE(SRmat)
END IF
	SRmat_d1max = MAX(SRmat_d1, p)
	SRmat_d2max = MAX(SRmat_d2, 5)
	ALLOCATE(SRmat(SRmat_d1max, SRmat_d2max))
	SRmat(1 : SRmat_d1, 1 : SRmat_d2) = SRmat_tmp(1 : SRmat_d1, 1 : SRmat_d2)
	SRmat_d1 = SRmat_d1max
	SRmat_d2 = SRmat_d2max
END IF
	SRmat(p, 5) = err;
ELSE 
	IF ((((m + 1) > SRmat_d1) .OR. (SRmat_d2 > SRmat_d2))) THEN 
	IF (ALLOCATED(SRmat_tmp)) THEN 
	DEALLOCATE(SRmat_tmp)
END IF
	ALLOCATE(SRmat_tmp(SRmat_d1, SRmat_d2))
	SRmat_tmp = SRmat
	IF (ALLOCATED(SRmat)) THEN 
	DEALLOCATE(SRmat)
END IF
	SRmat_d1max = MAX(SRmat_d1, (m + 1))
	SRmat_d2max = MAX(SRmat_d2, SRmat_d2)
	ALLOCATE(SRmat(SRmat_d1max, SRmat_d2max))
	SRmat(1 : SRmat_d1, 1 : SRmat_d2) = SRmat_tmp(1 : SRmat_d1, 1 : SRmat_d2)
	SRmat_d1 = SRmat_d1max
	SRmat_d2 = SRmat_d2max
END IF
	SRmat((p + 1) : (m + 1), :) = SRmat(p : m, :);
	m = (m + 1);
	IF (((p > SRmat_d1) .OR. (SRmat_d2 > SRmat_d2))) THEN 
	IF (ALLOCATED(SRmat_tmp)) THEN 
	DEALLOCATE(SRmat_tmp)
END IF
	ALLOCATE(SRmat_tmp(SRmat_d1, SRmat_d2))
	SRmat_tmp = SRmat
	IF (ALLOCATED(SRmat)) THEN 
	DEALLOCATE(SRmat)
END IF
	SRmat_d1max = MAX(SRmat_d1, p)
	SRmat_d2max = MAX(SRmat_d2, SRmat_d2)
	ALLOCATE(SRmat(SRmat_d1max, SRmat_d2max))
	SRmat(1 : SRmat_d1, 1 : SRmat_d2) = SRmat_tmp(1 : SRmat_d1, 1 : SRmat_d2)
	SRmat_d1 = SRmat_d1max
	SRmat_d2 = SRmat_d2max
END IF
	SRmat(p, :) = SR1vec;
	IF ((((p + 1) > SRmat_d1) .OR. (SRmat_d2 > SRmat_d2))) THEN 
	IF (ALLOCATED(SRmat_tmp)) THEN 
	DEALLOCATE(SRmat_tmp)
END IF
	ALLOCATE(SRmat_tmp(SRmat_d1, SRmat_d2))
	SRmat_tmp = SRmat
	IF (ALLOCATED(SRmat)) THEN 
	DEALLOCATE(SRmat)
END IF
	SRmat_d1max = MAX(SRmat_d1, (p + 1))
	SRmat_d2max = MAX(SRmat_d2, SRmat_d2)
	ALLOCATE(SRmat(SRmat_d1max, SRmat_d2max))
	SRmat(1 : SRmat_d1, 1 : SRmat_d2) = SRmat_tmp(1 : SRmat_d1, 1 : SRmat_d2)
	SRmat_d1 = SRmat_d1max
	SRmat_d2 = SRmat_d2max
END IF
	SRmat((p + 1), :) = SR2vec;
	state = iterating;
END IF
	
END IF
	
	
END DO
ELSE 
	EXIT
END IF
	
	
	
	
END DO











quad =  (0.0+SUM(SRmat(:, 4)));
err =  (0.0+SUM(abs(SRmat(:, 5))));
SRmat = SRmat(1 : m, 1 : 6);
IF (ALLOCATED(SRmat_tmp)) THEN 
	DEALLOCATE(SRmat_tmp)
END IF
END SUBROUTINE adapt

END MODULE Mod_drv_adpt

PROGRAM drv_adpt
USE Mod_drv_adpt
IMPLICIT NONE



INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(3) :: output
CHARACTER(3) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
CHARACTER(10) :: arg_buffer
DOUBLE PRECISION :: timing_clock
DOUBLE PRECISION :: tmpvar4
DOUBLE PRECISION :: tmpvar5
DOUBLE PRECISION :: tmpvar3
INTEGER, DIMENSION(1, 6) :: tmpvar1
INTEGER :: sz_guess
DOUBLE PRECISION :: tol
INTEGER :: b
INTEGER :: int_tmpvar
INTEGER :: a
INTEGER :: i
DOUBLE PRECISION :: quad
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: SRmat
DOUBLE PRECISION :: err
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar51

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
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;
a = (-1);
b = 6;
sz_guess = 1;
tol = 4e-13;
DO i = 1 , scale
	IF ((.NOT. ALLOCATED(SRmat))) THEN 
	ALLOCATE(SRmat(sz_guess, 6))
END IF
	CALL adapt(a, b, sz_guess, tol, SRmat, quad, err)
END DO
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar1(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar51 = MATMUL((t2 - t1), TRANSPOSE(tmpvar1))
! % Compute the running time in seconds
time = tmpvar51(1, 1);

tmpvar3 =  (SUM(SRmat)/(0.0+SIZE(SRmat)));
tmpvar4 = quad;
tmpvar5 = err;
! % Store the benchmark output
output = [tmpvar3, tmpvar4, tmpvar5];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
IF (ALLOCATED(SRmat)) THEN 
	DEALLOCATE(SRmat)
END IF
END PROGRAM drv_adpt

