
PROGRAM drv_foo
IMPLICIT NONE
INTEGER :: n
REAL :: tmpvar1
REAL, DIMENSION(:, :), ALLOCATABLE :: Rx




CALL RANDOM_NUMBER(tmpvar1);
! % Test case for renaming variable from 'nb1d'
! % Inside function rand2(), one of 'M' and 'm' should be renamed
n = floor((28 * tmpvar1));
IF ((.NOT. ALLOCATED(Rx))) THEN 
	ALLOCATE(Rx(n, 1))
END IF
CALL rand2(n, 1, .1, Rx)

DEALLOCATE(Rx)
END PROGRAM drv_foo


SUBROUTINE rand2(m, n, seed1, M1)
INTEGER :: m
INTEGER :: n
REAL :: seed
REAL, DIMENSION(m, n) :: M1
INTEGER :: j
INTEGER :: i
REAL :: seed1
seed = seed1


seed = (seed + (m * n));
M1 = 0;
DO i = 1 , m
	
	
	DO j = 1 , n
	M1(i, j) = mod(seed, 1.0);
	seed = ((seed + (M1(i, j) * sqrt(100.0))) + sqrt(2.0));
END DO
END DO


END SUBROUTINE rand2

