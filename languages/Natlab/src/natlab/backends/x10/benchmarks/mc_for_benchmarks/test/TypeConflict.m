function drv_foo()
%. When a variable is assigned to a new type [#2],
%    which cannot be merged with old type [#1].
%    Then, the variable in the rest of program should be rename. 
% Rename along the code-node-list  according to flow set, 
% for all reached and unique reached. 

	n = floor(2.0); 
		
	dr = zeros(n, n, 3);
	
	% Case-1: not first use flow conflict 'nb3d'
	for i=1:10
	
		% First def 		---- [#1]
		r = dr.*dr;
		
		% RHS use
		% LHS: new type cannot merge to old type, new-def ---- [#2]
		r = r(:, :, 1)+r(:, :, 2)+r(:, :, 3);
		
		% RHS direct-use of r
		dr(:, :, 2) = dr(:, :, 2)./r;  
		
		% LHS this is another new-def of r ---- [#3]
		r(1,1) = 1.0;			     
	
		% RHS is a use of #3
		% LHS is a new-def 	---- [#4]
		r = sqrt(r);
		
		%  RHS is a indirect-use ( #4-use)
		dr(:, :, 3) = dr(:, :, 3)./r;
	end

end
