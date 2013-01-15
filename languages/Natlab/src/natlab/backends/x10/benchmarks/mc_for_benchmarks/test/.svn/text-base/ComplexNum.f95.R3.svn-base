MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER :: t3
INTEGER :: t2
INTEGER :: t1
COMPLEX :: z0
COMPLEX :: z1
COMPLEX :: x4
COMPLEX :: z2
COMPLEX :: x2
COMPLEX :: x3
COMPLEX :: x1
COMPLEX :: y0
INTEGER :: n
COMPLEX :: y1
COMPLEX :: y2
COMPLEX :: y3
COMPLEX :: y5
INTEGER :: j
COMPLEX :: y6
COMPLEX :: y7
COMPLEX :: y8
INTEGER :: i




! % Two forms of complex number 
x1 = COMPLEX(2, 3);
x2 = COMPLEX(2, 3.1);

x3 = COMPLEX(2, 1.0);
x4 = COMPLEX(2, 3);

n = 10;
z0 = COMPLEX(0.0, 1.0);
z1 = COMPLEX(0.0, n);
z2 = COMPLEX(4, n);


y0 = 10;

! % The first two variables are complex
y1 = COMPLEX(2, 1.0);
y2 = COMPLEX(1, 3);


! % Addition of two complex numbers 
y3 = (y1 + COMPLEX(0.0, 1.0));


DO i = 1 , 3
	! % t1, t2 are integers 
t1 = (2 * i);
	j = i;
	t2 = (4 + (3 * j));
END DO


! % Type inference on the complex number 
n = 10
y0 = (n + y1);
y5 = (y2 + t2);
y6 = y2;
y7 = (y1 + (3 * i));
y8 = (y2 + j);


! % this is an integer
t3 = (n + (2 * j));
END PROGRAM drv_foo

