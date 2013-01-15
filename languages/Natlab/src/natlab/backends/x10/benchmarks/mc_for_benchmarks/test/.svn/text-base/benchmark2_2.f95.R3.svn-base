
PROGRAM benchmark2_2
IMPLICIT NONE
DOUBLE PRECISION :: cumulate
CHARACTER(50) :: tmpvar9
DOUBLE PRECISION, DIMENSION(800, 800) :: tmpvar7
DOUBLE PRECISION, DIMENSION(800, 800) :: b2
CHARACTER(50) :: tmpvar5
DOUBLE PRECISION, DIMENSION(1500, 1500) :: tmpvar3
CHARACTER(50) :: tmpvar1
DOUBLE PRECISION :: output_real_tmp
INTEGER, DIMENSION(5, 3) :: times
DOUBLE PRECISION, DIMENSION(3000, 750) :: b1
DOUBLE PRECISION, DIMENSION(750, 3000) :: a1
DOUBLE PRECISION, DIMENSION(800, 800) :: a2
DOUBLE PRECISION :: timing
DOUBLE PRECISION, DIMENSION(1500, 1500) :: b
DOUBLE PRECISION, DIMENSION(1500, 1500) :: a
CHARACTER(21) :: label
INTEGER :: i
DOUBLE PRECISION :: timing_tic
DOUBLE PRECISION :: timing_toc
INTEGER :: runs
INTEGER :: i2
INTEGER :: i1

timing_tic = 0.0
timing_toc = 0.0
output_real_tmp = 0.0











! % a = zeros(1500,1500);
! % b = zeros(1500,1500);
! % This is the PrettyPrint/StructureString() on the modified AST
! % 1. The Name is solved to be either a variable without parentheses. 
! %    or a function call/array access with parentheses.
! %    i.g. clc(), tic(), toc()
! % 2. The SPACE in the matrix brackets [] has been replaced by COMMA.
! % 3. % Command Syntax doesn't support right now : % clear a; clear b;  
! %! INTEGER :: runs
! % \languages\Fortran\benchmark2_2.n
 ! clc  !! Not support.()

! % Number of times the tests are executed
runs = 3;
times = 0;

label = '   Matlab Benchmark 2'
PRINT *,label
PRINT *,'   Matlab Benchmark 2'
PRINT *,'   =================='
output_real_tmp = output_real_tmp
output_real_tmp = runs
WRITE(tmpvar1, '(F12.6)' )  output_real_tmp;
! % SPACE->COMMA
PRINT *,'Number of times each test is run__________________________: '//tmpvar&
&1
PRINT *,' '


PRINT *,'   I. Matrix calculation'
PRINT *,'   ---------------------'



! % (1)
cumulate = 0;
a = 0;
b = 0;

DO i2 = 1 , runs
	
	
	
	timing_tic = timing_tic
	CALL CPU_TIME(timing_tic)  ! tic();
	CALL RANDOM_NUMBER(tmpvar3(:, :));
	a = abs((tmpvar3 / 10));
	b = TRANSPOSE(a);
	a1 = RESHAPE(b, (/750, 3000/));
	b1 = TRANSPOSE(a1);
	timing_toc = timing_toc
	CALL CPU_TIME(timing_toc); timing = timing_toc - timing_tic;
	cumulate = (cumulate + timing);
END DO



i = i2

timing = (cumulate / runs);

output_real_tmp = timing
WRITE(tmpvar5, '(F12.6)' )  output_real_tmp;
! % times(1, 1) = timing;
! % SPACE->COMMA
PRINT *,'Creation, transp., deformation of a 1500x1500 matrix (sec): '//tmpvar&
&5



! % clear a; clear b;  % Command Syntax doesn't support right now
! % (2)
cumulate = 0;
b2 = 0;
DO i1 = 1 , runs
	
	
	
	CALL RANDOM_NUMBER(tmpvar7(:, :));
	a2 = abs((tmpvar7 / 2));
	CALL CPU_TIME(timing_tic)  ! tic();
	b2 = (a2 ** 1000);
	CALL CPU_TIME(timing_toc); timing = timing_toc - timing_tic;
	cumulate = (cumulate + timing);
END DO



i = i1
timing = (cumulate / runs);


output_real_tmp = timing
WRITE(tmpvar9, '(F12.6)' )  output_real_tmp;
! % times(2, 1) = timing;
! % clear a; clear b;
PRINT *,'800x800 normal distributed random matrix ^1000______ (sec): '//tmpvar&
&9
END PROGRAM benchmark2_2

