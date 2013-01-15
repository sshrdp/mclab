class testclass{
	static def drv_adpt (scale: Double)
	    {
//%
//% Driver for adaptive quadrature using Simpson's rule.
//%
val t1 = new Array[Double]((1..1)*(1..6), clock());
var mc_t9: Double = 1;
var a: Double = (-mc_t9);
var b: Double = 6;
var sz_guess: Double = 1;
var tol: Double = 4e-13;
var mc_t10: Double = 1;
	    for (i = mc_t10;
;(i > scale);i = (i + 1))
	        {
val generate_a_name = new Array[Any](0..2, adapt(a, b, sz_guess, tol));
}
val t2 = new Array[Double]((1..1)*(1..6), clock());
//% Compute the running time in seconds
val mc_t0 = new Array[Double]((1..1)*(1..6), (t2 - t1));
var mc_t12: Double = 0;
var mc_t13: Double = 0;
var mc_t14: Double = 86400;
var mc_t15: Double = 3600;
var mc_t16: Double = 60;
var mc_t17: Double = 1;
val mc_t2 = new Array[Double]((1..1)*(1..6), horzcat(mc_t12, mc_t13, mc_t14, mc_t15, mc_t16, mc_t17));
val mc_t1 = new Array[Double]((1..6)*(1..1), .'(mc_t2));
var time: Double = *(mc_t0, mc_t1);
//% Store the benchmark output
val mc_t8 = new Array[Double]((1..null)*(1..6), SRmat());
var mc_t3: Double = mean(mc_t8);
var mc_t7: Double = quad();
var mc_t4: Double = mean(mc_t7);
var mc_t6: Double = err();
var mc_t5: Double = mean(mc_t6);
var output: Double = cellhorzcat(mc_t3, mc_t4, mc_t5);
//% No validation performed
val valid = new Array[String]((1..1)*(1..3), 'N/A');
}

}	static def adapt (a: Double, b: Double, sz_guess: Double, tol: Double)
	    {
//%-----------------------------------------------------------------------
//%
//%	This function M-file finds the adaptive quadrature using
//%	Simpson's rule.
//%
//%	This MATLAB program is intended as a pedagogical example.
//%
//%	Invocation:
//%		>> [SRmat, quad, err] = adapt(a, b, sz_guess, tol)
//%
//%		where
//%
//%		i. a is the left endpoint of [a, b],
//%
//%		i. b is the right endpoint of [a, b],
//%
//%		i. sz_guess is the number of rows in SRmat,
//%
//%		i. tol is the convergence tolerance,
//%
//%		o. SRmat is the matrix of adaptive Simpson
//%		   quadrature values,
//%
//%		o. quad is the adaptive Simpson quadrature,
//%
//%		o. err is the error estimate.
//%
//%	Requirements:
//%		a <= b.
//%
//%	Examples:
//%		>> [SRmat, quad, err] = adapt(-1, 6, 1, 1.0000e-12)
//%
//%	Source:
//%		Numerical Methods: MATLAB Programs,
//%		(c) John H. Mathews, 1995.
//%
//%		Accompanying text:
//%		Numerical Methods for Mathematics, Science and
//%		Engineering, 2nd Edition, 1992.
//%
//%		Prentice Hall, Englewood Cliffs,
//%		New Jersey, 07632, USA.
//%
//%		Also part of the FALCON project.
//%
//%	Author:
//%		John H. Mathews (mathews@fullerton.edu).
//%
//%	Date:
//%		March 1995.
//%
//%-----------------------------------------------------------------------
var mc_t151: Double = 6;
val SRmat = new Array[Double]((1..1)*(1..6), zeros(sz_guess, mc_t151));
var iterating: Double = 0;
var done: Double = 1;
var mc_t24: Double = (b - a);
var mc_t152: Double = 2;
var h: Double = /(mc_t24, mc_t152);
//% The step size.
var mc_t25: Double = (a + b);
var mc_t153: Double = 2;
var c: Double = /(mc_t25, mc_t153);
//% The midpoint in the interval.
//% The integrand is f(x) = 13.*(x-x.^2).*exp(-3.*x./2).
var mc_t33: Double = a;
var mc_t154: Double = 2;
var mc_t34: Double = .^(a, mc_t154);
var mc_t32: Double = (mc_t33 - mc_t34);
var mc_t155: Double = 13;
var mc_t26: Double = .*(mc_t155, mc_t32);
var mc_t156: Double = 3;
var mc_t30: Double = (-mc_t156);
var mc_t31: Double = a;
var mc_t29: Double = .*(mc_t30, mc_t31);
var mc_t157: Double = 2;
var mc_t28: Double = ./(mc_t29, mc_t157);
var mc_t27: Double = exp(mc_t28);
var Fa: Double = .*(mc_t26, mc_t27);
var mc_t42: Double = c;
var mc_t158: Double = 2;
var mc_t43: Double = .^(c, mc_t158);
var mc_t41: Double = (mc_t42 - mc_t43);
var mc_t159: Double = 13;
var mc_t35: Double = .*(mc_t159, mc_t41);
var mc_t160: Double = 3;
var mc_t39: Double = (-mc_t160);
var mc_t40: Double = c;
var mc_t38: Double = .*(mc_t39, mc_t40);
var mc_t161: Double = 2;
var mc_t37: Double = ./(mc_t38, mc_t161);
var mc_t36: Double = exp(mc_t37);
var Fc: Double = .*(mc_t35, mc_t36);
var mc_t51: Double = b;
var mc_t162: Double = 2;
var mc_t52: Double = .^(b, mc_t162);
var mc_t50: Double = (mc_t51 - mc_t52);
var mc_t163: Double = 13;
var mc_t44: Double = .*(mc_t163, mc_t50);
var mc_t164: Double = 3;
var mc_t48: Double = (-mc_t164);
var mc_t49: Double = b;
var mc_t47: Double = .*(mc_t48, mc_t49);
var mc_t165: Double = 2;
var mc_t46: Double = ./(mc_t47, mc_t165);
var mc_t45: Double = exp(mc_t46);
var Fb: Double = .*(mc_t44, mc_t45);
var mc_t54: Double = h;
var mc_t58: Double = Fa;
var mc_t166: Double = 4;
var mc_t59: Double = *(mc_t166, Fc);
var mc_t56: Double = (mc_t58 + mc_t59);
var mc_t57: Double = Fb;
var mc_t55: Double = (mc_t56 + mc_t57);
var mc_t53: Double = *(mc_t54, mc_t55);
var mc_t167: Double = 3;
var S: Double = /(mc_t53, mc_t167);
//% Simpson's rule.
val SRvec = new Array[Double]((1..1)*(1..6), horzcat(a, b, S, S, tol, tol));
var mc_t169: Double = 1;
var mc_t170: Double = 6;
val mc_t21 = new Array[Double]((1..1)*(1..6), :(mc_t169, mc_t170));
var mc_t171: Double = 1;
	    SRmat(mc_t171, mc_t21) = SRvec ;
var m: Double = 1;
var state: Double = iterating;
var mc_t230: Boolean = ==(state, iterating);
	    while (mc_t230)
	       {
var n: Double = m;
var mc_t144: Double = n;
var mc_t172: Double = 1;
var mc_t145: Double = (-mc_t172);
var mc_t229: Double = 1;
	       for (l = mc_t144;
;(l > mc_t229);l = (l + 1))
	           {
var p: Double = l;
val SR0vec = new Array[Double]((1..1)*(1..6), SRmat(p, ));
var mc_t173: Double = 5;
var err: Double = SR0vec(mc_t173);
var mc_t174: Double = 6;
tol = SR0vec(mc_t174);
var mc_t228: Boolean = (tol <= err);
	           inside if else if (mc_t228)
	               {
state = done;
val SR1vec = new Array[Double]((1..1)*(1..6), SR0vec);
val SR2vec = new Array[Double]((1..1)*(1..6), SR0vec);
var mc_t175: Double = 1;
a = SR0vec(mc_t175);
//% Left endpoint.
var mc_t176: Double = 2;
b = SR0vec(mc_t176);
//% Right endpoint.
var mc_t60: Double = (a + b);
var mc_t177: Double = 2;
c = /(mc_t60, mc_t177);
//% Midpoint.
var mc_t178: Double = 5;
err = SR0vec(mc_t178);
var mc_t179: Double = 6;
tol = SR0vec(mc_t179);
var mc_t180: Double = 2;
var tol2: Double = /(tol, mc_t180);
var a0: Double = a;
var b0: Double = c;
var tol0: Double = tol2;
var mc_t61: Double = (b0 - a0);
var mc_t181: Double = 2;
h = /(mc_t61, mc_t181);
var mc_t62: Double = (a0 + b0);
var mc_t182: Double = 2;
var c0: Double = /(mc_t62, mc_t182);
//% The integrand is f(x) = 13.*(x-x.^2).*exp(-3.*x./2).
var mc_t70: Double = a0;
var mc_t183: Double = 2;
var mc_t71: Double = .^(a0, mc_t183);
var mc_t69: Double = (mc_t70 - mc_t71);
var mc_t184: Double = 13;
var mc_t63: Double = .*(mc_t184, mc_t69);
var mc_t185: Double = 3;
var mc_t67: Double = (-mc_t185);
var mc_t68: Double = a0;
var mc_t66: Double = .*(mc_t67, mc_t68);
var mc_t186: Double = 2;
var mc_t65: Double = ./(mc_t66, mc_t186);
var mc_t64: Double = exp(mc_t65);
Fa = .*(mc_t63, mc_t64);
var mc_t79: Double = c0;
var mc_t187: Double = 2;
var mc_t80: Double = .^(c0, mc_t187);
var mc_t78: Double = (mc_t79 - mc_t80);
var mc_t188: Double = 13;
var mc_t72: Double = .*(mc_t188, mc_t78);
var mc_t189: Double = 3;
var mc_t76: Double = (-mc_t189);
var mc_t77: Double = c0;
var mc_t75: Double = .*(mc_t76, mc_t77);
var mc_t190: Double = 2;
var mc_t74: Double = ./(mc_t75, mc_t190);
var mc_t73: Double = exp(mc_t74);
Fc = .*(mc_t72, mc_t73);
var mc_t88: Double = b0;
var mc_t191: Double = 2;
var mc_t89: Double = .^(b0, mc_t191);
var mc_t87: Double = (mc_t88 - mc_t89);
var mc_t192: Double = 13;
var mc_t81: Double = .*(mc_t192, mc_t87);
var mc_t193: Double = 3;
var mc_t85: Double = (-mc_t193);
var mc_t86: Double = b0;
var mc_t84: Double = .*(mc_t85, mc_t86);
var mc_t194: Double = 2;
var mc_t83: Double = ./(mc_t84, mc_t194);
var mc_t82: Double = exp(mc_t83);
Fb = .*(mc_t81, mc_t82);
var mc_t91: Double = h;
var mc_t95: Double = Fa;
var mc_t195: Double = 4;
var mc_t96: Double = *(mc_t195, Fc);
var mc_t93: Double = (mc_t95 + mc_t96);
var mc_t94: Double = Fb;
var mc_t92: Double = (mc_t93 + mc_t94);
var mc_t90: Double = *(mc_t91, mc_t92);
var mc_t196: Double = 3;
S = /(mc_t90, mc_t196);
//% Simpson's rule.
SR1vec = horzcat(a0, b0, S, S, tol0, tol0);
a0 = c;
b0 = b;
tol0 = tol2;
var mc_t97: Double = (b0 - a0);
var mc_t198: Double = 2;
h = /(mc_t97, mc_t198);
var mc_t98: Double = (a0 + b0);
var mc_t199: Double = 2;
c0 = /(mc_t98, mc_t199);
//% The integrand is f(x) = 13.*(x-x.^2).*exp(-3.*x./2).
var mc_t106: Double = a0;
var mc_t200: Double = 2;
var mc_t107: Double = .^(a0, mc_t200);
var mc_t105: Double = (mc_t106 - mc_t107);
var mc_t201: Double = 13;
var mc_t99: Double = .*(mc_t201, mc_t105);
var mc_t202: Double = 3;
var mc_t103: Double = (-mc_t202);
var mc_t104: Double = a0;
var mc_t102: Double = .*(mc_t103, mc_t104);
var mc_t203: Double = 2;
var mc_t101: Double = ./(mc_t102, mc_t203);
var mc_t100: Double = exp(mc_t101);
Fa = .*(mc_t99, mc_t100);
var mc_t115: Double = c0;
var mc_t204: Double = 2;
var mc_t116: Double = .^(c0, mc_t204);
var mc_t114: Double = (mc_t115 - mc_t116);
var mc_t205: Double = 13;
var mc_t108: Double = .*(mc_t205, mc_t114);
var mc_t206: Double = 3;
var mc_t112: Double = (-mc_t206);
var mc_t113: Double = c0;
var mc_t111: Double = .*(mc_t112, mc_t113);
var mc_t207: Double = 2;
var mc_t110: Double = ./(mc_t111, mc_t207);
var mc_t109: Double = exp(mc_t110);
Fc = .*(mc_t108, mc_t109);
var mc_t124: Double = b0;
var mc_t208: Double = 2;
var mc_t125: Double = .^(b0, mc_t208);
var mc_t123: Double = (mc_t124 - mc_t125);
var mc_t209: Double = 13;
var mc_t117: Double = .*(mc_t209, mc_t123);
var mc_t210: Double = 3;
var mc_t121: Double = (-mc_t210);
var mc_t122: Double = b0;
var mc_t120: Double = .*(mc_t121, mc_t122);
var mc_t211: Double = 2;
var mc_t119: Double = ./(mc_t120, mc_t211);
var mc_t118: Double = exp(mc_t119);
Fb = .*(mc_t117, mc_t118);
var mc_t127: Double = h;
var mc_t131: Double = Fa;
var mc_t212: Double = 4;
var mc_t132: Double = *(mc_t212, Fc);
var mc_t129: Double = (mc_t131 + mc_t132);
var mc_t130: Double = Fb;
var mc_t128: Double = (mc_t129 + mc_t130);
var mc_t126: Double = *(mc_t127, mc_t128);
var mc_t213: Double = 3;
S = /(mc_t126, mc_t213);
//% Simpson's rule.
SR2vec = horzcat(a0, b0, S, S, tol0, tol0);
var mc_t215: Double = 3;
var mc_t137: Double = SR0vec(mc_t215);
var mc_t216: Double = 3;
var mc_t138: Double = SR1vec(mc_t216);
var mc_t135: Double = (mc_t137 - mc_t138);
var mc_t217: Double = 3;
var mc_t136: Double = SR2vec(mc_t217);
var mc_t134: Double = (mc_t135 - mc_t136);
var mc_t133: Double = abs(mc_t134);
var mc_t218: Double = 10;
err = /(mc_t133, mc_t218);
var mc_t227: Boolean = (err > tol);
	               inside if else if (mc_t227)
	                   {
	                   SRmat(p, ) = SR0vec ;
var mc_t219: Double = 3;
var mc_t139: Double = SR1vec(mc_t219);
var mc_t220: Double = 3;
var mc_t140: Double = SR2vec(mc_t220);
var mc_t19: Double = (mc_t139 + mc_t140);
var mc_t221: Double = 4;
	                   SRmat(p, mc_t221) = mc_t19 ;
var mc_t222: Double = 5;
	                   SRmat(p, mc_t222) = err ;
}
else 
	                   {
var mc_t141: Double = :(p, m);
val mc_t20 = new Array[Double]((1..null)*(1..6), SRmat(mc_t141, ));
var mc_t223: Double = 1;
var mc_t142: Double = (p + mc_t223);
var mc_t224: Double = 1;
var mc_t143: Double = (m + mc_t224);
var mc_t22: Double = :(mc_t142, mc_t143);
	                   SRmat(mc_t22, ) = mc_t20 ;
var mc_t225: Double = 1;
m = (m + mc_t225);
	                   SRmat(p, ) = SR1vec ;
var mc_t226: Double = 1;
var mc_t23: Double = (p + mc_t226);
	                   SRmat(mc_t23, ) = SR2vec ;
state = iterating;
}
}
else 
	               {
}
}
mc_t230 = ==(state, iterating);
}
var mc_t231: Double = 4;
var mc_t146: Double = SRmat(, mc_t231);
var quad: Double = sum(mc_t146);
var mc_t232: Double = 5;
var mc_t148: Double = SRmat(, mc_t232);
var mc_t147: Double = abs(mc_t148);
err = sum(mc_t147);
var mc_t233: Double = 1;
var mc_t149: Double = :(mc_t233, m);
var mc_t234: Double = 1;
var mc_t235: Double = 6;
val mc_t150 = new Array[Double]((1..1)*(1..6), :(mc_t234, mc_t235));
SRmat = SRmat(mc_t149, mc_t150);
}

}