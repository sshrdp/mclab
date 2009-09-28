function test
% the following script shows some example uses for the units aspect
% (it's a function so that we can use private functions)

% falling for a year
d = 9.8*m/s^2  *1*year;
disp(d);

% bmi given in imperial units
bmi = 180*lb/(5*feet + 8*inches)^2
disp(bmi);

% integrating - distance travelled in 10 seconds when accelerating with
% v(t) = t*10 km/s
v = @(t) t.*10.*km./s
disp(integrate(v,[0*s 10*s],100));
end



% integrates f over I = [a b] using even N subintervales
% utilizes the composite trapezoidal rule, which is
% int(f,[a,b],N) = h/2[f(a) + 2*sum(f(x_i),i=1..N-1) + f(b)] +
% (b-a)*h^2*f"(z)/12
% where h = (b-a)/N and x_i = a + i*h
% where the last term is the error term (not calculated), with z in [a b]
function x = integrate(f,I,N)
    a = I(1);
    b = I(2);
    h = (b-a)/N;
    x = h/2*(f(a) + f(b)); % f(a), f(b) in the above sum
    for xi = a+h:h:b-h % sum over all x_i, i = 1,2,3,..,N-1
        x = x + h*f(xi);
    end
end

