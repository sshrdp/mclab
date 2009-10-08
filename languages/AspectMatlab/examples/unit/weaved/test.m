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
  AM_CVar_0 = AM_GLOBAL.unit.unit_acalls('G', 0, @G, {});
  AM_CVar_1 = AM_GLOBAL.unit.unit_acalls('m_earth', 1, @m_earth, {});
  AM_CVar_2 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_0, AM_CVar_1}, 0, @mtimes, {AM_CVar_0, AM_CVar_1});
  AM_CVar_3 = AM_GLOBAL.unit.unit_acalls('r_earth', 2, @r_earth, {});
  AM_CVar_4 = AM_GLOBAL.unit.unit_power({AM_CVar_3, 2}, 0, @power, {AM_CVar_3, 2});
  AM_CVar_5 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_2, AM_CVar_4}, 0, @mrdivide, {AM_CVar_2, AM_CVar_4});
  AM_CVar_6 = AM_GLOBAL.unit.unit_acalls('dozen', 3, @dozen, {});
  AM_CVar_7 = AM_GLOBAL.unit.unit_acalls('s', 4, @s, {});
  AM_CVar_8 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_6, AM_CVar_7}, 1, @mtimes, {AM_CVar_6, AM_CVar_7});
  AM_CVar_9 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_5, AM_CVar_8}, 2, @mtimes, {AM_CVar_5, AM_CVar_8});
% the following script shows some example uses for the units aspect
% (it's a function so that we can use private functions)
% falling for a dozen seconds down from the earth surface in vacuum (approximate)
%d = (G*m_earth/r_earth^2)*dozen*s; % 1g*12*seconds
  d = AM_CVar_9;
  AM_CVar_10 = d;
  AM_GLOBAL.unit.unit_adisp({AM_CVar_10}, 0, @disp, {AM_CVar_10});
  AM_CVar_11 = AM_GLOBAL.unit.unit_acalls('AU', 5, @AU, {});
  AM_CVar_12 = AM_GLOBAL.unit.unit_acalls('c', 6, @c, {});
  AM_CVar_13 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_11, AM_CVar_12}, 1, @mrdivide, {AM_CVar_11, AM_CVar_12});
% time for light to travel from sun to earth
%t = AU/c;
  t = AM_CVar_13;
  AM_CVar_14 = t;
  AM_GLOBAL.unit.unit_adisp({AM_CVar_14}, 1, @disp, {AM_CVar_14});
  AM_CVar_15 = AM_GLOBAL.unit.unit_acalls('lb', 7, @lb, {});
  AM_CVar_16 = AM_GLOBAL.unit.unit_acalls('feet', 8, @feet, {});
  AM_CVar_17 = AM_GLOBAL.unit.unit_amtimes({5, AM_CVar_16}, 3, @mtimes, {5, AM_CVar_16});
  AM_CVar_18 = AM_GLOBAL.unit.unit_acalls('inches', 9, @inches, {});
  AM_CVar_19 = AM_GLOBAL.unit.unit_amtimes({8, AM_CVar_18}, 4, @mtimes, {8, AM_CVar_18});
  AM_CVar_20 = AM_GLOBAL.unit.unit_aplus({AM_CVar_17, AM_CVar_19}, 0, @plus, {AM_CVar_17, AM_CVar_19});
  AM_CVar_21 = AM_GLOBAL.unit.unit_power({AM_CVar_20, 2}, 1, @power, {AM_CVar_20, 2});
  AM_CVar_22 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_15, AM_CVar_21}, 2, @mrdivide, {AM_CVar_15, AM_CVar_21});
  AM_CVar_23 = AM_GLOBAL.unit.unit_amtimes({180, AM_CVar_22}, 5, @mtimes, {180, AM_CVar_22});
% bmi given in imperial units
%bmi = 180*lb/(5*feet + 8*inches)^2
  bmi = AM_CVar_23;
  AM_CVar_24 = bmi;
  AM_GLOBAL.unit.unit_adisp({AM_CVar_24}, 2, @disp, {AM_CVar_24});
  AM_CVar_25 = AM_GLOBAL.unit.unit_amtimes({50, 25}, 6, @mtimes, {50, 25});
  AM_CVar_26 = AM_GLOBAL.unit.unit_acalls('m', 10, @m, {});
  AM_CVar_27 = AM_GLOBAL.unit.unit_power({AM_CVar_26, 3}, 2, @power, {AM_CVar_26, 3});
  AM_CVar_28 = AM_GLOBAL.unit.unit_amtimes({2, AM_CVar_27}, 7, @mtimes, {2, AM_CVar_27});
  AM_CVar_29 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_25, AM_CVar_28}, 8, @mtimes, {AM_CVar_25, AM_CVar_28});
  AM_CVar_30 = AM_GLOBAL.unit.unit_acalls('Kg', 11, @Kg, {});
  AM_CVar_31 = AM_GLOBAL.unit.unit_acalls('L', 12, @L, {});
  AM_CVar_32 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_30, AM_CVar_31}, 3, @mrdivide, {AM_CVar_30, AM_CVar_31});
  AM_CVar_33 = AM_GLOBAL.unit.unit_acalls('K', 13, @K, {});
  AM_CVar_34 = AM_GLOBAL.unit.unit_amtimes({80, AM_CVar_33}, 9, @mtimes, {80, AM_CVar_33});
  AM_CVar_35 = AM_GLOBAL.unit.unit_acalls('J', 14, @J, {});
  AM_CVar_36 = AM_GLOBAL.unit.unit_acalls('g', 15, @g, {});
  AM_CVar_37 = AM_GLOBAL.unit.unit_acalls('K', 16, @K, {});
  AM_CVar_38 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_36, AM_CVar_37}, 10, @mtimes, {AM_CVar_36, AM_CVar_37});
  AM_CVar_39 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_35, AM_CVar_38}, 4, @mrdivide, {AM_CVar_35, AM_CVar_38});
  AM_CVar_40 = AM_GLOBAL.unit.unit_amtimes({4.1813, AM_CVar_39}, 11, @mtimes, {4.1813, AM_CVar_39});
  AM_CVar_41 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_34, AM_CVar_40}, 12, @mtimes, {AM_CVar_34, AM_CVar_40});
  AM_CVar_42 = AM_GLOBAL.unit.unit_acalls('KJ', 17, @KJ, {});
  AM_CVar_43 = AM_GLOBAL.unit.unit_acalls('Kg', 18, @Kg, {});
  AM_CVar_44 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_42, AM_CVar_43}, 5, @mrdivide, {AM_CVar_42, AM_CVar_43});
  AM_CVar_45 = AM_GLOBAL.unit.unit_amtimes({2257, AM_CVar_44}, 13, @mtimes, {2257, AM_CVar_44});
  AM_CVar_46 = AM_GLOBAL.unit.unit_aplus({AM_CVar_41, AM_CVar_45}, 1, @plus, {AM_CVar_41, AM_CVar_45});
  AM_CVar_47 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_32, AM_CVar_46}, 14, @mtimes, {AM_CVar_32, AM_CVar_46});
  AM_CVar_48 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_29, AM_CVar_47}, 15, @mtimes, {AM_CVar_29, AM_CVar_47});
% energy needed to vaporize an olympic size swimming pool at 293K (20celsius)
...
...
...
  e1 = AM_CVar_48;
  AM_CVar_49 = e1;
  AM_GLOBAL.unit.unit_adisp({AM_CVar_49}, 3, @disp, {AM_CVar_49});
  AM_CVar_50 = AM_GLOBAL.unit.unit_acalls('kilotons', 19, @kilotons, {});
  AM_CVar_51 = AM_GLOBAL.unit.unit_amtimes({12, AM_CVar_50}, 16, @mtimes, {12, AM_CVar_50});
  AM_CVar_52 = AM_GLOBAL.unit.unit_acalls('J', 20, @J, {});
  AM_CVar_53 = AM_GLOBAL.unit.unit_acalls('g', 21, @g, {});
  AM_CVar_54 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_52, AM_CVar_53}, 6, @mrdivide, {AM_CVar_52, AM_CVar_53});
  AM_CVar_55 = AM_GLOBAL.unit.unit_amtimes({4184, AM_CVar_54}, 17, @mtimes, {4184, AM_CVar_54});
  AM_CVar_56 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_51, AM_CVar_55}, 18, @mtimes, {AM_CVar_51, AM_CVar_55});
% energy of a small nuclear bomb
%e2 = 12*kilotons*(4184*J/g);
  e2 = AM_CVar_56;
  AM_CVar_57 = e2;
  AM_GLOBAL.unit.unit_adisp({AM_CVar_57}, 4, @disp, {AM_CVar_57});
  AM_CVar_58 = e2;
  AM_CVar_59 = e1;
  AM_CVar_60 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_58, AM_CVar_59}, 7, @mrdivide, {AM_CVar_58, AM_CVar_59});
  AM_GLOBAL.unit.unit_adisp({AM_CVar_60}, 5, @disp, {AM_CVar_60});
  AM_CVar_61 = integrate(@F, [0, 10], 0.001);
  AM_CVar_62 = AM_GLOBAL.unit.unit_acalls('s', 22, @s, {});
  AM_CVar_63 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_61, AM_CVar_62}, 19, @mtimes, {AM_CVar_61, AM_CVar_62});
  AM_GLOBAL.unit.unit_adisp({AM_CVar_63}, 6, @disp, {AM_CVar_63});
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
  function  [y] = F(t)
    AM_CVar_64 = t;
    AM_CVar_65 = AM_GLOBAL.unit.unit_acalls('m', 23, @m, {});
    AM_CVar_66 = AM_GLOBAL.unit.unit_acalls('s', 24, @s, {});
    AM_CVar_67 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_65, AM_CVar_66}, 8, @mrdivide, {AM_CVar_65, AM_CVar_66});
    AM_CVar_68 = AM_GLOBAL.unit.unit_amtimes({10, AM_CVar_67}, 20, @mtimes, {10, AM_CVar_67});
    AM_CVar_69 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_64, AM_CVar_68}, 21, @mtimes, {AM_CVar_64, AM_CVar_68});
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
  AM_CVar_74 = AM_GLOBAL.unit.unit_aminus({AM_CVar_72, AM_CVar_73}, 0, @minus, {AM_CVar_72, AM_CVar_73});
  AM_CVar_75 = b;
  AM_CVar_76 = a;
  AM_CVar_77 = AM_GLOBAL.unit.unit_aminus({AM_CVar_75, AM_CVar_76}, 1, @minus, {AM_CVar_75, AM_CVar_76});
  AM_CVar_78 = dx;
  AM_CVar_79 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_77, AM_CVar_78}, 9, @mrdivide, {AM_CVar_77, AM_CVar_78});
  AM_CVar_80 = AM_GLOBAL.unit.unit_round({AM_CVar_79}, 0, @round, {AM_CVar_79});
  AM_CVar_81 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_74, AM_CVar_80}, 10, @mrdivide, {AM_CVar_74, AM_CVar_80});
%dx = (b-a)/round((b-a)/dx); % round dx    
  dx = AM_CVar_81;
  AM_CVar_82 = dx;
  AM_CVar_83 = AM_GLOBAL.unit.unit_amrdivide({AM_CVar_82, 2}, 11, @mrdivide, {AM_CVar_82, 2});
  AM_CVar_84 = a;
  AM_CVar_85 = f(AM_CVar_84);
  AM_CVar_86 = b;
  AM_CVar_87 = f(AM_CVar_86);
  AM_CVar_88 = AM_GLOBAL.unit.unit_aplus({AM_CVar_85, AM_CVar_87}, 2, @plus, {AM_CVar_85, AM_CVar_87});
  AM_CVar_89 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_83, AM_CVar_88}, 22, @mtimes, {AM_CVar_83, AM_CVar_88});
%x = dx/2*(f(a) + f(b)); % f(a), f(b) in the above sum
  x = AM_CVar_89;
  AM_CVar_90 = a;
  AM_CVar_91 = dx;
  AM_CVar_92 = AM_GLOBAL.unit.unit_aplus({AM_CVar_90, AM_CVar_91}, 3, @plus, {AM_CVar_90, AM_CVar_91});
  AM_CVar_93 = dx;
  AM_CVar_94 = b;
  AM_CVar_95 = dx;
  AM_CVar_96 = AM_GLOBAL.unit.unit_aminus({AM_CVar_94, AM_CVar_95}, 2, @minus, {AM_CVar_94, AM_CVar_95});
  AM_CVar_97 = AM_GLOBAL.unit.unit_colon({AM_CVar_92, AM_CVar_93, AM_CVar_96}, 0, @colon, {AM_CVar_92, AM_CVar_93, AM_CVar_96});
  AM_tmpAS_xi = AM_GLOBAL.unit.unit_loop(AM_CVar_97, 0, {}, {AM_CVar_97});
  for AM_tmpFS_xi = (1 : numel(AM_tmpAS_xi))
    xi = AM_tmpAS_xi(AM_tmpFS_xi);
    AM_CVar_98 = x;
    AM_CVar_99 = dx;
    AM_CVar_100 = xi;
    AM_CVar_101 = f(AM_CVar_100);
    AM_CVar_102 = AM_GLOBAL.unit.unit_amtimes({AM_CVar_99, AM_CVar_101}, 23, @mtimes, {AM_CVar_99, AM_CVar_101});
    AM_CVar_103 = AM_GLOBAL.unit.unit_aplus({AM_CVar_98, AM_CVar_102}, 4, @plus, {AM_CVar_98, AM_CVar_102});
% sum over all x_i, i = 1,2,3,..,N-1
% x = x + dx*f(xi);
    x = AM_CVar_103;
  end
  if AM_EntryPoint_0
    AM_GLOBAL = [];
  end
end

