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
			TIRAbstractAssignStmt node, IRx10ASTGenerator target,
			StmtBlock block) {
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
			// IDInfo LHSinfo = new IDInfo();
			// assign_stmt.setLHS(LHSinfo);
			assign_stmt.setLHS(Helper.generateIDInfo(target.analysis,
					target.index, node, LHS));
			assign_stmt.getLHS().setName(
					((TIRAbstractAssignToVarStmt) node).getTargetName().getID()
							.toString());
			setRHSValue(isDecl, assign_stmt, node);
			block.addStmt(assign_stmt);
			// TODO : Handle expressions of various types
			// Set parent's value in various expressions
		} else {
			isDecl = true;
			DeclStmt decl_stmt = new DeclStmt();
			IDInfo LHSinfo = new IDInfo();
			decl_stmt.setLHS(Helper.generateIDInfo(target.analysis,
					target.index, node, LHS));

			decl_stmt.getLHS().setName(((TIRAbstractAssignToVarStmt)node).getLHS().getVarName());
			setRHSValue(isDecl, decl_stmt, node);

			target.symbolMap
					.put(((TIRAbstractAssignToVarStmt)node).getLHS().getVarName(), Helper
							.getAnalysisValue(target.analysis, target.index,
									node, LHS));
			block.addStmt(decl_stmt);

		}

	}

	public static void setRHSValue(boolean isDecl, Stmt decl_or_assgn,
			TIRAbstractAssignStmt node) {
		if (isDecl) {
			((DeclStmt) decl_or_assgn).setRHS(Expressions.makeIRx10Exp(node
					.getRHS()));
		} else {
			((AssignStmt) decl_or_assgn).setRHS(Expressions.makeIRx10Exp(node
					.getRHS()));
		}

	}

	public static void handleTIRAbstractAssignToListStmt(
			TIRAbstractAssignStmt node, IRx10ASTGenerator target,
			StmtBlock block) {

		// Handle separately if only one variable in LHS
		if (1 == ((TIRAbstractAssignToListStmt) node).getTargets().asNameList()
				.size()) {
			String LHS;
			boolean isDecl;
			target.symbolMapKey = ((TIRAbstractAssignToListStmt) node)
					.getTargetName().getID();
			LHS = target.symbolMapKey;

			if (true == target.symbolMap.containsKey(target.symbolMapKey)) {
				isDecl = false;
				// IDInfo LHSinfo = new IDInfo();
				// assign_stmt.setLHS(LHSinfo);
				AssignStmt list_single_assign_stmt = new AssignStmt();
				list_single_assign_stmt.setLHS(Helper.generateIDInfo(target.analysis,
						target.index, node, LHS));
				list_single_assign_stmt.getLHS().setName(
						((TIRAbstractAssignToListStmt) node).getTargets().getChild(0).getVarName());
				setRHSValue(false, list_single_assign_stmt, node);
				block.addStmt(list_single_assign_stmt);

			} else {
				isDecl = true;
				DeclStmt decl_stmt = new DeclStmt();
				IDInfo LHSinfo = new IDInfo();
				decl_stmt.setLHS(Helper.generateIDInfo(target.analysis,
						target.index, node, LHS));

				decl_stmt.getLHS().setName(((TIRAbstractAssignToListStmt)node).getTargets().getChild(0).getVarName());
				setRHSValue(isDecl, decl_stmt, node);

				target.symbolMap.put(node.getLHS().getNodeString(), Helper
						.getAnalysisValue(target.analysis, target.index, node,
								LHS));
				block.addStmt(decl_stmt);

			}

		}

		else {
			AssignStmt list_assign_stmt = new AssignStmt();
			for (ast.Name name : ((TIRAbstractAssignToListStmt) node)
					.getTargets().asNameList()) {
				handleTIRAbstractAssignToListVarStmt(node, name, target,
						list_assign_stmt);
			}
			list_assign_stmt.setLHS(null);
			setRHSValue(false, list_assign_stmt, node);
			block.addStmt(list_assign_stmt);
		}

	}

	// This version handles assignment to multiple variables
	public static void handleTIRAbstractAssignToListVarStmt(
			TIRAbstractAssignStmt node, ast.Name name,
			IRx10ASTGenerator target, AssignStmt assign_stmt) {
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

		

	}

}
