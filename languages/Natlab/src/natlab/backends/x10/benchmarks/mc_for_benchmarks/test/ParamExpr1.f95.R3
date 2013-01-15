MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER, DIMENSION(:, :), ALLOCATABLE :: D
INTEGER, DIMENSION(1, 1) :: E
INTEGER, DIMENSION(:, :), ALLOCATABLE :: A
INTEGER, DIMENSION(:, :), ALLOCATABLE :: B
INTEGER, DIMENSION(:, :), ALLOCATABLE :: C
INTEGER :: n
INTEGER :: m
INTEGER :: i
INTEGER :: III
INTEGER :: x

III = 0





! % This test case shows
! % Column-vector and row-vector's basic operations 
! % Linear-indexing on vectors
n = floor(4.0);
IF ((.NOT. ALLOCATED(A))) THEN 
	ALLOCATE(A(n, 1))
END IF
A = 0;
A(3, 1) = 1
IF ((.NOT. ALLOCATED(B))) THEN 
	ALLOCATE(B(1, n))
END IF
B = 0;
B(1, 2) = 2
m = (n - 1)
A(m, 1) = 3
B(1, m) = 4

x = 1
DO i = 1 , (m + 1)
	
	
	A(i, 1) = x
	x = B(1, (n - i))
END DO




III = III
! % automatically transform vector assignment
A(:, 1) = (/(III, III=1 , n)/)
B(1, :) = (/(III, III=n , 1 , (-1))/)



IF ((.NOT. ALLOCATED(C))) THEN 
	ALLOCATE(C(1, n))
END IF
! % automatically transform vectors to two-dimensions 
! % to accommodate the mutiplicate and transpose operations
C(1, :) = (/(III, III=1 , n)/)
IF ((.NOT. ALLOCATED(D))) THEN 
	ALLOCATE(D(1, (((1 - n) / (-1)) + 1)))
END IF
D(1, :) = (/(III, III=n , 1 , (-1))/)
E = MATMUL(C, TRANSPOSE(D))
PRINT *,A
PRINT *,B
PRINT *,C
PRINT *,D
PRINT *,E
IF (ALLOCATED(D)) THEN 
	DEALLOCATE(D)
END IF
IF (ALLOCATED(A)) THEN 
	DEALLOCATE(A)
END IF
IF (ALLOCATED(B)) THEN 
	DEALLOCATE(B)
END IF
IF (ALLOCATED(C)) THEN 
	DEALLOCATE(C)
END IF
END PROGRAM drv_foo

