function  drv_foo()
	m1=[9,8,7;6,5,4;11,12,13;15,16,17];
	mr1 = mean(m1(:));
	mr2 = mean(m1);
	disp(mr1);
	disp(mr2);

	m2(1, :) = [9, 8, 7]
	m2(2, :) = [6, 5, 4]
	m2(3, :) = [11, 12, 13]
	m2(4, :) = [15, 16, 17]

	s1=zeros(4,4,3);
	ms1 = mean(s1(:))
	ms2 = mean(s1)
	r1 = s1(:,1,1)

	A1 = 10:15
	A2 = -2.5:2.5	 
	A3 = 1:6.3
	A4 = 10:2.5:15
	A5 = -1:5
	A6 = 10:2:15
	disp(A1);disp(A2);disp(A3);disp(A4);disp(A5);disp(A6);
	
	Up=9^1
	Low = 2^1
	Inc=2^1
	y=Low:Inc:Up
	
	Upf=7.2^2
	z=Low:Inc:Upf	 
	
	T = 2.3^1
	dT = 0.5^1
	x = 1:dT:T,
	disp(x);disp(y);disp(z);
	
	% Loop with floating-point increasing step
	for i = 1:dT:T,
	    disp(i);
	end

	% Nonconsecutive Elements
	n=floor(2.0);
	m=floor(3.0);
	AA=zeros(m,n);
	XX=AA(2:m,1)
	YY=AA(:,1)
	ZZ=AA(1,:)
	QQ=AA(:,:)
	
	ii = ones(n, 1)*(1:n);  

	ii(1:n, 1)=(1:n);
	disp(size(XX));disp(size(YY));disp(size(ZZ));disp(size(QQ));disp(size(ii));
	
	BB=[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1];
	BB(1:3:16) = -9
	disp(BB)	
end
