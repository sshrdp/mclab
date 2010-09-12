function  [] = test()
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'unit'))
    AM_GLOBAL.unit = unit;
  end
  AM_CVar_0 = AM_GLOBAL.unit.unit_acalls(0, @G, {}, {}, {}, 'test.m', 6, 'test', 'G', {}, []);
  AM_CVar_1 = AM_GLOBAL.unit.unit_acalls(1, @m_earth, {}, {}, {}, 'test.m', 6, 'test', 'm_earth', {}, []);
  AM_CVar_2 = AM_GLOBAL.unit.unit_amtimes(0, @mtimes, {AM_CVar_0, AM_CVar_1}, {AM_CVar_0, AM_CVar_1}, {}, 'test.m', 6, 'test', 'mtimes', {}, []);
  AM_CVar_3 = AM_GLOBAL.unit.unit_acalls(2, @r_earth, {}, {}, {}, 'test.m', 6, 'test', 'r_earth', {}, []);
  AM_CVar_4 = AM_GLOBAL.unit.unit_power(0, @power, {AM_CVar_3, 2}, {AM_CVar_3, 2}, {}, 'test.m', 6, 'test', 'power', {}, []);
  AM_CVar_5 = AM_GLOBAL.unit.unit_amrdivide(0, @mrdivide, {AM_CVar_2, AM_CVar_4}, {AM_CVar_2, AM_CVar_4}, {}, 'test.m', 6, 'test', 'mrdivide', {}, []);
  AM_CVar_6 = AM_GLOBAL.unit.unit_acalls(3, @dozen, {}, {}, {}, 'test.m', 6, 'test', 'dozen', {}, []);
  AM_CVar_7 = AM_GLOBAL.unit.unit_acalls(4, @s, {}, {}, {}, 'test.m', 6, 'test', 's', {}, []);
  AM_CVar_8 = AM_GLOBAL.unit.unit_amtimes(1, @mtimes, {AM_CVar_6, AM_CVar_7}, {AM_CVar_6, AM_CVar_7}, {}, 'test.m', 6, 'test', 'mtimes', {}, []);
  AM_CVar_9 = AM_GLOBAL.unit.unit_amtimes(2, @mtimes, {AM_CVar_5, AM_CVar_8}, {AM_CVar_5, AM_CVar_8}, {}, 'test.m', 6, 'test', 'mtimes', {}, []);
% the following script shows some example uses for the units aspect
% (it's a function so that we can use private functions)
% falling for a dozen seconds down from the earth surface in vacuum (approximate)
%d = (G*m_earth/r_earth^2)*dozen*s; % 1g*12*seconds
  d = AM_CVar_9;
  AM_CVar_10 = d;
  AM_GLOBAL.unit.unit_adisp(0, @disp, {AM_CVar_10}, {AM_CVar_10}, {}, 'test.m', 7, 'test', 'disp', {}, []);
  AM_CVar_11 = AM_GLOBAL.unit.unit_acalls(5, @AU, {}, {}, {}, 'test.m', 10, 'test', 'AU', {}, []);
  AM_CVar_12 = AM_GLOBAL.unit.unit_acalls(6, @c, {}, {}, {}, 'test.m', 10, 'test', 'c', {}, []);
  AM_CVar_13 = AM_GLOBAL.unit.unit_amrdivide(1, @mrdivide, {AM_CVar_11, AM_CVar_12}, {AM_CVar_11, AM_CVar_12}, {}, 'test.m', 10, 'test', 'mrdivide', {}, []);
% time for light to travel from sun to earth
%t = AU/c;
  t = AM_CVar_13;
  AM_CVar_14 = t;
  AM_GLOBAL.unit.unit_adisp(1, @disp, {AM_CVar_14}, {AM_CVar_14}, {}, 'test.m', 11, 'test', 'disp', {}, []);
  AM_CVar_15 = AM_GLOBAL.unit.unit_acalls(7, @lb, {}, {}, {}, 'test.m', 14, 'test', 'lb', {}, []);
  AM_CVar_16 = AM_GLOBAL.unit.unit_acalls(8, @feet, {}, {}, {}, 'test.m', 14, 'test', 'feet', {}, []);
  AM_CVar_17 = AM_GLOBAL.unit.unit_amtimes(3, @mtimes, {5, AM_CVar_16}, {5, AM_CVar_16}, {}, 'test.m', 14, 'test', 'mtimes', {}, []);
  AM_CVar_18 = AM_GLOBAL.unit.unit_acalls(9, @inches, {}, {}, {}, 'test.m', 14, 'test', 'inches', {}, []);
  AM_CVar_19 = AM_GLOBAL.unit.unit_amtimes(4, @mtimes, {8, AM_CVar_18}, {8, AM_CVar_18}, {}, 'test.m', 14, 'test', 'mtimes', {}, []);
  AM_CVar_20 = AM_GLOBAL.unit.unit_aplus(0, @plus, {AM_CVar_17, AM_CVar_19}, {AM_CVar_17, AM_CVar_19}, {}, 'test.m', 14, 'test', 'plus', {}, []);
  AM_CVar_21 = AM_GLOBAL.unit.unit_power(1, @power, {AM_CVar_20, 2}, {AM_CVar_20, 2}, {}, 'test.m', 14, 'test', 'power', {}, []);
  AM_CVar_22 = AM_GLOBAL.unit.unit_amrdivide(2, @mrdivide, {AM_CVar_15, AM_CVar_21}, {AM_CVar_15, AM_CVar_21}, {}, 'test.m', 14, 'test', 'mrdivide', {}, []);
  AM_CVar_23 = AM_GLOBAL.unit.unit_amtimes(5, @mtimes, {180, AM_CVar_22}, {180, AM_CVar_22}, {}, 'test.m', 14, 'test', 'mtimes', {}, []);
% bmi given in imperial units
%bmi = 180*lb/(5*feet + 8*inches)^2
  bmi = AM_CVar_23;
  AM_CVar_24 = bmi;
  AM_GLOBAL.unit.unit_adisp(2, @disp, {AM_CVar_24}, {AM_CVar_24}, {}, 'test.m', 15, 'test', 'disp', {}, []);
  AM_CVar_25 = AM_GLOBAL.unit.unit_amtimes(6, @mtimes, {50, 25}, {50, 25}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_26 = AM_GLOBAL.unit.unit_acalls(10, @m, {}, {}, {}, 'test.m', 18, 'test', 'm', {}, []);
  AM_CVar_27 = AM_GLOBAL.unit.unit_power(2, @power, {AM_CVar_26, 3}, {AM_CVar_26, 3}, {}, 'test.m', 18, 'test', 'power', {}, []);
  AM_CVar_28 = AM_GLOBAL.unit.unit_amtimes(7, @mtimes, {2, AM_CVar_27}, {2, AM_CVar_27}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_29 = AM_GLOBAL.unit.unit_amtimes(8, @mtimes, {AM_CVar_25, AM_CVar_28}, {AM_CVar_25, AM_CVar_28}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_30 = AM_GLOBAL.unit.unit_acalls(11, @Kg, {}, {}, {}, 'test.m', 18, 'test', 'Kg', {}, []);
  AM_CVar_31 = AM_GLOBAL.unit.unit_acalls(12, @L, {}, {}, {}, 'test.m', 18, 'test', 'L', {}, []);
  AM_CVar_32 = AM_GLOBAL.unit.unit_amrdivide(3, @mrdivide, {AM_CVar_30, AM_CVar_31}, {AM_CVar_30, AM_CVar_31}, {}, 'test.m', 18, 'test', 'mrdivide', {}, []);
  AM_CVar_33 = AM_GLOBAL.unit.unit_acalls(13, @K, {}, {}, {}, 'test.m', 18, 'test', 'K', {}, []);
  AM_CVar_34 = AM_GLOBAL.unit.unit_amtimes(9, @mtimes, {80, AM_CVar_33}, {80, AM_CVar_33}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_35 = AM_GLOBAL.unit.unit_acalls(14, @J, {}, {}, {}, 'test.m', 18, 'test', 'J', {}, []);
  AM_CVar_36 = AM_GLOBAL.unit.unit_acalls(15, @g, {}, {}, {}, 'test.m', 18, 'test', 'g', {}, []);
  AM_CVar_37 = AM_GLOBAL.unit.unit_acalls(16, @K, {}, {}, {}, 'test.m', 18, 'test', 'K', {}, []);
  AM_CVar_38 = AM_GLOBAL.unit.unit_amtimes(10, @mtimes, {AM_CVar_36, AM_CVar_37}, {AM_CVar_36, AM_CVar_37}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_39 = AM_GLOBAL.unit.unit_amrdivide(4, @mrdivide, {AM_CVar_35, AM_CVar_38}, {AM_CVar_35, AM_CVar_38}, {}, 'test.m', 18, 'test', 'mrdivide', {}, []);
  AM_CVar_40 = AM_GLOBAL.unit.unit_amtimes(11, @mtimes, {4.1813, AM_CVar_39}, {4.1813, AM_CVar_39}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_41 = AM_GLOBAL.unit.unit_amtimes(12, @mtimes, {AM_CVar_34, AM_CVar_40}, {AM_CVar_34, AM_CVar_40}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_42 = AM_GLOBAL.unit.unit_acalls(17, @KJ, {}, {}, {}, 'test.m', 18, 'test', 'KJ', {}, []);
  AM_CVar_43 = AM_GLOBAL.unit.unit_acalls(18, @Kg, {}, {}, {}, 'test.m', 18, 'test', 'Kg', {}, []);
  AM_CVar_44 = AM_GLOBAL.unit.unit_amrdivide(5, @mrdivide, {AM_CVar_42, AM_CVar_43}, {AM_CVar_42, AM_CVar_43}, {}, 'test.m', 18, 'test', 'mrdivide', {}, []);
  AM_CVar_45 = AM_GLOBAL.unit.unit_amtimes(13, @mtimes, {2257, AM_CVar_44}, {2257, AM_CVar_44}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_46 = AM_GLOBAL.unit.unit_aplus(1, @plus, {AM_CVar_41, AM_CVar_45}, {AM_CVar_41, AM_CVar_45}, {}, 'test.m', 18, 'test', 'plus', {}, []);
  AM_CVar_47 = AM_GLOBAL.unit.unit_amtimes(14, @mtimes, {AM_CVar_32, AM_CVar_46}, {AM_CVar_32, AM_CVar_46}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
  AM_CVar_48 = AM_GLOBAL.unit.unit_amtimes(15, @mtimes, {AM_CVar_29, AM_CVar_47}, {AM_CVar_29, AM_CVar_47}, {}, 'test.m', 18, 'test', 'mtimes', {}, []);
% energy needed to vaporize an olympic size swimming pool at 293K (20celsius)
...
...
...
  e1 = AM_CVar_48;
  AM_CVar_49 = e1;
  AM_GLOBAL.unit.unit_adisp(3, @disp, {AM_CVar_49}, {AM_CVar_49}, {}, 'test.m', 23, 'test', 'disp', {}, []);
  AM_CVar_50 = AM_GLOBAL.unit.unit_acalls(19, @kilotons, {}, {}, {}, 'test.m', 26, 'test', 'kilotons', {}, []);
  AM_CVar_51 = AM_GLOBAL.unit.unit_amtimes(16, @mtimes, {12, AM_CVar_50}, {12, AM_CVar_50}, {}, 'test.m', 26, 'test', 'mtimes', {}, []);
  AM_CVar_52 = AM_GLOBAL.unit.unit_acalls(20, @J, {}, {}, {}, 'test.m', 26, 'test', 'J', {}, []);
  AM_CVar_53 = AM_GLOBAL.unit.unit_acalls(21, @g, {}, {}, {}, 'test.m', 26, 'test', 'g', {}, []);
  AM_CVar_54 = AM_GLOBAL.unit.unit_amrdivide(6, @mrdivide, {AM_CVar_52, AM_CVar_53}, {AM_CVar_52, AM_CVar_53}, {}, 'test.m', 26, 'test', 'mrdivide', {}, []);
  AM_CVar_55 = AM_GLOBAL.unit.unit_amtimes(17, @mtimes, {4184, AM_CVar_54}, {4184, AM_CVar_54}, {}, 'test.m', 26, 'test', 'mtimes', {}, []);
  AM_CVar_56 = AM_GLOBAL.unit.unit_amtimes(18, @mtimes, {AM_CVar_51, AM_CVar_55}, {AM_CVar_51, AM_CVar_55}, {}, 'test.m', 26, 'test', 'mtimes', {}, []);
% energy of a small nuclear bomb
%e2 = 12*kilotons*(4184*J/g);
  e2 = AM_CVar_56;
  AM_CVar_57 = e2;
  AM_GLOBAL.unit.unit_adisp(4, @disp, {AM_CVar_57}, {AM_CVar_57}, {}, 'test.m', 27, 'test', 'disp', {}, []);
  AM_CVar_58 = e2;
  AM_CVar_59 = e1;
  AM_CVar_60 = AM_GLOBAL.unit.unit_amrdivide(7, @mrdivide, {AM_CVar_58, AM_CVar_59}, {AM_CVar_58, AM_CVar_59}, {}, 'test.m', 30, 'test', 'mrdivide', {}, []);
  AM_GLOBAL.unit.unit_adisp(5, @disp, {AM_CVar_60}, {AM_CVar_60}, {}, 'test.m', 30, 'test', 'disp', {}, []);
  AM_CVar_61 = integrate(@F, [0, 10], 0.001);
  AM_CVar_62 = AM_GLOBAL.unit.unit_acalls(22, @s, {}, {}, {}, 'test.m', 36, 'test', 's', {}, []);
  AM_CVar_63 = AM_GLOBAL.unit.unit_amtimes(19, @mtimes, {AM_CVar_61, AM_CVar_62}, {AM_CVar_61, AM_CVar_62}, {}, 'test.m', 36, 'test', 'mtimes', {}, []);
  AM_GLOBAL.unit.unit_adisp(6, @disp, {AM_CVar_63}, {AM_CVar_63}, {}, 'test.m', 36, 'test', 'disp', {}, []);
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
  function  [y] = F(t)
    AM_CVar_64 = t;
    AM_CVar_65 = AM_GLOBAL.unit.unit_acalls(23, @m, {}, {}, {}, 'test.m', 34, 'F', 'm', {}, []);
    AM_CVar_66 = AM_GLOBAL.unit.unit_acalls(24, @s, {}, {}, {}, 'test.m', 34, 'F', 's', {}, []);
    AM_CVar_67 = AM_GLOBAL.unit.unit_amrdivide(8, @mrdivide, {AM_CVar_65, AM_CVar_66}, {AM_CVar_65, AM_CVar_66}, {}, 'test.m', 34, 'F', 'mrdivide', {}, []);
    AM_CVar_68 = AM_GLOBAL.unit.unit_amtimes(20, @mtimes, {10, AM_CVar_67}, {10, AM_CVar_67}, {}, 'test.m', 34, 'F', 'mtimes', {}, []);
    AM_CVar_69 = AM_GLOBAL.unit.unit_amtimes(21, @mtimes, {AM_CVar_64, AM_CVar_68}, {AM_CVar_64, AM_CVar_68}, {}, 'test.m', 34, 'F', 'mtimes', {}, []);
% integrating - distance travelled in 10 seconds when accelerating with v(t) = t*10 m/s
%v = @(t) t.*10.*m./s
    y = AM_CVar_69;
  end
end
function  [x] = integrate(f, I, dx)
  global AM_GLOBAL;
  if (~isempty(AM_GLOBAL))
    AM_EntryPoint_0 = 0;
  else 
    AM_EntryPoint_0 = 1;
  end
  if (~isfield(AM_GLOBAL, 'unit'))
    AM_GLOBAL.unit = unit;
  end
  AM_CVar_70 = I(1);
% integrates f over I = [a b] using dx as the width of intervals
% utilizes the composite trapezoidal rule, which is
% int(f,[a,b],dx) = dx/2*[f(a) + 2*sum(f(x_i),i=1..N-1) + f(b)] +
% (b-a)*h^2*f"(z)/12
% where dx = (b-a)/N and x_i = a + i*h
% dx is rounded to the next integer divisor of (b-a) (so that N is integer)
% where the last term is the error term (not calculated), with z in [a b]
  a = AM_CVar_70;
  AM_CVar_71 = I(2);
  b = AM_CVar_71;
  AM_CVar_72 = b;
  AM_CVar_73 = a;
  AM_CVar_74 = AM_GLOBAL.unit.unit_aminus(0, @minus, {AM_CVar_72, AM_CVar_73}, {AM_CVar_72, AM_CVar_73}, {}, 'test.m', 51, 'integrate', 'minus', {}, []);
  AM_CVar_75 = b;
  AM_CVar_76 = a;
  AM_CVar_77 = AM_GLOBAL.unit.unit_aminus(1, @minus, {AM_CVar_75, AM_CVar_76}, {AM_CVar_75, AM_CVar_76}, {}, 'test.m', 51, 'integrate', 'minus', {}, []);
  AM_CVar_78 = dx;
  AM_CVar_79 = AM_GLOBAL.unit.unit_amrdivide(9, @mrdivide, {AM_CVar_77, AM_CVar_78}, {AM_CVar_77, AM_CVar_78}, {}, 'test.m', 51, 'integrate', 'mrdivide', {}, []);
  AM_CVar_80 = AM_GLOBAL.unit.unit_round(0, @round, {AM_CVar_79}, {AM_CVar_79}, {}, 'test.m', 51, 'integrate', 'round', {}, []);
  AM_CVar_81 = AM_GLOBAL.unit.unit_amrdivide(10, @mrdivide, {AM_CVar_74, AM_CVar_80}, {AM_CVar_74, AM_CVar_80}, {}, 'test.m', 51, 'integrate', 'mrdivide', {}, []);
%dx = (b-a)/round((b-a)/dx); % round dx    
  dx = AM_CVar_81;
  AM_CVar_82 = dx;
  AM_CVar_83 = AM_GLOBAL.unit.unit_amrdivide(11, @mrdivide, {AM_CVar_82, 2}, {AM_CVar_82, 2}, {}, 'test.m', 52, 'integrate', 'mrdivide', {}, []);
  AM_CVar_84 = a;
  AM_CVar_85 = f(AM_CVar_84);
  AM_CVar_86 = b;
  AM_CVar_87 = f(AM_CVar_86);
  AM_CVar_88 = AM_GLOBAL.unit.unit_aplus(2, @plus, {AM_CVar_85, AM_CVar_87}, {AM_CVar_85, AM_CVar_87}, {}, 'test.m', 52, 'integrate', 'plus', {}, []);
  AM_CVar_89 = AM_GLOBAL.unit.unit_amtimes(22, @mtimes, {AM_CVar_83, AM_CVar_88}, {AM_CVar_83, AM_CVar_88}, {}, 'test.m', 52, 'integrate', 'mtimes', {}, []);
%x = dx/2*(f(a) + f(b)); % f(a), f(b) in the above sum
  x = AM_CVar_89;
  AM_CVar_90 = a;
  AM_CVar_91 = dx;
  AM_CVar_92 = AM_GLOBAL.unit.unit_aplus(3, @plus, {AM_CVar_90, AM_CVar_91}, {AM_CVar_90, AM_CVar_91}, {}, 'test.m', 53, 'integrate', 'plus', {}, []);
  AM_CVar_93 = dx;
  AM_CVar_94 = b;
  AM_CVar_95 = dx;
  AM_CVar_96 = AM_GLOBAL.unit.unit_aminus(2, @minus, {AM_CVar_94, AM_CVar_95}, {AM_CVar_94, AM_CVar_95}, {}, 'test.m', 53, 'integrate', 'minus', {}, []);
  AM_CVar_97 = AM_GLOBAL.unit.unit_colon(0, @colon, {AM_CVar_92, AM_CVar_93, AM_CVar_96}, {AM_CVar_92, AM_CVar_93, AM_CVar_96}, {}, 'test.m', 53, 'integrate', 'colon', {}, []);
  AM_tmpAS_xi = AM_GLOBAL.unit.unit_loop(0, {}, {AM_CVar_97}, {}, {}, 'test.m', 53, 'integrate', {}, AM_CVar_97, {});
  for AM_tmpFS_xi = (1 : numel(AM_tmpAS_xi))
    xi = AM_tmpAS_xi(AM_tmpFS_xi);
    AM_CVar_98 = x;
    AM_CVar_99 = dx;
    AM_CVar_100 = xi;
    AM_CVar_101 = f(AM_CVar_100);
    AM_CVar_102 = AM_GLOBAL.unit.unit_amtimes(23, @mtimes, {AM_CVar_99, AM_CVar_101}, {AM_CVar_99, AM_CVar_101}, {}, 'test.m', 54, 'integrate', 'mtimes', {}, []);
    AM_CVar_103 = AM_GLOBAL.unit.unit_aplus(4, @plus, {AM_CVar_98, AM_CVar_102}, {AM_CVar_98, AM_CVar_102}, {}, 'test.m', 54, 'integrate', 'plus', {}, []);
% sum over all x_i, i = 1,2,3,..,N-1
% x = x + dx*f(xi);
    x = AM_CVar_103;
  end
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end
