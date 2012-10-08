package natlab.backends.x10.codegen;

import natlab.backends.x10.IRx10.ast.AssignStmt;
import natlab.backends.x10.IRx10.ast.ForStmt;
import natlab.backends.x10.IRx10.ast.IDInfo;
import natlab.backends.x10.IRx10.ast.IDUse;
import natlab.backends.x10.IRx10.ast.IncExp;
import natlab.backends.x10.IRx10.ast.LTExp;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.LoopBody;
import natlab.backends.x10.IRx10.ast.Stmt;
import natlab.backends.x10.IRx10.ast.StmtBlock;
import natlab.backends.x10.IRx10.ast.Type;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRForStmt;
import natlab.tame.tir.TIRNode;
import ast.RangeExpr;

public class ForLoopStmt {
	public static void handleTIRForStmt(TIRForStmt node,
			IRx10ASTGenerator target, StmtBlock block) {

		ForStmt for_stmt = new ForStmt();
		AssignStmt for_assign = new AssignStmt();

		IDInfo LHSinfo = new IDInfo(new Type("Int"), node.getAssignStmt().getLHS().getVarName(), null, null, null);
		/*
		LHSinfo = Helper.generateIDInfo(target.analysis, target.index,
				node, node.getVarName());
		*/
		//System.out.println(LHSinfo.getName()+LHSinfo.getisComplex());
		
		for_assign.setLHS(LHSinfo);
		for_assign.getLHS().setName(node.getAssignStmt().getLHS().getVarName());
		IDUse lower = new IDUse(((RangeExpr)(node.getAssignStmt().getRHS())).getLower().getVarName());
		IDUse upper = new IDUse(((RangeExpr)(node.getAssignStmt().getRHS())).getUpper().getVarName());
		IDUse increment = new IDUse("1");
		/**
		 * uncomment below If after fixing the following
		 * TODO
		 * getIncr throws errors sometimes. Look into it and fix it ....till then using "1" temporarily		
		 */
		//if(null != ((RangeExpr)(node.getAssignStmt().getRHS())).getIncr())
		//	increment= new IDUse(((RangeExpr)(node.getAssignStmt().getRHS())).getIncr().getVarName());//
		
			
		for_assign.setRHS(lower);
		for_stmt.setAssignStmt(for_assign);		

		for_stmt.setCondition(new LTExp(new IDUse(for_assign.getLHS().getName()), upper));
		for_stmt.setStepper(new IncExp(new IDUse(for_assign.getLHS().getName()),increment));
		for_stmt.setLoopBody(new LoopBody(new List<Stmt>()));
		LoopBody loop_body_block = for_stmt.getLoopBody();
		target.currentBlock.add(loop_body_block);
		buildStmtsSubAST(node.getStmts(), target);
		target.currentBlock.remove(loop_body_block);
		block.addStmt(for_stmt);

		System.out.println(loop_body_block.getStmts().getNumChild());
	}

	private static void buildStmtsSubAST(ast.List<ast.Stmt> stmts,
			IRx10ASTGenerator target) {
		for (ast.Stmt stmt : stmts) {
			((TIRNode) stmt).tirAnalyze(target);
		}
	}
}
