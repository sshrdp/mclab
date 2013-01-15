MODULE Mod_drv_fft
CONTAINS

SUBROUTINE fft_four1(data1, n, isign, result)
DOUBLE PRECISION, DIMENSION(1, (2 * n)) :: data
INTEGER :: n
INTEGER :: isign
DOUBLE PRECISION, DIMENSION(1, (2 * n)) :: result
DOUBLE PRECISION :: theta
DOUBLE PRECISION :: tempi
INTEGER :: nn
INTEGER :: istep
DOUBLE PRECISION :: tempr
DOUBLE PRECISION :: wi
INTEGER :: m1
DOUBLE PRECISION :: wr
INTEGER :: mmax
INTEGER :: i2
INTEGER :: i1
DOUBLE PRECISION :: wtemp
DOUBLE PRECISION :: m
DOUBLE PRECISION :: j
INTEGER :: i
DOUBLE PRECISION :: t
DOUBLE PRECISION :: wpr
DOUBLE PRECISION :: wpi
DOUBLE PRECISION, DIMENSION(1, (2 * n)) :: data1
data = data1


IF (((n < 2) .OR. (IAND(n, (n - 1)) /= 0))) THEN 
	
	
	
	
	
	
	
	
	! % The original algorithim in C can be found in
! % Numerical recipes : the art of scientific computing / William H. Press ... [et al.]
! % Computes the discrete fourier transform for complex data type
! % data: contains the complex data values whose real and
! % imaginary parts are stored in contigous locations in the array data;
! % size of data is 2 * n (the number of complex numbers in the data points).
! % isign: is a flag whose value is either 1 or -1; if isign is 1, forward
! % transform is computed and backward transform otherwise.
! 'n must be power of 2 in four1'
	return
END IF
nn = (2 * n);
j = 2;
DO i2 = 2 , nn , 2
	
	
	IF ((j > i2)) THEN 
	t = data(1, (j - 1));
	data(1, (j - 1)) = data(1, (i2 - 1));
	data(1, (i2 - 1)) = t;
	t = data(1, j);
	data(1, j) = data(1, i2);
	data(1, i2) = t;
END IF
	m = n;
	DO  
	
	
	IF (((m >= 2) .AND. (j > m))) THEN 
	j = (j - m);
	m = ((m + 0.0) / 2);
ELSE 
	EXIT
END IF
END DO
	
	
	j = (j + m);
END DO


i = i2
mmax = 2;
DO  
	
	
	
	
	
	
	
	
	IF ((nn > mmax)) THEN 
	istep = (mmax * 2);
	theta = (isign * (6.28318530717959 / mmax));
	wtemp = sin((0.5 * theta));
	wpr = (((-2.0) * wtemp) * wtemp);
	wpi = sin(theta);
	wr = 1.0;
	wi = 0.0;
	DO m1 = 2 , mmax , 2
	
	
	
	DO i1 = m1 , nn , istep
	j = (i1 + mmax);
	tempr = ((wr * data(1, (j - 1))) - (wi * data(1, j)));
	tempi = ((wr * data(1, j)) + (wi * data(1, (j - 1))));
	data(1, (j - 1)) = (data(1, (i1 - 1)) - tempr);
	data(1, j) = (data(1, i1) - tempi);
	data(1, (i1 - 1)) = (data(1, (i1 - 1)) + tempr);
	data(1, i1) = (data(1, i1) + tempi);
END DO
	i = i1
	wtemp = wr;
	wr = (((wtemp * wpr) - (wi * wpi)) + wr);
	wi = (((wi * wpr) + (wtemp * wpi)) + wi);
END DO
	
	
	
	m = m1
	mmax = istep;
ELSE 
	EXIT
END IF
	
	
	
END DO








result = data;
END SUBROUTINE fft_four1

END MODULE Mod_drv_fft

PROGRAM drv_fft
USE Mod_drv_fft
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(1) :: output
CHARACTER(3) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
CHARACTER(10) :: arg_buffer
DOUBLE PRECISION :: timing_clock
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: data
DOUBLE PRECISION :: tmpvar6
INTEGER, DIMENSION(1, 6) :: tmpvar4
INTEGER :: tmpvar3
INTEGER :: int_tmpvar
INTEGER :: n
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: out
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar34

timing_clock = 0.0
int_tmpvar = 0
arg_buffer = '0000000000'
DO int_tmpvar = 1 , IARGC()
	CALL GETARG(int_tmpvar, arg_buffer) 
	IF ((int_tmpvar == 1)) THEN 
	READ(arg_buffer, *) scale
END IF
END DO






! %Driver for fast fourier transform
! %input is n randomly generated complex numbers stored in an array of size 2*n
! %n must be a power of 2
! % transf = fft_four1(data,n,1) computes forward transform
! % 1/n * fft_four1(transf,n,-1) computes backward tranform
n = ((1024 * 1024) * (2 ** floor(0.5+LOG(scale+0.0)/LOG(2.0)) ));
tmpvar3 = (2 * n);
IF ((.NOT. ALLOCATED(data))) THEN 
	ALLOCATE(data(1, tmpvar3))
END IF
CALL RANDOM_NUMBER(data(:, :));
! 'computing the forward transform'
timing_clock = timing_clock
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;
IF ((.NOT. ALLOCATED(out))) THEN 
	ALLOCATE(out(1, (2 * n)))
END IF
CALL fft_four1(data, n, 1, out)
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;

tmpvar4(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar34 = MATMUL((t2 - t1), TRANSPOSE(tmpvar4))
! % Compute the running time in seconds
time = tmpvar34(1, 1);

tmpvar6 =  (SUM(out)/(0.0+SIZE(out)));
! % Store the benchmark output
output = [tmpvar6];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
IF (ALLOCATED(data)) THEN 
	DEALLOCATE(data)
END IF
IF (ALLOCATED(out)) THEN 
	DEALLOCATE(out)
END IF
END PROGRAM drv_fft

