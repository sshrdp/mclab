package natlab.backends.x10.codegen;

import java.util.ArrayList;

import ast.Name;
import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.MethodBlock;
import natlab.backends.x10.IRx10.ast.MethodHeader;
import natlab.backends.x10.IRx10.ast.Stmt;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRFunction;
import natlab.tame.tir.TIRNode;

public class Function {

	static void handleTIRFunction(TIRFunction node, IRx10ASTGenerator target) {
		ArrayList<String> inArgs = new ArrayList<String>();
		// TODO - CHANGE IT TO DETERMINE RETURN TYPE
		// TODO - change "Args" to "ARguments" to avoid name clash
		// TODO - change to Arguments* for MethodHeader
		List<Args> arguments = new List<Args>();
		MethodHeader method_header = new MethodHeader();
		method_header.setName(node.getName());
		method_header.setReturnType(null);

		for (Name param : node.getInputParams()) {

			arguments.add(new Args(target.x10Map.getX10TypeMapping(Helper
					.getArgumentType(target.analysis, target.index, node,
							param.getID())), param.getPrettyPrinted()));
			target.symbolMap.put(param.getID().toString(), Helper
					.getAnalysisValue(target.analysis, target.index, node,
							param.getID()));
		}
		method_header.setArgsList(arguments);
		MethodBlock method_block = new MethodBlock(new List<Stmt>());
		target.method.setMethodHeader(method_header);
		target.method.setMethodBlock(method_block);
		buildStmtsSubAST(node.getStmts(), target);

	}

	private static void buildStmtsSubAST(ast.List<ast.Stmt> stmts,
			IRx10ASTGenerator target) {
		for (ast.Stmt stmt : stmts) {
			((TIRNode) stmt).tirAnalyze(target);
		}
	}
}
