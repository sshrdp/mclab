package natlab.backends.x10.codegen;

import ast.ParameterizedExpr;
import natlab.backends.x10.IRx10.ast.*;
import natlab.tame.tir.TIRAbstractAssignToListStmt;
import natlab.tame.tir.TIRAbstractAssignToVarStmt;
import natlab.tame.tir.TIRArrayGetStmt;
import natlab.tame.tir.TIRArraySetStmt;

public class ArrayGetSet {

	public static void handleTIRAbstractArraySetStmt(TIRArraySetStmt node,
			IRx10ASTGenerator target, StmtBlock block) {

		System.out.println(node.getArrayName().getID());
		System.out.println(node.getRHS().getPrettyPrinted());
		//System.out.println(node.getTargetName().getID());

		String LHS;
		target.symbolMapKey = node.getArrayName().getID();
		LHS = target.symbolMapKey;
		
		
		
		
		if (true != target.symbolMap.containsKey(target.symbolMapKey)) {
			DeclStmt array_decl = new DeclStmt();
			IDInfo LHSinfo = new IDInfo();
			array_decl.setLHS(Helper.generateIDInfo(target.analysis,
					target.index, node, LHS));

			array_decl.getLHS().setName(LHS);
			array_decl.setRHS(new EmptyExp());
			target.symbolMap.put(target.symbolMapKey, Helper
					.getAnalysisValue(target.analysis, target.index, node,
							LHS));
			block.addStmt(array_decl);
			
		}
			/**
			 * The array has been declared before. This is just an assignment to
			 * its index. If not declared before first declare the array and
			 * then set the index
			 */
			ArraySetStmt array_set = new ArraySetStmt();
			array_set.setLHS(Helper.generateIDInfo(target.analysis,
					target.index, node, LHS));
			array_set.getLHS().setName(LHS.toString());
			
			array_set.getLHS()
					.setValue(
							new ArrayAccess(new IDUse(LHS), Expressions.getArgs(node.getLHS(), target))
							);
			
			boolean tf = true;
			if (null != array_set.getLHS().getShape())
			for (int i = 0; i < array_set.getLHS().getShape().size(); i++) {
				if (null != array_set.getLHS().getShape().get(i))
					tf &= ("1").equals(array_set.getLHS().getShape().get(i)
					.toString());
			}
			array_set.setRHS(Expressions.makeIRx10Exp(node.getRHS(), tf, target));
			
			//System.out.println(((IDUse) ((ArrayAccess)array_set.getLHS().getValue()).getIndices(0)).getID()+"%%");
			
			block.addStmt(array_set);

		 

	}

	public static void handleTIRAbstractArrayGetStmt(TIRArrayGetStmt node,
			IRx10ASTGenerator target, StmtBlock block) {
		
//		if (node instanceof TIRAbstractAssignToListStmt){
//			System.out.println("get is an instance of assign to list");
//		}
//		else System.out.println("get is NOT an instance of assign to list");
//
//		System.out.println(node.getArrayName().getID());

		AssignsAndDecls.handleTIRAbstractAssignToListStmt(node, target, block);

	}

}
