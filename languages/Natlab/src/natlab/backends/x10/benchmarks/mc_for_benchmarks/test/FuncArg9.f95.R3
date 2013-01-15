MODULE Mod_drv_foo
CONTAINS

SUBROUTINE adapt(a, b, sz_guess, tol, SRmat, quad, err)
INTEGER :: a
INTEGER :: b
INTEGER :: sz_guess
DOUBLE PRECISION :: tol
INTEGER, DIMENSION(sz_guess, 6) :: SRmat
DOUBLE PRECISION :: quad
DOUBLE PRECISION :: err
SRmat = 0;
quad =  (0.0+SUM(SRmat(:, 4)));
err =  (0.0+SUM(abs(SRmat(:, 5))));
SRmat = SRmat(1 : 1, 1 : 6);


END SUBROUTINE adapt

END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: quad
INTEGER :: b
INTEGER, DIMENSION(:, :), ALLOCATABLE :: SRmat
INTEGER :: a
DOUBLE PRECISION :: err
INTEGER :: sz_guess
DOUBLE PRECISION :: tol
DO int_tmpvar = 1 , IARGC()
	CALL GETARG(int_tmpvar, arg_buffer) 
	IF ((int_tmpvar == 1)) THEN 
	READ(arg_buffer, *) scale
END IF
END DO


! % This is for 'adpt'  
a = (-1);
b = 6;
sz_guess = 1;
tol = 4e-13;
IF ((.NOT. ALLOCATED(SRmat))) THEN 
	ALLOCATE(SRmat(sz_guess, 6))
END IF
CALL adapt(a, b, sz_guess, tol, SRmat, quad, err)

IF (ALLOCATED(SRmat)) THEN 
	DEALLOCATE(SRmat)
END IF
END PROGRAM drv_foo

