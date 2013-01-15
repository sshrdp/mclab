MODULE Mod_drv_foo
CONTAINS

SUBROUTINE foo(side, set)
INTEGER :: side
INTEGER, DIMENSION(((side-1)+1), ((side-1)+1)) :: set
INTEGER, DIMENSION(((side - 1) + 1), ((side - 1) + 1)) :: B
INTEGER :: tmpvar4
INTEGER :: tmpvar5
INTEGER :: tmpvar3
INTEGER :: y
INTEGER :: x




DO x = 0 , (side - 1)
	DO y = 0 , (side - 1)
	tmpvar3 = (y + 1);
	tmpvar4 = (x + 1);
	CALL iterations(x, y, tmpvar5)
	set((y + 1), (x + 1)) = tmpvar5;
END DO
END DO
CALL seidel(set, side, B)
END SUBROUTINE foo


SUBROUTINE iterations(x, max, out)
INTEGER :: x
INTEGER :: max
INTEGER :: out




out = (x + max);
END SUBROUTINE iterations


SUBROUTINE seidel(f1, n, f)
INTEGER, DIMENSION(((n - 1) + 1), ((n - 1) + 1)) :: f1
INTEGER :: n
INTEGER, DIMENSION(((n - 1) + 1), ((n - 1) + 1)) :: f
f = f1






! % The 2nd 'f' will be renamed
f(1, 1) = 0;
END SUBROUTINE seidel

END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER, DIMENSION(:, :), ALLOCATABLE :: set
INTEGER :: N






! % This test case shows:
! % The calling sequence from 
! % drv_foo()->foo()->iterations()
N = floor(((100 ** 1) + 0.0))
IF ((.NOT. ALLOCATED(set))) THEN 
	ALLOCATE(set(((N - 1) + 1), ((N - 1) + 1)))
END IF
CALL foo(N, set)
PRINT *,size(set)
IF (ALLOCATED(set)) THEN 
	DEALLOCATE(set)
END IF
END PROGRAM drv_foo

