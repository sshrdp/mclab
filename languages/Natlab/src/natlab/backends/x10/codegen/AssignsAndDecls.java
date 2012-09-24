package natlab.backends.x10.codegen;

import java.util.ArrayList;

import natlab.backends.x10.IRx10.ast.*;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRAbstractAssignToListStmt;
import natlab.tame.tir.TIRAbstractAssignToVarStmt;
import natlab.tame.tir.TIRAbstractCreateFunctionHandleStmt;
import natlab.tame.tir.TIRAssignLiteralStmt;
import natlab.tame.tir.TIRCopyStmt;
import ast.FPLiteralExpr;
import ast.IntLiteralExpr;
import ast.StringLiteralExpr;

public class AssignsAndDecls {

	public static void handleTIRAbstractAssignToVarStmt(
			TIRAbstractAssignStmt node, IRx10ASTGenerator target, StmtBlock block) {
		boolean isDecl;
		String LHS;
		target.symbolMapKey = ((TIRAbstractAssignToVarStmt) node)
				.getTargetName().getID();
		LHS = target.symbolMapKey;
		/*
		 * If checks whether it is already defined, in which case it's
		 * assignment otherwise it's a declaration
		 */
		if (true == target.symbolMap.containsKey(target.symbolMapKey)) {
			isDecl = false;
			AssignStmt assign_stmt = new AssignStmt();
			IDInfo LHSinfo = new IDInfo();
			assign_stmt.setLHS(LHSinfo);
			assign_stmt.getLHS().setName(
					((TIRAbstractAssignToVarStmt) node).getTargetName()
							.toString());
			setRHSValue(isDecl, assign_stmt, node);
			block.addStmt(assign_stmt);
			// TODO : Handle expressions of various types
			// Set parent's value in various expressions
		} else {
			isDecl = true;
			DeclStmt decl_stmt = new DeclStmt();
			IDInfo LHSinfo = new IDInfo();
			decl_stmt.setLHS(LHSinfo);
			Type type = x10Mapping.getX10TypeMapping(Helper.getLHSType(
					target.analysis, target.index, node, LHS));

			decl_stmt.getLHS().setName(node.getLHS().getNodeString());
			decl_stmt.getLHS().setType(type);
			setRHSValue(isDecl, decl_stmt, node);

			// TODO check for expression on RHS
			// TODO check for built-ins
			// TODO check for operators
			// add to symbol Map
			target.symbolMap
					.put(node.getLHS().getNodeString(), Helper
							.getAnalysisValue(target.analysis, target.index,
									node, LHS));
			block.addStmt(decl_stmt);

		}

		/**
		 * Below if-elses are to identify separate RHS expressions TODO identify
		 * if these 3 cases cover all assignToVarstatements
		 */

	}

	public static void setRHSValue(boolean isDecl, Stmt decl_or_assgn,
			TIRAbstractAssignStmt node) {
		if (node instanceof TIRAssignLiteralStmt) {
			if (node.getRHS() instanceof IntLiteralExpr) {
				if (isDecl)
					((DeclStmt) decl_or_assgn).setRHS(new IntLiteral(node
							.getRHS().getNodeString()));
				else
					((AssignStmt) decl_or_assgn).setRHS(new IntLiteral(node
							.getRHS().getNodeString()));
			} else if (node.getRHS() instanceof FPLiteralExpr) {
				if (isDecl)
					((DeclStmt) decl_or_assgn).setRHS(new FPLiteral(node
							.getRHS().getNodeString()));
				else
					((AssignStmt) decl_or_assgn).setRHS(new FPLiteral(node
							.getRHS().getNodeString()));
			} else if (node.getRHS() instanceof StringLiteralExpr) {
				if (isDecl)
					((DeclStmt) decl_or_assgn).setRHS(new StringLiteral(node
							.getRHS().getNodeString()));
				else
					((AssignStmt) decl_or_assgn).setRHS(new StringLiteral(node
							.getRHS().getNodeString()));
			} else {
				if (isDecl)
					((DeclStmt) decl_or_assgn).setRHS(new Literal(node.getRHS()
							.getNodeString()));
				else
					((AssignStmt) decl_or_assgn).setRHS(new Literal(node
							.getRHS().getNodeString()));
			}

		}

		else if (node instanceof TIRCopyStmt) {
			String RHSid = node.getRHS().getNodeString();
			if (isDecl)
				((DeclStmt) decl_or_assgn).setRHS(new IDUse(RHSid));
			else
				((AssignStmt) decl_or_assgn).setRHS(new IDUse(RHSid));

		}

		else if (node instanceof TIRAbstractCreateFunctionHandleStmt) {
			// TODO
		}
	}

	public static void handleTIRAbstractAssignToListStmt(
			TIRAbstractAssignStmt node, IRx10ASTGenerator target, StmtBlock block) {
		// System.out.println("list assignment");
		new ArrayList<ast.Name>();
		AssignStmt list_assign_stmt = new AssignStmt();
		setRHSValue(false, list_assign_stmt, node);
		for (ast.Name name : ((TIRAbstractAssignToListStmt) node).getTargets()
				.asNameList()) {
			handleTIRAbstractAssignToListVarStmt(node, name, target, list_assign_stmt);
		}
		
		block.addStmt(list_assign_stmt);
		
	}

	// This version handles assignment to multiple variables
	public static void handleTIRAbstractAssignToListVarStmt(
			TIRAbstractAssignStmt node, ast.Name name, IRx10ASTGenerator target, AssignStmt assign_stmt) {
		String LHS;
		target.symbolMapKey = name.getID();
		LHS = target.symbolMapKey;
		/**
		 * As of now(sep 17, 2012) just create an assignment node with
		 * MultiAssignLHS and a single RHS Change if we really need declaration
		 * for such case OR just pretty print as declaration in X10 code
		 */

		/*
		 * if (true == target.symbolMap.containsKey(LHS)) // variable already //
		 * defined and analyzed {
		 */
		MultiAssignLHS LHSinfo = new MultiAssignLHS();
		assign_stmt.setMultiAssignLHS(LHSinfo);

		assign_stmt.getMultiAssignLHS()
				.addIDInfo(
						Helper.generateIDInfo(target.analysis, target.index,
								node, LHS));

		assign_stmt
				.getMultiAssignLHS()
				.getIDInfo(assign_stmt.getMultiAssignLHS().getNumIDInfo() - 1)
				.setName(
						((TIRAbstractAssignToListStmt) node).getTargetName()
								.toString());

		//assign_stmt.setRHS(null); // Set RHS expression here
		// TODO : Handle expressions of various types
		// Set parent's value in various expressions
		/*
		 * } else { DeclStmt decl_stmt = new DeclStmt(); IDInfo LHSinfo = new
		 * IDInfo(); decl_stmt.setLHS(LHSinfo); Type type =
		 * x10Mapping.getX10TypeMapping(Helper.getLHSType( target.analysis,
		 * target.index, node, LHS));
		 * 
		 * decl_stmt.getLHS().setName(node.getLHS().getNodeString());
		 * decl_stmt.getLHS().setType(type); decl_stmt.setRHS(null);
		 * MethodBlock.addStmt(decl_stmt); // TODO check for
		 * expression on RHS // TODO check for built-ins // TODO check for
		 * operators // add to symbol Map target.symbolMap
		 * .put(node.getLHS().getNodeString(), Helper
		 * .getAnalysisValue(target.analysis, target.index, node, LHS));
		 * 
		 * }
		 */

	}

}
