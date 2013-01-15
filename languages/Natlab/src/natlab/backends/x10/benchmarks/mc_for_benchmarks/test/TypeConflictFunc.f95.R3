MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER :: sum
DOUBLE PRECISION :: a
DOUBLE PRECISION :: n
DOUBLE PRECISION :: j
DOUBLE PRECISION, DIMENSION(3, 3) :: k
INTEGER :: i
DOUBLE PRECISION, DIMENSION(3, 3) :: z
INTEGER :: i3
INTEGER :: y
INTEGER :: i2
INTEGER :: i1
INTEGER, DIMENSION(3, 3) :: x







! % Cases to show type inference functions 
! % appear in different flow control statments
! % --------------------------------
i = 0;
sum = 0;
DO i1 = 1 , 100
	
	sum = (sum + 1);
END DO

i = i1
y = sum;

IF ((i == 1)) THEN 
	y = 1;
ELSE 
	y = i;
END IF

z = y;



! % --------------------------------
i = 0
x = 0
DO i2 = 1 , 5
	
	
	k = x
	IF ((i2 > 2)) THEN 
	x = 0
ELSE 
	x = 1
END IF
	
	IF ((i2 == 1)) THEN 
	y = 1;
ELSE 
	y = i2;
END IF
	
END DO


i = i2
z = x




! % --------------------------------
! % switch-case statement
i = 0;
a = 0;
DO i3 = 1 , 5
	
	
	k = a;
	SELECT CASE (i3)
CASE (1)
	a = (a * 2);
CASE (2)
	a = ((a + 0.0) / 2);
CASE (3)
	a = (a ** 2);
CASE DEFAULT 
	a = (a - 1);
END SELECT
	
END DO


i = i3
z = a;



! % --------------------------------
! %  While statement
j = 1;
k = 0;
DO  
	
	
	IF ((j > 0)) THEN 
	IF ((j > 10)) THEN 
	j = ((j + 0.0) / 2);
ELSE 
	j = (j / 3);
END IF
	
	k = j;
ELSE 
	EXIT
END IF
	
END DO


n = j;

END PROGRAM drv_foo

