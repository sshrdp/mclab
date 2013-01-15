% args: {a=(double,[2, 2],{REAL})}
function  [x] = forTest2(a)
  mc_t1 = 1;                          % mc_t1=(double,1.0,[1, 1],{REAL})
  mc_t2 = 10;                         % mc_t2=(double,10.0,[1, 1],{REAL})
  for b = (mc_t1 : mc_t2);
    mc_t0 = 1;                          % mc_t0=(double,1.0,[1, 1],{REAL})
    [b] = plus(b, mc_t0);               % b=(double,[1, 1],{REAL})
    [a] = plus(a, b);                   % a=(double,[1, 1],{REAL})
  end
  x = a;                              % x=(double,null,{REAL})
end
% results: [(double,null,{REAL})]