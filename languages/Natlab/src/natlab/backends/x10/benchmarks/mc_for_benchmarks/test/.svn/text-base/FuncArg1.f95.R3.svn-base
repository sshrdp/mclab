MODULE Mod_drv_foo
CONTAINS

SUBROUTINE foo(n, t)
INTEGER :: n
INTEGER :: t
INTEGER, DIMENSION(n, t) :: A





A = 0
END SUBROUTINE foo


SUBROUTINE bar(x, y, B)
INTEGER :: x
INTEGER :: y
INTEGER, DIMENSION(x, y) :: B





B = 0
END SUBROUTINE bar

END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER :: scale
INTEGER :: m
CHARACTER(10) :: arg_buffer
INTEGER :: int_tmpvar
INTEGER, DIMENSION(:, :), ALLOCATABLE :: B
INTEGER, DIMENSION(:, :), ALLOCATABLE :: set
INTEGER :: N
INTEGER :: tmpvar2
INTEGER :: tmpvar3

int_tmpvar = 0
arg_buffer = '0000000000'
DO int_tmpvar = 1 , IARGC()
	CALL GETARG(int_tmpvar, arg_buffer) 
	IF ((int_tmpvar == 1)) THEN 
	READ(arg_buffer, *) scale
END IF
	IF ((int_tmpvar == 2)) THEN 
	READ(arg_buffer, *) m
END IF
END DO





! % This test case shows:
! % How to converts different numbers of main
! % function's parameters
N = floor(((scale ** 3) + 0.0))
tmpvar2 = floor((m + 0.0));
IF ((.NOT. ALLOCATED(set))) THEN 
	ALLOCATE(set(N, tmpvar2))
END IF
set = 0


! % passing n,t to a simple function call
CALL foo(N, N)

tmpvar3 = (N + 1);
IF ((.NOT. ALLOCATED(B))) THEN 
	ALLOCATE(B(N, tmpvar3))
END IF
CALL bar(N, (N + 1), B)
IF (ALLOCATED(B)) THEN 
	DEALLOCATE(B)
END IF
IF (ALLOCATED(set)) THEN 
	DEALLOCATE(set)
END IF
END PROGRAM drv_foo

