MODULE Mod_drv_foo
CONTAINS

SUBROUTINE rand1(m, n, seed1, D)
INTEGER :: m
INTEGER :: n
DOUBLE PRECISION :: seed
INTEGER, DIMENSION(m, n) :: D
DOUBLE PRECISION :: seed1
seed = seed1


seed = (seed + (m * n));
D = 0;
END SUBROUTINE rand1


SUBROUTINE foo(F)
INTEGER, DIMENSION(2, 3) :: F


CALL rand1(2, 3, 4, F)
END SUBROUTINE foo

END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER, DIMENSION(2, 3) :: F
INTEGER :: n
INTEGER, DIMENSION(:, :), ALLOCATABLE :: Rx




! % Test case for renaming variable from 'nb1d'
! % Inside function rand2(), one of 'M' and 'm' should be renamed
n = floor(((28 * rand1()) + 0.0));
IF ((.NOT. ALLOCATED(Rx))) THEN 
	ALLOCATE(Rx(n, 1))
END IF
CALL rand1(n, 1, .1, Rx)
CALL foo(F)
DEALLOCATE(Rx)
END PROGRAM drv_foo

