MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: A
INTEGER, DIMENSION(:, :, :), ALLOCATABLE :: tmpvar2
INTEGER, DIMENSION(:, :, :), ALLOCATABLE :: tmpvar3
INTEGER, DIMENSION(:, :), ALLOCATABLE :: jj
INTEGER :: JJJ
INTEGER, DIMENSION(:, :), ALLOCATABLE :: R
INTEGER, DIMENSION(:, :, :), ALLOCATABLE :: dr
INTEGER :: int_tmpvar
INTEGER :: n
INTEGER, DIMENSION(:, :), ALLOCATABLE :: ii
INTEGER, DIMENSION(:), ALLOCATABLE :: kk
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: rr
DOUBLE PRECISION :: z
INTEGER :: III

III = 0
JJJ = 0
int_tmpvar = 0




! % This test case shows:
! % two cases of Array-indexing
n = floor(0.5+(4 * 1.0)) ;


IF ((.NOT. ALLOCATED(R))) THEN 
	ALLOCATE(R(n, 3))
END IF
! %	A=foo(n)
! %function A=foo(n)
R = 0;
IF ((.NOT. ALLOCATED(ii))) THEN 
	ALLOCATE(ii(n, n))
END IF
ii = 1;
IF ((.NOT. ALLOCATED(jj))) THEN 
	ALLOCATE(jj(n, n))
END IF
jj = 1;
IF ((.NOT. ALLOCATED(dr))) THEN 
	ALLOCATE(dr(n, n, 3))
END IF
dr = 0;


DO III = 1 , n
	DO JJJ = 1 , n
	IF ((.NOT. ALLOCATED(tmpvar2))) THEN 
	ALLOCATE(tmpvar2(n, 3))
END IF
	tmpvar2(III, JJJ, :) = R(jj(III, JJJ), :);
END DO
END DO
DO III = 1 , n
	DO JJJ = 1 , n
	IF ((.NOT. ALLOCATED(tmpvar3))) THEN 
	ALLOCATE(tmpvar3(n, 3))
END IF
	tmpvar3(III, JJJ, :) = R(ii(III, JJJ), :);
END DO
END DO
! % (1) array indexing, on RHS
dr = (tmpvar2 - tmpvar3);

III = III
IF ((.NOT. ALLOCATED(kk))) THEN 
	ALLOCATE(kk(n))
END IF
kk = (/(III, III=1 , n)/);
IF ((.NOT. ALLOCATED(rr))) THEN 
	ALLOCATE(rr(n, n))
END IF
rr = 0;


DO int_tmpvar = 1 , n
	III = (floor((((kk(int_tmpvar) - 1) / n) + 0.0)) + 1)
	JJJ = (mod((kk(int_tmpvar) - 1), n) + 1)
	! % (2) array indexing, on LHS
rr(III, JJJ) = 1.0;
END DO

PRINT *,size(dr)

IF ((.NOT. ALLOCATED(A))) THEN 
	ALLOCATE(A(n, n))
END IF
A = rr

! %end
PRINT *, (SUM(A)/(0.0+SIZE(A)))
PRINT *, (SUM(A,1)/(0.0+SIZE(A,1)))


! % it's necessary to convert sum() into double 
PRINT *, (0.0+SUM(A))
PRINT *, (0.0+SUM(A,1))
z = ( (0.0+SUM(A)) / size(A))
PRINT *,z
IF (ALLOCATED(A)) THEN 
	DEALLOCATE(A)
END IF
IF (ALLOCATED(tmpvar2)) THEN 
	DEALLOCATE(tmpvar2)
END IF
IF (ALLOCATED(tmpvar3)) THEN 
	DEALLOCATE(tmpvar3)
END IF
IF (ALLOCATED(jj)) THEN 
	DEALLOCATE(jj)
END IF
IF (ALLOCATED(R)) THEN 
	DEALLOCATE(R)
END IF
IF (ALLOCATED(dr)) THEN 
	DEALLOCATE(dr)
END IF
IF (ALLOCATED(ii)) THEN 
	DEALLOCATE(ii)
END IF
IF (ALLOCATED(kk)) THEN 
	DEALLOCATE(kk)
END IF
IF (ALLOCATED(rr)) THEN 
	DEALLOCATE(rr)
END IF
END PROGRAM drv_foo

