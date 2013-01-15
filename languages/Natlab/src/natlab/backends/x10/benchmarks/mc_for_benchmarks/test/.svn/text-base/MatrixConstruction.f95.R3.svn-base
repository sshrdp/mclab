MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
INTEGER, DIMENSION(3, 1) :: v1
INTEGER, DIMENSION(3, 1) :: v2
INTEGER, DIMENSION(3, 3) :: m121
INTEGER, DIMENSION(2, 3) :: m34
INTEGER, DIMENSION(2, 3) :: m12
INTEGER, DIMENSION(3, 3) :: m13
INTEGER, DIMENSION(3) :: m10
INTEGER, DIMENSION(4, 3) :: m5
INTEGER, DIMENSION(2, 6) :: m6
INTEGER, DIMENSION(2, 9) :: m7
INTEGER, DIMENSION(3, 2) :: m12row
INTEGER, DIMENSION(2, 3) :: m4
INTEGER, DIMENSION(3, 3) :: m112
INTEGER, DIMENSION(6, 1) :: m12col
INTEGER, DIMENSION(3) :: r1
INTEGER, DIMENSION(3) :: r2
INTEGER, DIMENSION(3) :: r3






! % This test case shows
! % basic matrix construction and matrix concatenations
! % Constructing single row
r1 = [9, 8, 7];
r2 = [6, 5, 4];
r3 = [3, 2, 1];


! % Constructing matrix by row(s)
m10 = r1;
m12(1, :) = r1
m12(2, :) = r2
m13(1, :) = r1
m13(2, :) = r2
m13(3, :) = r3


m34(1, :) = [11, 12, 13]
m34(2, :) = [15, 16, 17]


! % Concatenating matrices, row-matrices, column-matrix
m4 = m34

m5(1 : 2, :) = m12
m5(3 : 4, :) = m34

m6(1, :) = [m12(1, :), m34(1, :)]
m6(2, :) = [m12(2, :), m34(2, :)]
m7(1, :) = [m6(1, :), m34(1, :)]
m7(2, :) = [m6(2, :), m34(2, :)]

m121(1 : 2, :) = m12
m121(3, :) = r1
m112(1, :) = r1
m112(2 : 3, :) = m12


v1(1, :) = [9]
v1(2, :) = [8]
v1(3, :) = [7]
v2(1, :) = [6]
v2(2, :) = [5]
v2(3, :) = [4]

m12row(1, :) = [v1(1, :), v2(1, :)]
m12row(2, :) = [v1(2, :), v2(2, :)]
m12row(3, :) = [v1(3, :), v2(3, :)]
m12col(1 : 3, :) = v1
m12col(4 : 6, :) = v2

PRINT *,m10;
PRINT *,m13;
PRINT *,m5;
PRINT *,m4;
PRINT *,m7;
PRINT *,m121;
PRINT *,m112;
PRINT *,m12row;
PRINT *,m12col;

END PROGRAM drv_foo

