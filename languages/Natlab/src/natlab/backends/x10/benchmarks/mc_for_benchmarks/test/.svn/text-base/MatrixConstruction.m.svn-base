function   drv_foo()
% This test case shows
% basic matrix construction and matrix concatenations

	% Constructing single row
	r1=[9,8,7];
	r2=[6,5,4];
	r3=[3,2,1];

	% Constructing matrix by row(s)
	m10 = [r1];
	m12 = [r1; r2];
	m13 = [r1; r2; r3];

	% Constructing matrix by elements in row/column
	m34=[11,12,13; 15,16,17];
	
	% Concatenating matrices, row-matrices, column-matrix
	m4=[m34]

	m5=[m12; m34]
	
	m6=[m12, m34]
	m7=[m6, m34]
	
	m121=[m12; r1]
	m112=[r1; m12]

	% Construting and concatenating column-vector 	
	v1=[9; 8; 7];
	v2=[6; 5; 4];
	
	m12row = [v1, v2];	
	m12col = [v1; v2];
	
	disp(m10); disp(m13); disp(m5); disp(m4); disp(m7); 
	disp(m121); disp(m112); 
	disp(m12row); disp(m12col);
	
end		
