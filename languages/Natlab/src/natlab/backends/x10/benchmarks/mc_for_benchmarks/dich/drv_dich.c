void main()
{
	int a,b,f1,f2,f3,f4,max1,t;
	int n,m;
	double h,tol;
	double ave;

	a = 4;
	b = 4;
	h = 0.03;
	tol = 10^-5;
	max1 = 1000;
	
	f1 = 20;
	f2 = 180;
	f3 = 80;
	f4 = 0;

	for( t=1; t<=scale; t++) {
	  // U = dirich(f1, f2, f3, f4, a, b, h, tol, max1);  
	  // function U = dirich(f1, f2, f3, f4, a, b, h, tol, max1)
		n = fix(a/h)+1;
		m = fix(b/h)+1;
		ave = (a*(f1+f2)+b*(f3+f4))/(2*a+2*b);
		U = ave*ones(n, m);
		for l = 1:m,
		    U(1, l) = f3;
		    U(n, l) = f4;
		end;
		
		for k = 1:n,
		    U(k, 1) = f1;
		    U(k, m) = f2;
		end;
		
		U(1, 1) = (U(1, 2)+U(2, 1))/2;
		U(1, m) = (U(1, m-1)+U(2, m))/2;
		U(n, 1) = (U(n-1, 1)+U(n, 2))/2;
		U(n, m) = (U(n-1, m)+U(n, m-1))/2;
		
		w = 4/(2+sqrt(4-(cos(pi/(n-1))+cos(pi/(m-1)))^2));
		err = 1;
		cnt = 0;
		
		while ((err > tol) & (cnt <= max1)),
		      err = 0;
		      for l = 2:(m-1),
			  for k = 2:(n-1),
			      relx = w*(U(k, l+1)+U(k, l-1)+ ...
			      U(k+1, l)+U(k-1, l)-4*U(k, l))/4;
			      U(k, l) = U(k, l)+relx;
			      if (err <= abs(relx)),
				 err = abs(relx);
			      end;
			  end;
		      end;
		cnt = cnt+1;
		end;
	}

}

