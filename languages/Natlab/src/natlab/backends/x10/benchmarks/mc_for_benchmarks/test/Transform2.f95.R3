MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER :: cap
DOUBLE PRECISION :: tmpvar2
DOUBLE PRECISION :: tmpvar1
DOUBLE PRECISION, DIMENSION(2) :: output






! % This test case shows
! % transform integer expression to double expression
! % in different situations
cap = 10

tmpvar1 = cap;
tmpvar2 = cap;
! % They are legal in MATLAB
output = [tmpvar1, tmpvar2]
PRINT *,output
END PROGRAM drv_foo

