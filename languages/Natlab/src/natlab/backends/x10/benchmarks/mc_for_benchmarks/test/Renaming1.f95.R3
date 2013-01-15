
PROGRAM drv_foo
IMPLICIT NONE
INTEGER :: j4
INTEGER :: t2
INTEGER :: t1
INTEGER :: j2
INTEGER :: j3
INTEGER, DIMENSION(10) :: A
REAL :: sum
INTEGER :: j1
INTEGER :: cnt
INTEGER :: n
INTEGER :: j
INTEGER :: i
REAL :: t
INTEGER :: i5
INTEGER :: i4
INTEGER :: z
INTEGER :: i3
INTEGER :: i2
INTEGER :: i1


n = 10



DO t1 = 1 , n
	! % this case, loop variable 't' isn't as same as outside definition.
! % so rename the loop variable 't'
A(t1) = t1;
END DO
t = t1
t = (2.5 + t)


DO t2 = 1 , n
	
	A(t2) = t2;
END DO

t = t2
sum = 0;
DO i3 = 1 , 10
	
	
	sum = (sum + t);
	t = (2.5 + t)
END DO


i = i3
IF ((t > 4)) THEN 
	t = (2.5 + t)
END IF



! % Case #3
! % Rename the assignment on 'i' inside the loop
sum = 0;
DO i1 = 1 , 10
	
	i2 = i1
	i2 = (i2 * 2);
	sum = (sum + i2);
END DO

i = i1


DO j4 = 1 , n
	
	j1 = j4
	! % Rename the assignment on 'j' inside the loop
j1 = (j1 * 2);
	A(j1) = j1;
END DO

j = j4



DO j2 = 1 , n
	
	j3 = j2
	! % Type-#2 Rename the loop variable 'j', since this is 2nd time use
! % Rename the assignment on 'j' inside the loop
A(j3) = j3;
	j3 = (j3 * 2);
END DO

j = j2


! % This is very ambigious
sum = 0;
cnt = 0;
z = 0;
DO i4 = 1 , 10
	
	
	
	
	i5 = i4
	cnt = (cnt + i5);
	IF ((i5 < 5)) THEN 
	z = (z + i5);
	i5 = (i5 * 2);
END IF
	sum = (sum + i5);
END DO




i = i4
END PROGRAM drv_foo

