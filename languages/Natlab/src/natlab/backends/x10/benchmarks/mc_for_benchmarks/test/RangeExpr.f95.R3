MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
DOUBLE PRECISION, DIMENSION(6) :: A2
INTEGER, DIMENSION(6) :: A1
DOUBLE PRECISION, DIMENSION(3) :: A4
INTEGER, DIMENSION(6) :: A3
INTEGER, DIMENSION(3) :: A6
INTEGER, DIMENSION(:), ALLOCATABLE :: YY
INTEGER, DIMENSION(7) :: A5
INTEGER :: Up
INTEGER, DIMENSION(:, :), ALLOCATABLE :: tmpvar6
DOUBLE PRECISION, DIMENSION(3) :: mr2
DOUBLE PRECISION :: mr1
INTEGER, DIMENSION(:, :), ALLOCATABLE :: tmpvar5
INTEGER, DIMENSION(:, :), ALLOCATABLE :: QQ
INTEGER, DIMENSION(:), ALLOCATABLE :: XX
INTEGER, DIMENSION(4, 3) :: m1
INTEGER, DIMENSION(4, 3) :: m2
DOUBLE PRECISION :: dT
INTEGER, DIMENSION(:, :), ALLOCATABLE :: AA
INTEGER, DIMENSION(4) :: r1
INTEGER :: III
INTEGER :: Low
DOUBLE PRECISION :: T
INTEGER, DIMENSION(16) :: BB
DOUBLE PRECISION :: Upf
INTEGER, DIMENSION(:), ALLOCATABLE :: ZZ
INTEGER, DIMENSION(4, 4, 3) :: s1
INTEGER, DIMENSION(:, :), ALLOCATABLE :: ii
INTEGER :: n
DOUBLE PRECISION :: ms1
INTEGER :: Inc
INTEGER :: m
DOUBLE PRECISION, DIMENSION(4, 3) :: ms2
DOUBLE PRECISION :: i
INTEGER, DIMENSION(:), ALLOCATABLE :: z
INTEGER, DIMENSION(:), ALLOCATABLE :: y
DOUBLE PRECISION, DIMENSION(:), ALLOCATABLE :: x
INTEGER :: tmpvar13
INTEGER :: tmpvar14
INTEGER :: tmpvar16

III = 0

m1(1, :) = [9, 8, 7]
m1(2, :) = [6, 5, 4]
m1(3, :) = [11, 12, 13]
m1(4, :) = [15, 16, 17]
mr1 =  (SUM(m1)/(0.0+SIZE(m1)));
mr2 =  (SUM(m1,1)/(0.0+SIZE(m1,1)));
PRINT *,mr1;
PRINT *,mr2;

m2(1, :) = [9, 8, 7]
m2(2, :) = [6, 5, 4]
m2(3, :) = [11, 12, 13]
m2(4, :) = [15, 16, 17]

s1 = 0;
ms1 =  (SUM(s1)/(0.0+SIZE(s1)))
ms2 =  (SUM(s1,1)/(0.0+SIZE(s1,1)))
r1 = s1(:, 1, 1)

III = III
A1 = (/(III, III=10 , 15)/)
A2 = ((/(III, III=0 , 5)/) + (-2.5))
A3 = (/(III, III=1 , floor(6.3))/)
A4 = (((/(III, III=0 , 2)/) * 2.5) + 10)
A5 = (/(III, III=(-1) , 5)/)
A6 = (/(III, III=10 , 15 , 2)/)
PRINT *,A1;
PRINT *,A2;
PRINT *,A3;
PRINT *,A4;
PRINT *,A5;
PRINT *,A6;

Up = (9 ** 1)
Low = (2 ** 1)
Inc = (2 ** 1)
IF ((.NOT. ALLOCATED(y))) THEN 
	ALLOCATE(y((((Up - Low) / Inc) + 1)))
END IF
y = (/(III, III=Low , Up , Inc)/)

Upf = (7.2 ** 2)
tmpvar13 = (floor((((floor(Upf) - Low) / Inc) + 0.0)) + 1)
IF ((.NOT. ALLOCATED(z))) THEN 
	ALLOCATE(z(tmpvar13))
END IF
z = ((Inc * (/(III, III=0 , ((floor((((floor(Upf) - Low) / Inc) + 0.0)) + 1) - 1))/)) + Lo&
&w)

T = (2.3 ** 1)
dT = (0.5 ** 1)
tmpvar14 = (floor(((T - 1) / dT)) + 1)
IF ((.NOT. ALLOCATED(x))) THEN 
	ALLOCATE(x(tmpvar14))
END IF
x = ((dT * (/(III, III=0 , ((floor(((T - 1) / dT)) + 1) - 1))/)) + 1)
PRINT *,x;
PRINT *,y;
PRINT *,z;


DO tmpvar16 = 0 , ((floor(((T - 1) / dT)) + 1) - 1)
	i = ((dT * tmpvar16) + 1)
	! % Loop with floating-point increasing step
PRINT *,i;
END DO


! % Nonconsecutive Elements
n = floor(2.0);
m = floor(3.0);
IF ((.NOT. ALLOCATED(AA))) THEN 
	ALLOCATE(AA(m, n))
END IF
AA = 0;
IF ((.NOT. ALLOCATED(XX))) THEN 
	ALLOCATE(XX((m - 1)))
END IF
XX = AA(2 : m, 1)
IF ((.NOT. ALLOCATED(YY))) THEN 
	ALLOCATE(YY(m))
END IF
YY = AA(:, 1)
IF ((.NOT. ALLOCATED(ZZ))) THEN 
	ALLOCATE(ZZ(n))
END IF
ZZ = AA(1, :)
IF ((.NOT. ALLOCATED(QQ))) THEN 
	ALLOCATE(QQ(m, n))
END IF
QQ = AA

IF ((.NOT. ALLOCATED(tmpvar5))) THEN 
	ALLOCATE(tmpvar5(n, 1))
END IF
tmpvar5 = 1;
IF ((.NOT. ALLOCATED(tmpvar6))) THEN 
	ALLOCATE(tmpvar6(1, n))
END IF
tmpvar6(1, :) = (/(III, III=1 , n)/);
IF ((.NOT. ALLOCATED(ii))) THEN 
	ALLOCATE(ii(n, n))
END IF
ii = MATMUL(tmpvar5, tmpvar6);

ii(1 : n, 1) = (/(III, III=1 , n)/);
PRINT *,size(XX);
PRINT *,size(YY);
PRINT *,size(ZZ);
PRINT *,size(QQ);
PRINT *,size(ii);

BB = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1];
BB(1 : 16 : 3) = (-9)
PRINT *,BB
IF (ALLOCATED(YY)) THEN 
	DEALLOCATE(YY)
END IF
IF (ALLOCATED(tmpvar6)) THEN 
	DEALLOCATE(tmpvar6)
END IF
IF (ALLOCATED(tmpvar5)) THEN 
	DEALLOCATE(tmpvar5)
END IF
IF (ALLOCATED(QQ)) THEN 
	DEALLOCATE(QQ)
END IF
IF (ALLOCATED(XX)) THEN 
	DEALLOCATE(XX)
END IF
IF (ALLOCATED(AA)) THEN 
	DEALLOCATE(AA)
END IF
IF (ALLOCATED(ZZ)) THEN 
	DEALLOCATE(ZZ)
END IF
IF (ALLOCATED(ii)) THEN 
	DEALLOCATE(ii)
END IF
IF (ALLOCATED(z)) THEN 
	DEALLOCATE(z)
END IF
IF (ALLOCATED(y)) THEN 
	DEALLOCATE(y)
END IF
IF (ALLOCATED(x)) THEN 
	DEALLOCATE(x)
END IF
END PROGRAM drv_foo

