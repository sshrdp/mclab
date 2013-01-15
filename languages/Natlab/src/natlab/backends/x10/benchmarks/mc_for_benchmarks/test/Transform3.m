function drv_foo()
% Tranform matrix concatenation into 
% index increasing form, and 
% Infer the values of new index

n=floor(2*2.0)
m=n*2.2;
s = n*0.5;

mag = zeros(0, 2);
for i=1:s:m
    newdata = [i, i];
    mag = [mag; newdata];
end


end
