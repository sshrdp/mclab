MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
DOUBLE PRECISION, DIMENSION(2) :: valid
DOUBLE PRECISION :: t2
DOUBLE PRECISION :: t1
DOUBLE PRECISION, DIMENSION(2) :: output




! %	A = ones(2,2);
! %	output = {mean(A(:))};
t1 = 10.2;
t2 = 12.1;
valid = [t1, t2]
output = [t1, t2]
END PROGRAM drv_foo

