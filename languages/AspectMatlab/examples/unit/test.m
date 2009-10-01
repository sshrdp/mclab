function test
% the following script shows some example uses for the units aspect
% (it's a function so that we can use private functions)

% falling for a dozen seconds down from the earth surface in vacuum (approximate)
d = mtimes(mrdivide(mtimes(g,m_earth),power(r_earth,2)),mtimes(dozen,s));  %d = (G*m_earth/r_earth^2)*dozen*s; % 1g*12*seconds
disp(d);

% time for light to travel from sun to earth
t = mrdivide(AU,c); %t = AU/c;
disp(t);

% bmi given in imperial units
bmi = mtimes(180,mrdivide(lb,power(plus(mtimes(5,feet),mtimes(8,inches)),2))); %bmi = 180*lb/(5*feet + 8*inches)^2
disp(bmi);

% energy needed to vaporize an olympic size swimming pool at 293K (20celsius)
e1 = mtimes(mtimes(mtimes(50,25),mtimes(2,power(m,3))),...
            mtimes(mridivide(Kg,L),...
                   plus(mtimes(mtimes(80,K),mtimes(4.1813,mrdivide(J,mtimes(g,K)))),...
                        mtimes(2257,mrdivide(KJ,Kg)))));
%e1 = (50*25*2*m^3)*(1*Kg/L)*(80*K*4.1813*J/(g*K) + 2257*KJ/Kg);
disp(e1);

% energy of a small nuclear bomb
e2 = mtimes(mtimes(12,kilotons),mtimes(4184,mrdivide(J,g))); %e2 = 12*kilotons*(4184*J/g);
disp(e2);

% number of olympic sized swimming pools that a small nuclear bomb can vaporize
disp(e2/e1);

% integrating - distance travelled in 10 seconds when accelerating with v(t) = t*10 km/s
function y = f(t)
  y = mtimes(t,mtimes(10,mrdivide(km,s))); %v = @(t) t.*10.*km./s
end
disp(integrate(v,[0, 10],mtimes(0.001,s))); % disp(integrate(v,[0 10],0.001*s));
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
    dx = mrdivide(minus(b,a),round(mrdivide(mtimes(b,a),dx))); %dx = (b-a)/round((b*a)/dx); % round dx    
    x = mtimes(mrdivide(dx,2),plus(f(a),f(b)));  %x = dx/2*(f(a) + f(b)); % f(a), f(b) in the above sum
    for xi = a+dx:dx:b-dx % sum over all x_i, i = 1,2,3,..,N-1
        x = plus(x,mtimes(dx,f(xi))); % x = x + dx*f(xi);
    end
end

