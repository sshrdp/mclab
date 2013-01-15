package natlab.backends.x10.codegen;

import natlab.backends.x10.IRx10.ast.ElseBody;
import natlab.backends.x10.IRx10.ast.IfBody;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.LoopBody;
import natlab.backends.x10.IRx10.ast.StmtBlock;
import natlab.backends.x10.IRx10.ast.IfElseIf;
import java.util.ArrayList;

import natlab.tame.tir.TIRIfStmt;
import natlab.tame.tir.TIRNode;

public class IfElseStmt {
	public static void handleTIRIfStmt(TIRIfStmt node, IRx10ASTGenerator target, StmtBlock block){
		List<IfElseIf> if_else_if = new List<IfElseIf>();
		ElseBody else_body = new ElseBody();
		natlab.backends.x10.IRx10.ast.IfElseStmt if_else_stmt= new natlab.backends.x10.IRx10.ast.IfElseStmt();
		if_else_stmt.setIfElseIfList(if_else_if);
		if_else_stmt.setElseBody(else_body);
		
		System.out.println("inside If-else");
		for (ast.IfBlock if_block : node.getIfBlockList()){
			IfElseIf temp = new IfElseIf();
			
			System.out.println(if_block.getCondition().getPrettyPrinted());
			
			temp.setCondition(Expressions.makeIRx10Exp(if_block.getCondition(), true, target));//CHECK IT
			temp.setIfBody(new IfBody());
			
			IfBody loop_body_block = temp.getIfBody();
			target.currentBlock.add(loop_body_block);
			buildStmtsSubAST(if_block.getStmts(), target);
			target.currentBlock.remove(loop_body_block);
			//block.addStmt(while_stmt);
			if_else_stmt.getIfElseIfList().add(temp);
			/*TESTING			 */
			System.out.println("IF"+if_else_stmt.getIfElseIfList().getChild(0).getIfBody().getNumStmt());
		}
		
		target.currentBlock.add(if_else_stmt.getElseBody());
		buildStmtsSubAST(node.getElseBlock().getStmts(), target);
		target.currentBlock.remove(if_else_stmt.getElseBody());
		block.addStmt(if_else_stmt);
		
		System.out.println("Else"+if_else_stmt.getElseBody().getNumStmt());
		
		
}
	
	private static void buildStmtsSubAST(ast.List<ast.Stmt> stmts,
			IRx10ASTGenerator target) {
		for (ast.Stmt stmt : stmts) {
			((TIRNode) stmt).tirAnalyze(target);
		}
	}
}