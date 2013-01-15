MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
DOUBLE PRECISION, DIMENSION(2) :: newdata
INTEGER :: n
DOUBLE PRECISION :: m
INTEGER :: tmpvar2
DOUBLE PRECISION :: i
DOUBLE PRECISION :: s
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: mag
INTEGER :: III
INTEGER :: tmpvar3
INTEGER :: tmpvar4

III = 0





! % Tranform matrix concatenation into 
! % index increasing form, and 
! % Infer the values of new index
n = floor((2 * 2.0))
m = (n * 2.2);
s = (n * 0.5);

tmpvar2 = 0
tmpvar3 = (floor(((m - 1) / s)) + 1)
IF ((.NOT. ALLOCATED(mag))) THEN 
	ALLOCATE(mag((((((tmpvar3 - 1) - 0) + 1) * 1) + 0), 2))
END IF
mag = 0;
DO tmpvar4 = 0 , ((floor(((m - 1) / s)) + 1) - 1)
	III = III
	i = ((s * tmpvar4) + 1)
	
	
	newdata = [i, i];
	tmpvar2 = (tmpvar2 + 1)
	mag(tmpvar2, :) = newdata;
END DO




IF (ALLOCATED(mag)) THEN 
	DEALLOCATE(mag)
END IF
END PROGRAM drv_foo

