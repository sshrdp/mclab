MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER, DIMENSION(:, :), ALLOCATABLE :: A
INTEGER, DIMENSION(:, :), ALLOCATABLE :: B
INTEGER :: n
INTEGER :: III
DOUBLE PRECISION :: y
INTEGER, DIMENSION(1, 1) :: Z
DOUBLE PRECISION :: x

III = 0




! % Following code segment, 
! % Type inference should have 2 iterations.
x = 0;
y = (x + 1);
x = (x + 0.5);






! % Following code segment, 
! % Type inference should have 2 iterations.
! % During 2nd iteration, A=1:n -> A(1,:)=1:n 
! % 
n = floor(((3 ** 1) + 0.0));
III = III
IF ((.NOT. ALLOCATED(A))) THEN 
	ALLOCATE(A(1, n))
END IF
A(1, :) = (/(III, III=1 , n)/);
IF ((.NOT. ALLOCATED(B))) THEN 
	ALLOCATE(B(n, 1))
END IF
B = 1;
Z = MATMUL(A, B);




IF (ALLOCATED(A)) THEN 
	DEALLOCATE(A)
END IF
IF (ALLOCATED(B)) THEN 
	DEALLOCATE(B)
END IF
END PROGRAM drv_foo

