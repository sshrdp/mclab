package natlab.backends.x10.codegen;

import natlab.backends.x10.IRx10.ast.*;
import ast.BinaryExpr;
import ast.Expr;
import ast.LTExpr;

public class Expressions {

	static Exp makeIRx10Exp(ast.Expr NatlabExp) {
		// Handle Binary Expressions
		if (NatlabExp instanceof ast.BinaryExpr) {

			// RELATIONAL EXPRESSIONS
			if (NatlabExp instanceof ast.AndExpr) {
				return new AndExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.OrExpr){
				return new OrExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.LTExpr){
				return new LTExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.GTExpr){
				return new GTExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.LEExpr){
				return new LEExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.GEExpr){
				return new GEExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.EQExpr){
				return new EQExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.NEExpr){
				return new NEExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.PlusExpr){
				return new AddExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.MinusExpr){
				return new SubExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			else if (NatlabExp instanceof ast.MinusExpr){
				return new SubExp(
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
			}
			
			/**
			*TODO M and E Multiplicative expressions
			*/
			
			
			
		}
		// Handle Unary Expressions
		else if (NatlabExp instanceof ast.UnaryExpr) {
			if (NatlabExp instanceof ast.UPlusExpr) {
				return new PlusExp(
						makeIRx10Exp(((ast.UnaryExpr) NatlabExp).getOperand()));
			}
			else if (NatlabExp instanceof ast.UMinusExpr) {
				return new MinusExp(
						makeIRx10Exp(((ast.UnaryExpr) NatlabExp).getOperand()));
			}
			else if (NatlabExp instanceof ast.NotExpr) {
				return new NegExp(
						makeIRx10Exp(((ast.UnaryExpr) NatlabExp).getOperand()));
			}
			

		}
		
		//Handle Literal Expressions
		else if (NatlabExp instanceof ast.LiteralExpr)
		{
			if (NatlabExp instanceof ast.IntLiteralExpr)
				return new IntLiteral(((ast.LiteralExpr) NatlabExp).getNodeString());
			else if (NatlabExp instanceof ast.FPLiteralExpr)
				return new FPLiteral(((ast.LiteralExpr) NatlabExp).getNodeString());
			else if (NatlabExp instanceof ast.StringLiteralExpr)
				return new StringLiteral(((ast.LiteralExpr) NatlabExp).getNodeString());
		}
		/*
		 * TODO add other instances of NatlabExp
		 */
		
		return null;
	}
}
