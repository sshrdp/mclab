MODULE Mod_drv_diff
CONTAINS

SUBROUTINE diffraction(CELLS, SLITSIZE1, SLITSIZE2, T1, T2, mag)
INTEGER :: CELLS
DOUBLE PRECISION :: SLITSIZE1
DOUBLE PRECISION :: SLITSIZE2
INTEGER :: T1
INTEGER :: T2
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: mag
DOUBLE PRECISION :: intensity
INTEGER :: tmpvar23
DOUBLE PRECISION :: tmpvar22
DOUBLE PRECISION :: CELLSIZE
DOUBLE PRECISION, DIMENSION(2) :: newdata
INTEGER :: DISTANCE
DOUBLE PRECISION :: SCREENLENGTH
DOUBLE PRECISION :: horizpos
DOUBLE PRECISION :: tmpvar19
DOUBLE PRECISION :: pi
INTEGER :: III
DOUBLE PRECISION :: sourcept
DOUBLE PRECISION :: SLITRES
COMPLEX :: wavesum
INTEGER :: cellnum
DOUBLE PRECISION :: K
DOUBLE PRECISION :: SCREENRES
DOUBLE PRECISION :: screenpt
DOUBLE PRECISION :: sourcept1
DOUBLE PRECISION :: sourcept2
DOUBLE PRECISION :: WAVELENGTH
DOUBLE PRECISION :: x
INTEGER :: tmpvar24
INTEGER :: tmpvar25
INTEGER :: tmpvar27
INTEGER :: tmpvar29

pi = 3.14159265359
III = 0



















































! %-----------------------------------------------------------------------
! %
! %	This function M-file calculates the diffraction pattern of
! %	monochromatic light through a transmission grating for
! %	arbitrary slit sizes and slit transmission coefficients.
! %
! %	This MATLAB program is intended as a pedagogical example
! %	of how diffraction and interference arise strictly from
! %	the wave nature of light.
! %
! %	Invocation:
! %		>> mag = diffraction(CELLS, SSIZE1, SSIZE2, T1, T2)
! %
! %		where
! %
! %		i. CELLS is the number of pairs of slits,
! %
! %		i. SSIZE1 is the width of slit 1 in meters,
! %
! %		i. SSIZE2 is the width of slit 2 in meters,
! %
! %		i. T1 is the transmission coefficient of slit 1,
! %
! %		i. T2 is the transmission coefficient of slit 2.
! %
! %		o. mag is the result.
! %
! %	Requirements:
! %		0 < T1, T2 < 1.
! %
! %	Examples:
! %		% One-slit diffraction.
! %		>> mag = diffraction(1, 1e-5, 1e-5, 1, 0)
! %
! %		% Young's two-slit experiment.
! %		>> mag = diffraction(2, 1e-5, 1e-5, 1, 0)
! %
! %	Source:
! %		MATLAB 5 user contributed M-Files at
! %		http://www.mathworks.com/support/ftp/.
! %		("Functions Related to Physics" category.)
! %
! %	Author:
! %		Ian Appelbaum (appeli@mit.edu).
! %
! %	Date:
! %		December 1999.
! %
! %-----------------------------------------------------------------------
! % Distance from slit to screen in meters.
DISTANCE = 5;

! % Wavelength of light in meters
WAVELENGTH = 633e-9;


! % (633 nm is HeNe laser line).
! % Wavenumber.
K = ((2 * pi) / WAVELENGTH);
CELLSIZE = (SLITSIZE1 + SLITSIZE2);



! % The following constants are calculated from the inputs assuming that
! % SLITX >> WAVELENGTH.
! % Resolution of position of sources at slit.
SLITRES = (WAVELENGTH / 100);

! % Resolution of pattern at screen.
SCREENRES = ((((DISTANCE + 0.0) / (CELLS * 10)) * WAVELENGTH) /  (SUM([SLITSIZE1, SLITSIZE&
&2])/SIZE([SLITSIZE1, SLITSIZE2])));

! % Distance from center point to end of screen in meters.
SCREENLENGTH = (((3 * DISTANCE) * WAVELENGTH) /  (SUM([SLITSIZE1, SLITSIZE2])/SIZE([SLITSI&
&ZE1, SLITSIZE2])));
tmpvar23 = 0
tmpvar24 = (floor(((SCREENLENGTH - 0) / SCREENRES)) + 1)
IF ((.NOT. ALLOCATED(mag))) THEN 
	ALLOCATE(mag((((((tmpvar24 - 1) - 0) + 1) * 1) + 0), 2))
END IF
mag = 0;
DO tmpvar25 = 0 , ((floor(((SCREENLENGTH - 0) / SCREENRES)) + 1) - 1)
	III = III
	screenpt = ((SCREENRES * tmpvar25) + 0)
	
	
	
	
	
	wavesum = 0;
	DO cellnum = 0 , (CELLS - 1)
	
	DO tmpvar27 = 0 , ((floor(((SLITSIZE1 - 0) / SLITRES)) + 1) - 1)
	sourcept2 = ((SLITRES * tmpvar27) + 0)
	
	! % First slit.
horizpos = (screenpt - ((cellnum * CELLSIZE) + sourcept2));
	x = abs((horizpos + COMPLEX(0.0, DISTANCE)));
	
	! % Add up the wave contribution from the first slit.
wavesum = (wavesum + (T1 * exp((COMPLEX(0.0, K) * x))));
END DO
	sourcept = sourcept2
	DO tmpvar29 = 0 , ((floor(((SLITSIZE2 - 0) / SLITRES)) + 1) - 1)
	sourcept1 = ((SLITRES * tmpvar29) + 0)
	
	! % Second slit.
! ...
horizpos = (screenpt - (((cellnum * CELLSIZE) + SLITSIZE1) + sourcept1));
	x = abs((horizpos + COMPLEX(0.0, DISTANCE)));
	
	! % Add up the wave contribution from the second slit.
wavesum = (wavesum + (T2 * exp((COMPLEX(0.0, K) * x))));
END DO
	sourcept = sourcept1
END DO
	
	
	! % Intensity is electric field
intensity = (abs(wavesum) ** 2);
	
	
	tmpvar19 = (screenpt * 100);
	tmpvar22 = (intensity / ((CELLS * CELLSIZE) / SLITRES));
	! % squared.
! % Normalize intensity so that it is approximately 1.
newdata = [tmpvar19, tmpvar22];
	tmpvar23 = (tmpvar23 + 1)
	mag(tmpvar23, :) = newdata;
END DO





END SUBROUTINE diffraction

END MODULE Mod_drv_diff

PROGRAM drv_diff
USE Mod_drv_diff
IMPLICIT NONE
INTEGER :: scale
DOUBLE PRECISION :: time
DOUBLE PRECISION, DIMENSION(1) :: output
CHARACTER(3) :: valid
DOUBLE PRECISION, DIMENSION(1, 6) :: t1
CHARACTER(10) :: arg_buffer
DOUBLE PRECISION :: SLITSIZE2
DOUBLE PRECISION :: SLITSIZE1
DOUBLE PRECISION :: timing_clock
INTEGER :: CELLS
DOUBLE PRECISION :: tmpvar3
INTEGER, DIMENSION(1, 6) :: tmpvar1
INTEGER :: time1
DOUBLE PRECISION, DIMENSION(:, :), ALLOCATABLE :: mag
INTEGER :: int_tmpvar
INTEGER :: T3
INTEGER :: T2
DOUBLE PRECISION, DIMENSION(1, 6) :: t4
DOUBLE PRECISION, DIMENSION(1, 1) :: tmpvar30

timing_clock = 0.0
int_tmpvar = 0
arg_buffer = '0000000000'
DO int_tmpvar = 1 , IARGC()
	CALL GETARG(int_tmpvar, arg_buffer) 
	IF ((int_tmpvar == 1)) THEN 
	READ(arg_buffer, *) scale
END IF
END DO




timing_clock = timing_clock
! % 
! %  Driver for the diffraction pattern calculator.
! % 
CALL CPU_TIME(timing_clock); t1 = 0.0;t1(1,6) = timing_clock;
CELLS = 2;
SLITSIZE1 = 1e-5;
SLITSIZE2 = 1e-5;
T3 = 1;
T2 = 0;
DO time1 = 1 , scale
	CALL diffraction(CELLS, SLITSIZE1, SLITSIZE2, T3, T2, mag)
END DO
time = time1
CALL CPU_TIME(timing_clock); t4 = 0.0;t4(1,6) = timing_clock;

tmpvar1(1, :) = [0, 0, 86400, 3600, 60, 1];
tmpvar30 = MATMUL((t4 - t1), TRANSPOSE(tmpvar1))
! % Compute the running time in seconds
time = tmpvar30(1, 1);

tmpvar3 =  (SUM(mag)/(0.0+SIZE(mag)));
! % Store the benchmark output
output = [tmpvar3];

! % No validation performed
valid = 'N/A';
PRINT *,time;
PRINT *,output;
PRINT *,valid;
IF (ALLOCATED(mag)) THEN 
	DEALLOCATE(mag)
END IF
END PROGRAM drv_diff

