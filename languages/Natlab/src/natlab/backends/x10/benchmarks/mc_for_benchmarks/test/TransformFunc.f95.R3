MODULE Mod_drv_foo
CONTAINS

SUBROUTINE foo(A, n, B)
INTEGER, DIMENSION(n) :: A
INTEGER :: n
INTEGER, DIMENSION(n) :: B





B = A
END SUBROUTINE foo

END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER, DIMENSION(:), ALLOCATABLE :: A
INTEGER, DIMENSION(:), ALLOCATABLE :: B
INTEGER, DIMENSION(:, :), ALLOCATABLE :: C
INTEGER :: n
INTEGER, DIMENSION(:), ALLOCATABLE :: tmpvar1
INTEGER :: III

III = 0







! % This test case shows
! % When function is transformed into subroutine, 
! % caller side also needs proper transformation.
! % And dynamic allocation code must correctly put 
! % in front of the first use of that variable
n = floor(2.5)
III = III
IF ((.NOT. ALLOCATED(A))) THEN 
	ALLOCATE(A(n))
END IF
A = (/(III, III=1 , n)/)

IF ((.NOT. ALLOCATED(B))) THEN 
	ALLOCATE(B(n))
END IF
CALL foo(A, n, B)

IF ((.NOT. ALLOCATED(C))) THEN 
	ALLOCATE(C(n, 3))
END IF
C = 0
IF ((.NOT. ALLOCATED(tmpvar1))) THEN 
	ALLOCATE(tmpvar1(n))
END IF
CALL foo(A, n, tmpvar1)
C(:, 1) = tmpvar1

IF (ALLOCATED(A)) THEN 
	DEALLOCATE(A)
END IF
IF (ALLOCATED(B)) THEN 
	DEALLOCATE(B)
END IF
IF (ALLOCATED(C)) THEN 
	DEALLOCATE(C)
END IF
IF (ALLOCATED(tmpvar1)) THEN 
	DEALLOCATE(tmpvar1)
END IF
END PROGRAM drv_foo

