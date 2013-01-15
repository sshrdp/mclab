package natlab.backends.x10.codegen;

import natlab.tame.tir.TIRNode;
import natlab.tame.tir.TIRWhileStmt;
import natlab.backends.x10.IRx10.ast.*;
public class WhileLoopStmt {
	
	public static void handleTIRWhileStmt(TIRWhileStmt node, IRx10ASTGenerator target, StmtBlock block) {
		WhileStmt while_stmt =  new WhileStmt();
		while_stmt.setCondition(Expressions.makeIRx10Exp(node.getExpr(), true, target));//check it
		
		System.out.println(while_stmt.getCondition().toString());
		while_stmt.setLoopBody(new LoopBody(new List<Stmt>()));
		LoopBody loop_body_block = while_stmt.getLoopBody();
		target.currentBlock.add(loop_body_block);
		buildStmtsSubAST(node.getStmts(), target);
		target.currentBlock.remove(loop_body_block);
		block.addStmt(while_stmt);
		
		System.out.println(loop_body_block.getStmts().getNumChild());
	}
	private static void buildStmtsSubAST(ast.List<ast.Stmt> stmts,
			IRx10ASTGenerator target) {
		for (ast.Stmt stmt : stmts) {
			((TIRNode) stmt).tirAnalyze(target);
		}
	}
}
