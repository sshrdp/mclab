package natlab.backends.x10.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import natlab.backends.x10.IRx10.ast.AssignStmt;
import natlab.backends.x10.IRx10.ast.ClassBlock;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.Method;
import natlab.backends.x10.IRx10.ast.MethodBlock;
import natlab.backends.x10.IRx10.ast.MethodHeader;
import natlab.backends.x10.IRx10.ast.Program;
import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.Type;
import natlab.backends.x10.IRx10.ast.Stmt;
import natlab.tame.classes.reference.ClassReference;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRAbstractAssignToListStmt;
import natlab.tame.tir.TIRAbstractAssignToVarStmt;
import natlab.tame.tir.TIRFunction;
import natlab.tame.tir.TIRNode;
import natlab.tame.tir.analysis.TIRAbstractNodeCaseHandler;
import natlab.tame.valueanalysis.ValueAnalysis;
import natlab.tame.valueanalysis.advancedMatrix.AdvancedMatrixValue;
import natlab.tame.valueanalysis.aggrvalue.AggrValue;
import ast.ASTNode;
import ast.Name;

import natlab.tame.classes.reference.ClassReference;
import natlab.tame.tir.*;

import natlab.tame.tir.analysis.TIRAbstractNodeCaseHandler;
import natlab.tame.valueanalysis.ValueAnalysis;
import natlab.tame.valueanalysis.ValueAnalysisPrinter;
import natlab.tame.valueanalysis.basicmatrix.BasicMatrixValue;
import natlab.tame.valueanalysis.aggrvalue.AggrValue;
import natlab.backends.Fortran.codegen.FortranMapping;

@SuppressWarnings("unused")
public class IRx10ASTGenerator extends TIRAbstractNodeCaseHandler {
	ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis;
	x10Mapping x10Map;
	HashMap<String, Collection<ClassReference>> symbolMap = new HashMap<String, Collection<ClassReference>>();
	String symbolMapKey;
	private int graphSize;
	int index;
	private String fileDir;
	Method method;

	private IRx10ASTGenerator(
			ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis2, int size,
			int index, String fileDir, String classname) {
		this.x10Map = new x10Mapping();
		this.analysis = analysis2;
		// this.graphSize = graphSize;
		this.index = index;
		this.fileDir = fileDir;
		this.method = new Method();
		((TIRNode) analysis2.getNodeList().get(index).getAnalysis().getTree())
				.tirAnalyze(this);

	}

	public static ClassBlock x10ClassMaker(
			ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis2,
			int graphSize, String fileDir, String classname) {

		List<Method> methodList = new List<Method>();
		IRx10ASTGenerator subAST;
		for (int i = 0; i < graphSize; i++) {
			subAST = new IRx10ASTGenerator(analysis2, graphSize, 0, fileDir,
					classname);
			methodList.add(subAST.method);
		}
		ClassBlock class_block = new ClassBlock(null, methodList);
		return class_block;

	}

	@Override
	public void caseASTNode(ASTNode node) {

	}

	@Override
	public void caseTIRFunction(TIRFunction node) {
		Function.handleTIRFunction(node, this);

	}

	@Override
	public void caseTIRAbstractAssignStmt(TIRAbstractAssignStmt node) {

		if (node instanceof TIRAbstractAssignToVarStmt) {
			Assignments.handleTIRAbstractAssignToVarStmt(node, this);
		} else if (node instanceof TIRAbstractAssignToListStmt) {
			// for(ast.Name name :
			// ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
			// vars.add(name.getID());
			Assignments.handleTIRAbstractAssignToListStmt(node, this);

		}
		// TODO implement other cases here - refer to ValueAnalysisPrinter
		/*
		 * else if (node instanceof TIRArraySetStmt){
		 * vars.add(((TIRArraySetStmt)node).getArrayName().getID()); } else if
		 * (node instanceof TIRCellArraySetStmt){
		 * vars.add(((TIRCellArraySetStmt)node).getCellArrayName().getID()); }
		 * else if (node instanceof TIRDotSetStmt){
		 * vars.add(((TIRDotSetStmt)node).getDotName().getID()); };
		 */
		// printVars(analysis.getOutFlowSets().get(node), vars);
	}

}
