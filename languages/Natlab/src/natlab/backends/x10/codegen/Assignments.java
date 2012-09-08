package natlab.backends.x10.codegen;

import java.util.ArrayList;
import java.util.Collection;
import natlab.backends.x10.IRx10.ast.AssignStmt;
import natlab.backends.x10.IRx10.ast.Type;
import natlab.tame.classes.reference.ClassReference;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRAbstractAssignToListStmt;
import natlab.tame.tir.TIRAbstractAssignToVarStmt;
import natlab.tame.tir.TIRFunction;
import natlab.tame.valueanalysis.ValueAnalysis;

public class Assignments {

	public static void handleTIRAbstractAssignToVarStmt(
			TIRAbstractAssignStmt node, IRx10ASTGenerator target) {
		String LHS;
		target.symbolMapKey = ((TIRAbstractAssignToVarStmt) node)
				.getTargetName().getID();
		LHS = target.symbolMapKey;
		AssignStmt assign_stmt = new AssignStmt();
		if (true == target.symbolMap.containsKey(target.symbolMapKey)) // variable
																		// already
																		// defined
																		// and
																		// analyzed
		{

			assign_stmt.setVariableName(((TIRAbstractAssignToVarStmt) node)
					.getTargetName().toString());
			assign_stmt.setValue(null);
			// TODO : Handle expressions of various types
			// Set parent's value in various expressions
		} else {

			Type type = target.x10Map.getX10TypeMapping(Helper.getLHSType(
					target.analysis, target.index, node, LHS));

			assign_stmt.setVariableName(node.getLHS().getNodeString());
			assign_stmt.setType(type);
			assign_stmt.setValue(null);

			// TODO check for expression on RHS
			// TODO check for built-ins
			// TODO check for operators
			// add to symbol Map
			target.symbolMap
					.put(node.getLHS().getNodeString(), Helper
							.getAnalysisValue(target.analysis, target.index,
									node, LHS));

		}
		target.method.getMethodBlock().addStmt(assign_stmt);
	}

	public static void handleTIRAbstractAssignToListStmt(
			TIRAbstractAssignStmt node, IRx10ASTGenerator target) {
		// TODO Auto-generated method stub
		System.out.println("list assignment");
		String LHS;
		ArrayList<ast.Name> lhsVars = new ArrayList<ast.Name>();
		for (ast.Name name : ((TIRAbstractAssignToListStmt) node).getTargets()
				.asNameList()) {
			handleTIRAbstractAssignToListVarStmt(node, name,
					((TIRAbstractAssignToListStmt) node).getRHS(), target);
		}
	}

	// This version handles assignment to multiple variables
	public static void handleTIRAbstractAssignToListVarStmt(
			TIRAbstractAssignStmt node, ast.Name name, ast.Expr rhs_expr,
			IRx10ASTGenerator target) {
		String LHS;
		target.symbolMapKey = name.getID();
		LHS = target.symbolMapKey;
		AssignStmt assign_stmt = new AssignStmt();
		if (true == target.symbolMap.containsKey(LHS)) // variable already
														// defined and analyzed
		{

			assign_stmt.setVariableName(name.getNodeString());
			assign_stmt.setValue(null); // Set RHS expression here
			// TODO : Handle expressions of various types
			// Set parent's value in various expressions
		} else {

			Type type = target.x10Map.getX10TypeMapping(Helper.getLHSType(
					target.analysis, target.index, node, LHS));
			assign_stmt.setVariableName(name.getNodeString());
			assign_stmt.setType(type);
			assign_stmt.setValue(null);
			target.method.getMethodBlock().addStmt(assign_stmt);
			// TODO check for expression on RHS
			// TODO check for built-ins
			// TODO check for operators
			// add to symbol Map
			target.symbolMap
					.put(node.getLHS().getNodeString(), Helper
							.getAnalysisValue(target.analysis, target.index,
									node, LHS));

		}

	}

}
