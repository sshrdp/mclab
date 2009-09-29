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
disp(integrate(v,[0 10],0.001*s));
end



% integrates f over I = [a b] using dx as the width of intervals
% utilizes the composite trapezoidal rule, which is
% int(f,[a,b],dx) = dx/2*[f(a) + 2*sum(f(x_i),i=1..N-1) + f(b)] +
% (b-a)*h^2*f"(z)/12
% where dx = (b-a)/N and x_i = a + i*h
% dx is rounded to the next integer divisor of (b-a) (so that N is integer)
% where the last term is the error term (not calculated), with z in [a b]
function x = integrate(f,I,dx)
    a = I(1);
    b = I(2);
    dx = (b-a)/round((b*a)/dx); % round dx    

    x = dx/2*(f(a) + f(b)); % f(a), f(b) in the above sum
    for xi = a+dx:dx:b-dx % sum over all x_i, i = 1,2,3,..,N-1
        x = x + dx*f(xi);
    end
end

