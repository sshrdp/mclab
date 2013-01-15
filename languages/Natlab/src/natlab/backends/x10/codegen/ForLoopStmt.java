package natlab.backends.x10.codegen;

import java.util.UUID;

import natlab.backends.x10.IRx10.ast.AssignStmt;
import natlab.backends.x10.IRx10.ast.DeclStmt;
import natlab.backends.x10.IRx10.ast.ForStmt;
import natlab.backends.x10.IRx10.ast.IDInfo;
import natlab.backends.x10.IRx10.ast.IDUse;
import natlab.backends.x10.IRx10.ast.IncExp;
import natlab.backends.x10.IRx10.ast.LTExp;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.LoopBody;
import natlab.backends.x10.IRx10.ast.Opt;
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

		IDInfo LHSinfo = new IDInfo(new Type("Int"), node.getAssignStmt()
				.getLHS().getVarName(), null, null, null);
		/*
		 * LHSinfo = Helper.generateIDInfo(target.analysis, target.index, node,
		 * node.getVarName());
		 */
		// System.out.println(LHSinfo.getName()+LHSinfo.getisComplex());

		for_assign.setLHS(LHSinfo);
		for_assign.getLHS().setName(node.getAssignStmt().getLHS().getVarName());
		IDUse lower = new IDUse(((RangeExpr) (node.getAssignStmt().getRHS()))
				.getLower().getVarName());
		IDUse upper = new IDUse(((RangeExpr) (node.getAssignStmt().getRHS()))
				.getUpper().getVarName());
		IDUse increment = new IDUse("1");
		/*
		 * uncomment below If after fixing the following TODO getIncr throws
		 * errors sometimes. Look into it and fix it ....till then using "1"
		 * temporarily
		 */
		// if(null != ((RangeExpr)(node.getAssignStmt().getRHS())).getIncr())
		// increment= new
		// IDUse(((RangeExpr)(node.getAssignStmt().getRHS())).getIncr().getVarName());//

		for_assign.setRHS(lower);
		for_stmt.setAssignStmt(for_assign);
		DeclStmt TempDeclStmt = new DeclStmt();
		TempDeclStmt.setLHS(for_assign.getLHS());
		block.addStmt(TempDeclStmt);
		target.symbolMap.put(LHSinfo.getName(), null);
		for_stmt.setCondition(new LTExp(
				new IDUse(for_assign.getLHS().getName()), upper));
		for_stmt.setStepper(new IncExp(
				new IDUse(for_assign.getLHS().getName()), increment));
		for_stmt.setLoopBody(new LoopBody(new List<Stmt>()));
		LoopBody loop_body_block = for_stmt.getLoopBody();
		target.currentBlock.add(loop_body_block);
		buildStmtsSubAST(node.getStmts(), target);
		target.currentBlock.remove(loop_body_block);

		block.addStmt(fixLoopVar(for_stmt, block));

		// System.out.println(loop_body_block.getStmts().getNumChild());
	}

	private static ForStmt fixLoopVar(ForStmt for_stmt, StmtBlock block) {

		boolean FixIt = false;
		for (Stmt stmt : for_stmt.getLoopBody().getStmtList()) {
			if (stmt instanceof AssignStmt
					&& ((AssignStmt) stmt).getLHS() != null) {
				if (((AssignStmt) stmt).getLHS().getName()
						.equals(for_stmt.getAssignStmt().getLHS().getName())) {
					FixIt = true;

				}
			}
		}
		if (FixIt) {
			// rename loop variable
			// assign old loop var to new one
			String randomizer = UUID.randomUUID().toString();
			DeclStmt InsertDeclStmt = new DeclStmt();
			IDInfo temp = for_stmt.getAssignStmt().getLHS();
			InsertDeclStmt.setLHS(new IDInfo(temp.getType(), temp.getName(),
					null, null, null));
			InsertDeclStmt.setRHS(new IDUse(for_stmt.getAssignStmt().getLHS()
					.getName()
					+ "_x10"+randomizer));
			for_stmt.getLoopBody().getStmtList().insertChild(InsertDeclStmt, 0);
			for_stmt.getAssignStmt()
					.getLHS()
					.setName(
							for_stmt.getAssignStmt().getLHS().getName()
									+ "_x10"+randomizer);
			((IDUse) (((LTExp) (for_stmt.getCondition())).getLeftOp()))
					.setID(for_stmt.getAssignStmt().getLHS().getName());
			((IDUse) (((IncExp) (for_stmt.getStepper())).getLeftOp()))
					.setID(for_stmt.getAssignStmt().getLHS().getName());
		}
		int i = 0;
		for (Stmt stmt : for_stmt.getLoopBody().getStmtList()) {
			// if a statement is Decl statement, add a declaration node to the
			// block
			// and change to assignment node inside for stmt
			if (stmt instanceof DeclStmt) {
				if (((DeclStmt) stmt).hasRHS()) {
					AssignStmt InsertAssignStmt = new AssignStmt();
					IDInfo temp = ((DeclStmt) stmt).getLHS();
					InsertAssignStmt.setLHS(temp);

					InsertAssignStmt.setRHS(((DeclStmt) stmt).getRHS());
					for_stmt.getLoopBody().getStmtList().insertChild(InsertAssignStmt, i);
				}
				block.addStmt(stmt);
				((DeclStmt) stmt).setChild(new Opt(), 2);
				for_stmt.getLoopBody().getStmtList().removeChild(i+1);
			}
			i++;
		}

		return for_stmt;
		// TODO Auto-generated method stub

	}

	private static void buildStmtsSubAST(ast.List<ast.Stmt> stmts,
			IRx10ASTGenerator target) {
		for (ast.Stmt stmt : stmts) {
			((TIRNode) stmt).tirAnalyze(target);
		}
	}
}
