MODULE Mod_drv_foo
CONTAINS

SUBROUTINE bar2()
INTEGER, DIMENSION(10, 5) :: B





B = 0;
END SUBROUTINE bar2


SUBROUTINE zoo(C)
INTEGER, DIMENSION(10, 5) :: C





C = 0;
END SUBROUTINE zoo

END MODULE Mod_drv_foo

PROGRAM drv_foo
USE Mod_drv_foo
IMPLICIT NONE
DOUBLE PRECISION, DIMENSION(1, 6) :: t2
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
DOUBLE PRECISION :: timing_clock
INTEGER, DIMENSION(1, 6) :: tmpvar2
DOUBLE PRECISION :: timing_tic
DOUBLE PRECISION :: timing_toc
INTEGER, DIMENSION(10, 5) :: C2
DOUBLE PRECISION :: timing

timing_tic = 0.0
timing_toc = 0.0
timing_clock = 0.0






timing_tic = timing_tic
! % This test case shows 
! % the Command Form function calls 
! % appeared in RHS, and stand alone statement
! % intrinsic functions: tic, toc, clock
CALL CPU_TIME(timing_tic)  ! tic();
timing_toc = timing_toc
CALL CPU_TIME(timing_toc); timing = timing_toc - timing_tic;
PRINT *,timing;

timing_clock = timing_clock
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;
CALL CPU_TIME(timing_clock); t2 = 0.0;t2(1,6) = timing_clock;
tmpvar2(1, :) = [0, 0, 86400, 3600, 60, 1];
PRINT *, 'Timing = %f\n', MATMUL((t2 - t1), TRANSPOSE(tmpvar2));


! % user-defined functions 
CALL bar2()
CALL zoo(C2)
END PROGRAM drv_foo

