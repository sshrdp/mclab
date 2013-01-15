% a = zeros(1500,1500);
% b = zeros(1500,1500);
% This is the PrettyPrint/StructureString() on the modified AST
% 1. The Name is solved to be either a variable without parentheses. 
%    or a function call/array access with parentheses.
%    i.g. clc(), tic(), toc()
% 2. The SPACE in the matrix brackets [] has been replaced by COMMA.
% 3. % Command Syntax doesn't support right now : % clear a; clear b;  
%! INTEGER :: runs
% \languages\Fortran\benchmark2_2.n
clc()

runs = 3;			% Number of times the tests are executed
times = zeros(5, 3);

label = '   Matlab Benchmark 2'
disp(label)
disp('   Matlab Benchmark 2')
disp('   ==================')
disp(['Number of times each test is run__________________________: ', num2str(runs)])  % SPACE->COMMA
disp(' ')


disp('   I. Matrix calculation')
disp('   ---------------------')


% (1)
cumulate = 0; a = 0;  b = 0;

for i = 1:runs
  tic();
    a = abs(randn(1500, 1500)/10);
    b = a';
    a1 = reshape(b, 750, 3000);
    b1 = a1';
  timing = toc();
  cumulate = cumulate + timing;
end;

timing = cumulate/runs;
% times(1, 1) = timing;
disp(['Creation, transp., deformation of a 1500x1500 matrix (sec): ', num2str(timing)]) % SPACE->COMMA
% clear a; clear b;  % Command Syntax doesn't support right now

% (2)
cumulate = 0; b2 = 0;
for i = 1:runs
  a2 = abs(randn(800, 800)/2);
  tic; 
    b2 = a2.^1000; 
  timing = toc();
  cumulate = cumulate + timing;
end
timing = cumulate/runs;
% times(2, 1) = timing;
% clear a; clear b;
disp(['800x800 normal distributed random matrix ^1000______ (sec): ', num2str(timing)])
