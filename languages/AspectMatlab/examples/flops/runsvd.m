

for n = 10:10:10
  tic
  x = rand(n,n);
  [u,s,v] = SVD(x);
  fprintf('run with n=%x, delta norm=%x, time: %.3fs\n',n,norm(u*diag(diag(s))*v-x,2),toc);
end

