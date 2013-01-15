MODULE Mod_drv_foo
CONTAINS

SUBROUTINE foo(n, m, seed1, M1)
INTEGER :: n
INTEGER :: m
DOUBLE PRECISION :: seed
DOUBLE PRECISION, DIMENSION(m, n) :: M1
INTEGER :: j
INTEGER :: i
INTEGER :: seed1
seed = seed1










! % Because there may have many defintion on same variable, i.e.'M' 									
! % Everytime after renaming, it needs to exit that function, 
! % and rebuild code-nodes and  def/use boxes 
! % then redo building symbol table.
seed = (seed + (m * n));
M1 = 0;
DO i = 1 , m
	
	
	DO j = 1 , n
	M1(i, j) = mod((seed + 0.0), 1.0);
	seed = ((seed + (M1(i, j) * sqrt(100.0))) + sqrt(2.0));
END DO
END DO


END SUBROUTINE foo


SUBROUTINE seidel(f1, n, m, f)
INTEGER, DIMENSION(n, m) :: f1
INTEGER :: n
INTEGER :: m
INTEGER, DIMENSION(n, m) :: f
f = f1







! % From 'capr',
! % assume the return 'f' is same as input 'f'
f = 2
END SUBROUTINE seidel

END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER :: scale
INTEGER, DIMENSION(:, :), ALLOCATABLE :: F
CHARACTER(10) :: arg_buffer
INTEGER :: int_tmpvar
INTEGER, DIMENSION(:, :), ALLOCATABLE :: set
INTEGER :: n
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: M
INTEGER :: N1

int_tmpvar = 0
arg_buffer = '0000000000'
DO int_tmpvar = 1 , IARGC()
	CALL GETARG(int_tmpvar, arg_buffer) 
	IF ((int_tmpvar == 1)) THEN 
	READ(arg_buffer, *) scale
END IF
END DO




! % This test case shows:
! % Solving conflict of variable's names in case-insensitive Fortran
! % function's parameters
n = floor((scale + 0.0))
N1 = floor(((scale ** 3) + 0.0))
IF ((.NOT. ALLOCATED(set))) THEN 
	ALLOCATE(set(N1, n))
END IF
set = 0


IF ((.NOT. ALLOCATED(M))) THEN 
	ALLOCATE(M(n, N1))
END IF
CALL foo(N1, n, scale, M)
IF ((.NOT. ALLOCATED(F))) THEN 
	ALLOCATE(F(N1, n))
END IF
CALL seidel(set, N1, n, F)
IF (ALLOCATED(F)) THEN 
	DEALLOCATE(F)
END IF
IF (ALLOCATED(set)) THEN 
	DEALLOCATE(set)
END IF
IF (ALLOCATED(M)) THEN 
	DEALLOCATE(M)
END IF
END PROGRAM drv_foo

