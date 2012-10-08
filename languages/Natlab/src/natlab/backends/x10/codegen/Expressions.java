package natlab.backends.x10.codegen;

import natlab.backends.x10.IRx10.ast.*;
import natlab.backends.x10.IRx10.ast.List;
import natlab.tame.tir.TIRAbstractAssignStmt;
import ast.*;

public class Expressions {
	
	static Exp getOperand1(ast.Expr OpExp)
	{
		return makeIRx10Exp((Expr) (OpExp.getChild(1).getNumChild()>=1?
				OpExp.getChild(1).getChild(0):null));
	}

	static Exp getOperand2(ast.Expr OpExp)
	{
		return makeIRx10Exp((Expr) (OpExp.getChild(1).getNumChild()>=2?
				OpExp.getChild(1).getChild(1):null));
	}
	
	static int getRHSType(ast.Expr nodeExp) {
		String RHSName;
		RHSName = nodeExp.getVarName();
		if (true == x10Mapping.isBinOperator(RHSName)) {
			return 1; // "binop";
		} else if (true == x10Mapping.isUnOperator(RHSName)) {
			return 2; // "unop";
		}

		else if (true == x10Mapping.isX10DirectBuiltin(RHSName)) {
			return 3; // "builtin";
		} else if (true == x10Mapping.isMethod(RHSName)) {
			return 4; // "method";
		} else if (true == x10Mapping.isBuiltinConst(RHSName)) {
			return 5; // "builtin";
		} else {
			return 0; // "default";
		}
	}
	
	
	static String getRHSExp(Expr natlabExp) {
		String RHS = null;
		String RHSName;
		RHSName = natlabExp.getVarName();
		if (true == x10Mapping.isBinOperator(RHSName)) {
			RHS = x10Mapping.getX10BinOpMapping(RHSName);
		} else if (true == x10Mapping.isUnOperator(RHSName)) {
			RHS = x10Mapping.getX10UnOpMapping(RHSName);
		}

		else if (true == x10Mapping.isX10DirectBuiltin(RHSName)) {
			RHS = x10Mapping.getX10DirectBuiltinMapping(RHSName);
		} else if (true == x10Mapping.isBuiltinConst(RHSName)) {
			RHS = x10Mapping.getX10BuiltinConstMapping(RHSName);
		} else if (true == x10Mapping.isMethod(RHSName)) {
			RHS = x10Mapping.getX10MethodMapping(RHSName);
		} else {
			RHS = "//cannot process it yet";
		}
		return RHS;
	}
	
	static List<Exp> getArgs(ast.Expr NatlabExp)
	{
		List<Exp> Args = new List<Exp>();
		int numArgs = NatlabExp.getChild(1).getNumChild();
		for (int i = 0; i < numArgs; i++) {
			Args.add(makeIRx10Exp((ast.Expr)NatlabExp.getChild(1).getChild(i)));
		}
		return Args;
	}
	
	static Exp makeIRx10Exp(ast.Expr NatlabExp) {
		// Handle Binary Expressions
		
		//System.out.println("^^"+NatlabExp.getPrettyPrinted()+"^^");
		
		if (NatlabExp instanceof IntLiteralExpr){
			return new IntLiteral(NatlabExp.getNodeString());
		}
		if (NatlabExp instanceof FPLiteralExpr){
			return new FPLiteral(NatlabExp.getNodeString());
		}
		if (NatlabExp instanceof StringLiteralExpr){
			return new StringLiteral(NatlabExp.getNodeString());
		}
		else if (NatlabExp instanceof LiteralExpr)
		{
			return new Literal(NatlabExp.getNodeString());
		}
		if (NatlabExp instanceof NameExpr){
			//System.out.println(((NameExpr) NatlabExp).getName().getID()+"~~~");
			return new IDUse(((NameExpr) NatlabExp).getName().getID());
			
			
		}
			
		int RHStype;
		String RHS;
		Exp Operand1=null, Operand2=null;
		RHStype = getRHSType(NatlabExp);
		RHS = getRHSExp(NatlabExp);
		
		
//		if (NatlabExp instanceof UnaryExpr)
//		{
//		Operand1 = makeIRx10Exp(((UnaryExpr) NatlabExp).getOperand());
//		Operand2 = null;
//		}
//		else if (NatlabExp instanceof BinaryExpr)
//		{
//			Operand1 = makeIRx10Exp(((BinaryExpr) NatlabExp).getLHS());
//			Operand2 = makeIRx10Exp(((BinaryExpr) NatlabExp).getRHS());
//		}
		
		List<Exp> Args = new List<Exp>();
				
		
		
		
		switch (RHStype)
		{
		case 1: //Binary expression
		{   
			Operand1 = getOperand1(NatlabExp);
			Operand2 = getOperand2(NatlabExp);
			if(RHS.equals("+"))
				return new AddExp(Operand1, Operand2);
			if(RHS.equals("&"))
				return new AndExp(Operand1, Operand2);
			if(RHS.equals("|"))
				return new OrExp(Operand1, Operand2);
			if(RHS.equals("<"))
				return new LTExp(Operand1, Operand2);
			if(RHS.equals(">"))
				return new GTExp(Operand1, Operand2);
			if(RHS.equals("<="))
				return new LEExp(Operand1, Operand2);
			if(RHS.equals(">="))
				return new GEExp(Operand1, Operand2);
			if(RHS.equals("!="))
				return new NEExp(Operand1, Operand2);
			if(RHS.equals("-"))
				return new SubExp(Operand1, Operand2);
		}
		case 2: //Unary expressions
		{
			if(RHS.equals("+"))
				return new PlusExp(Operand1);
			if(RHS.equals("-"))
				return new MinusExp(Operand1);
			if(RHS.equals("!"))
				return new NegExp(Operand1);
		}
		case 3: //Builtins
		{
			Args = getArgs(NatlabExp);
			BuiltinMethodCall libCall =new BuiltinMethodCall();
			libCall.setBuiltinMethodName(new MethodId(RHS));
			libCall.setArgumentList(Args);
			return libCall;
			
		}
		case 4: //user def methods
		{
			Args = getArgs(NatlabExp);
			UserDefMethodCall libCall =new UserDefMethodCall();
			libCall.setUserDefMethodName(new MethodId(RHS));
			libCall.setArgumentList(Args);
			return libCall;
			
		}
		
		case 5: //built in constants 
		{
				return new Literal(RHS);
		}
			
		default:
		{
			return new Literal("//TODO;");
		}
			
		}
			
		
//		if (NatlabExp instanceof ast.BinaryExpr) {
//
//			// RELATIONAL EXPRESSIONS
//			if (NatlabExp instanceof ast.AndExpr) {
//				return new AndExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.OrExpr){
//				return new OrExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.LTExpr){
//				return new LTExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.GTExpr){
//				return new GTExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.LEExpr){
//				return new LEExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.GEExpr){
//				return new GEExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.EQExpr){
//				return new EQExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.NEExpr){
//				return new NEExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.PlusExpr){
//				return new AddExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.MinusExpr){
//				return new SubExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			else if (NatlabExp instanceof ast.MinusExpr){
//				return new SubExp(
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getLHS()),
//						makeIRx10Exp(((ast.BinaryExpr) NatlabExp).getRHS()));
//			}
//			
//			/**
//			*TODO M and E Multiplicative expressions
//			*/
//			
//			
//			
//		}
//		// Handle Unary Expressions
//		else if (NatlabExp instanceof ast.UnaryExpr) {
//			if (NatlabExp instanceof ast.UPlusExpr) {
//				return new PlusExp(
//						makeIRx10Exp(((ast.UnaryExpr) NatlabExp).getOperand()));
//			}
//			else if (NatlabExp instanceof ast.UMinusExpr) {
//				return new MinusExp(
//						makeIRx10Exp(((ast.UnaryExpr) NatlabExp).getOperand()));
//			}
//			else if (NatlabExp instanceof ast.NotExpr) {
//				return new NegExp(
//						makeIRx10Exp(((ast.UnaryExpr) NatlabExp).getOperand()));
//			}
//			
//
//		}
//		
//		//Handle Literal Expressions
//		else if (NatlabExp instanceof ast.LiteralExpr)
//		{
//			if (NatlabExp instanceof ast.IntLiteralExpr)
//				return new IntLiteral(((ast.LiteralExpr) NatlabExp).getNodeString());
//			else if (NatlabExp instanceof ast.FPLiteralExpr)
//				return new FPLiteral(((ast.LiteralExpr) NatlabExp).getNodeString());
//			else if (NatlabExp instanceof ast.StringLiteralExpr)
//				return new StringLiteral(((ast.LiteralExpr) NatlabExp).getNodeString());
//		}
//		/*
//		 * TODO add other instances of NatlabExp
//		 */

	}
}
