MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER :: result
INTEGER, DIMENSION(:, :), ALLOCATABLE :: tmpvar2
INTEGER :: j1
INTEGER :: tmpvar1
INTEGER, DIMENSION(:, :), ALLOCATABLE :: U
INTEGER, DIMENSION(:), ALLOCATABLE :: Y
INTEGER, DIMENSION(:, :), ALLOCATABLE :: X
INTEGER :: n
INTEGER :: m
INTEGER, DIMENSION(:), ALLOCATABLE :: v
INTEGER, DIMENSION(:), ALLOCATABLE :: t
INTEGER, DIMENSION(:), ALLOCATABLE :: s
INTEGER, DIMENSION(:), ALLOCATABLE :: r
INTEGER :: q
INTEGER :: p
INTEGER :: z





! % This test case shows
! % Colon-expression in index
n = floor(6.0);
m = floor(5.0);





tmpvar1 = (m + 1);
IF ((.NOT. ALLOCATED(U))) THEN 
	ALLOCATE(U(n, tmpvar1))
END IF
! %	result = foo(n,m);
! %	disp(result)
! %end
! %function result = foo(n,m)	
U = 0;
IF ((.NOT. ALLOCATED(X))) THEN 
	ALLOCATE(X(1, n))
END IF
X = 1;
DO j1 = 2 , m
	
	IF ((.NOT. ALLOCATED(tmpvar2))) THEN 
	ALLOCATE(tmpvar2(n, 1))
END IF
	tmpvar2 = TRANSPOSE(X);
	U(1 : n, j1) = tmpvar2(:, 1);
	IF ((.NOT. ALLOCATED(Y))) THEN 
	ALLOCATE(Y(n))
END IF
	Y = U(1 : n, j1);
END DO

z = U(j1, j1)
p = U(1, 2)
q = U(1, j1)
IF ((.NOT. ALLOCATED(r))) THEN 
	ALLOCATE(r((m + 1)))
END IF
r = U(1, :)
IF ((.NOT. ALLOCATED(s))) THEN 
	ALLOCATE(s(n))
END IF
s = U(:, 2)
IF ((.NOT. ALLOCATED(t))) THEN 
	ALLOCATE(t(n))
END IF
t = U(1 : n, 2)
IF ((.NOT. ALLOCATED(v))) THEN 
	ALLOCATE(v((m - 1)))
END IF
v = U(1, 2 : m)

result = (m + n)
PRINT *,Y
PRINT *,z
PRINT *,p
PRINT *,q
PRINT *,r
PRINT *,s
PRINT *,t
PRINT *,v
IF (ALLOCATED(tmpvar2)) THEN 
	DEALLOCATE(tmpvar2)
END IF
IF (ALLOCATED(U)) THEN 
	DEALLOCATE(U)
END IF
IF (ALLOCATED(Y)) THEN 
	DEALLOCATE(Y)
END IF
IF (ALLOCATED(X)) THEN 
	DEALLOCATE(X)
END IF
IF (ALLOCATED(v)) THEN 
	DEALLOCATE(v)
END IF
IF (ALLOCATED(t)) THEN 
	DEALLOCATE(t)
END IF
IF (ALLOCATED(s)) THEN 
	DEALLOCATE(s)
END IF
IF (ALLOCATED(r)) THEN 
	DEALLOCATE(r)
END IF
END PROGRAM drv_foo

