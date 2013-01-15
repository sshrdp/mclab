MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER :: n
INTEGER :: i
DOUBLE PRECISION, DIMENSION(:, :, :), ALLOCATABLE :: r
DOUBLE PRECISION, DIMENSION(:, :, :), ALLOCATABLE :: dr
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: r1








! %. When a variable is assigned to a new type [#2],
! %    which cannot be merged with old type [#1].
! %    Then, the variable in the rest of program should be rename. 
! % Rename along the code-node-list  according to flow set, 
! % for all reached and unique reached. 
n = floor(2.0);

IF ((.NOT. ALLOCATED(dr))) THEN 
	ALLOCATE(dr(n, n, 3))
END IF
dr = 0;


DO i = 1 , 10
	
	
	
	IF ((.NOT. ALLOCATED(r))) THEN 
	ALLOCATE(r(n, n, 3))
END IF
	! % Case-1: not first use flow conflict 'nb3d'
! % First def 		---- [#1]
r = (dr * dr);
	
	
	
	IF ((.NOT. ALLOCATED(r1))) THEN 
	ALLOCATE(r1(n, n))
END IF
	! % RHS use
! % LHS: new type cannot merge to old type, new-def ---- [#2]
r1 = ((r(:, :, 1) + r(:, :, 2)) + r(:, :, 3));
	
	
	! % RHS direct-use of r
dr(:, :, 2) = (dr(:, :, 2) / r1);
	
	
	! % LHS this is another new-def of r ---- [#3]
r1(1, 1) = 1.0;
	
	
	
	! % RHS is a use of #3
! % LHS is a new-def 	---- [#4]
r1 = sqrt(r1);
	
	
	! %  RHS is a indirect-use ( #4-use)
dr(:, :, 3) = (dr(:, :, 3) / r1);
END DO


IF (ALLOCATED(r)) THEN 
	DEALLOCATE(r)
END IF
IF (ALLOCATED(r1)) THEN 
	DEALLOCATE(r1)
END IF
IF (ALLOCATED(dr)) THEN 
	DEALLOCATE(dr)
END IF
END PROGRAM drv_foo

