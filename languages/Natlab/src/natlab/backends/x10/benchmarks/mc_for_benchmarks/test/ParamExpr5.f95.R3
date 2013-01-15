
PROGRAM drv_foo
IMPLICIT NONE
INTEGER, DIMENSION(:, :), ALLOCATABLE :: F
REAL, DIMENSION(:, :), ALLOCATABLE :: a
INTEGER :: n
INTEGER, DIMENSION(:, :), ALLOCATABLE :: m






! % This test case shows
! % Column-vector and row-vector's basic operations 
! % Implicitly using vector as one dimension array
n = floor(2.0);

IF ((.NOT. ALLOCATED(a))) THEN 
	ALLOCATE(a(n, 3))
END IF
a = 1;
IF ((.NOT. ALLOCATED(F))) THEN 
	ALLOCATE(F(n, 3))
END IF
F = 1;
IF ((.NOT. ALLOCATED(m))) THEN 
	ALLOCATE(m(n, 1))
END IF
m = 1;



! % automatically transform vectors to one-dimensions 
! % Implicitly using m as m(:,1)
a(:, 1) = (F(:, 1) / m(:, 1));

DEALLOCATE(F)
DEALLOCATE(a)
DEALLOCATE(m)
END PROGRAM drv_foo

