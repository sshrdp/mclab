MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
DOUBLE PRECISION :: f
DOUBLE PRECISION :: dx
COMPLEX :: z1
DOUBLE PRECISION :: dy
INTEGER :: ya
INTEGER :: yb
INTEGER :: n
COMPLEX :: cx
COMPLEX :: x3
DOUBLE PRECISION :: xb
DOUBLE PRECISION :: xa







! % This test case shows
! % transform integer expression to double expression
! % in different situations
! % transform integer arguments to double, 
n = floor(0.5+(.4 * 30)) ;
f = sqrt((n + 0.0))
f = sqrt(100.0)
PRINT *,f


! %  transform integer division to double division 
ya = (-1);
yb = 1;
xa = (-1.5);
xb = .5;
dx = ((xb - xa) / xa);

! % dy should be inferred as REAL, and RHS transform to (((yb - ya) + 0.0) / yb);
dy = (((yb - ya) + 0.0) / yb);
cx = COMPLEX((xa + (xb * dx)), (ya + (yb * dy)))
PRINT *,cx


! % complex operations
z1 = (f + cx)
x3 = (z1 + COMPLEX(0.0, 1.0));

END PROGRAM drv_foo

