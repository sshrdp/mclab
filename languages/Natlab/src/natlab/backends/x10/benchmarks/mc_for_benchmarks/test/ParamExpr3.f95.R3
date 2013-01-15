MODULE Mod_drv_foo
CONTAINS
END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
DOUBLE PRECISION :: timing_clock
INTEGER, DIMENSION(1, 6) :: tmpvar5
INTEGER, DIMENSION(1, 6) :: tmpvar2
DOUBLE PRECISION :: time
INTEGER :: time1
DOUBLE PRECISION :: timing_tic
DOUBLE PRECISION :: timing_toc
DOUBLE PRECISION :: timing
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar7

timing_tic = 0.0
timing_toc = 0.0
timing_clock = 0.0





timing_tic = timing_tic
! % This test case shows the two popular way 
! % in MATLAB to measure the eclipsed time 
! % function clock, tic, toc
CALL CPU_TIME(timing_tic)  ! tic();
timing_toc = timing_toc
CALL CPU_TIME(timing_toc); timing = timing_toc - timing_tic;
PRINT *,timing;

timing_clock = timing_clock
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;
tmpvar2(1, :) = [0, 0, 86400, 3600, 60, 1];
PRINT *, 'Timing = %f\n', MATMUL((t2 - t1), TRANSPOSE(tmpvar2));

DO time1 = 1 , 10
END DO
time = time1
tmpvar5(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar7 = MATMUL((t2 - t1), TRANSPOSE(tmpvar5))
time = tmpvar7(1, 1);
PRINT *,time;
END PROGRAM drv_foo

