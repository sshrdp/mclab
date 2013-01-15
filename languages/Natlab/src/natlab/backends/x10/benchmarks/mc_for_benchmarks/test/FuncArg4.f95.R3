MODULE Mod_drv_foo
CONTAINS

SUBROUTINE seidel(f1, n, f)
DOUBLE PRECISION, DIMENSION(n, n) :: f1
INTEGER :: n
DOUBLE PRECISION, DIMENSION(n, n) :: f
f = f1




f(1, 1) = 1.1;
END SUBROUTINE seidel


SUBROUTINE seidel2(set1, n, R)
DOUBLE PRECISION, DIMENSION(n, n) :: set
INTEGER :: n
DOUBLE PRECISION, DIMENSION(n, n) :: R
INTEGER, DIMENSION(n, n) :: set1
set = set1







! % using bridge variable to avoid the change on the input parameters
set(1, 1) = 1.1;
R = set
END SUBROUTINE seidel2

END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: result
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: set
INTEGER :: N
INTEGER, DIMENSION(:, :), ALLOCATABLE :: set2






! % This test case shows:
! % The sub-function called by an argument
! % which type changed after calling 
! % taken from 'capr'
N = floor(((2 ** 1) + 0.0));
IF ((.NOT. ALLOCATED(set))) THEN 
	ALLOCATE(set(N, N))
END IF
set = 1;
CALL seidel(set, N, set)

IF ((.NOT. ALLOCATED(set2))) THEN 
	ALLOCATE(set2(N, N))
END IF
set2 = 1;
IF ((.NOT. ALLOCATED(result))) THEN 
	ALLOCATE(result(N, N))
END IF
CALL seidel2(set2, N, result)
IF (ALLOCATED(result)) THEN 
	DEALLOCATE(result)
END IF
IF (ALLOCATED(set)) THEN 
	DEALLOCATE(set)
END IF
IF (ALLOCATED(set2)) THEN 
	DEALLOCATE(set2)
END IF
END PROGRAM drv_foo

