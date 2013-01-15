function drv_foo()
% Cases to show type inference functions 
% appear in different flow control statments

% --------------------------------

i = 0;
sum = 0;
for i=1:100
    sum = sum + 1;
end
y = sum;

if(i==1),
    y = 1;
else
    y = i;
end
z = y;


% --------------------------------
i=0
x=0                     
for i=1:5               
  k = x
  if(i>2)               
    x = zeros(2,2)
  else                  
     x = ones(3,3) 
  end                   
  if(i==1),
    y = 1;
  else
    y = i;
  end
end                     
z = x


% --------------------------------
% switch-case statement
i=0;
a=0;
for i=1:5
  k = a;
 switch i
    case 1
      a = a * 2 ;
    case 2
      a = a / 2; 
    case 3
      a = a ^ 2; 
    otherwise
      a = a - 1;
    end
end
z = a;

% --------------------------------
%  While statement
j=1;
k=0;
while(j>0)
   if(j>10)
   	j = j/2;
   else
   	j = j/3;
   end
   k=j;
end
n=j;
