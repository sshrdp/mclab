package natlab.backends.x10.codegen;

import ast.*;
import ast.ASTNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;

import natlab.tame.classes.reference.ClassReference;
import natlab.tame.interproceduralAnalysis.InterproceduralAnalysisNode;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRAbstractAssignToListStmt;
import natlab.tame.tir.TIRAbstractAssignToVarStmt;
import natlab.tame.tir.TIRArraySetStmt;
import natlab.tame.tir.TIRCellArraySetStmt;
import natlab.tame.tir.TIRDotSetStmt;
import natlab.tame.tir.TIRFunction;
import natlab.tame.tir.TIRNode;
import natlab.tame.tir.analysis.TIRAbstractNodeCaseHandler;
import natlab.tame.valueanalysis.IntraproceduralValueAnalysis;
import natlab.tame.valueanalysis.ValueAnalysis;
import natlab.tame.valueanalysis.ValueAnalysis;
import natlab.tame.valueanalysis.ValueAnalysisPrinter;
import natlab.tame.valueanalysis.advancedMatrix.AdvancedMatrixValue;
import natlab.tame.valueanalysis.aggrvalue.AggrValue;
import natlab.tame.valueanalysis.value.Args;
import natlab.tame.valueanalysis.value.Res;
import natlab.backends.x10.codegen.x10Mapping;
import natlab.backends.x10.IRx10.ast.*;

public class x10CodeGenerator extends TIRAbstractNodeCaseHandler {
	ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis;
	private StringBuffer buf;
	private x10Mapping x10Map;
	private HashMap<String, Collection<ClassReference>> symbolMap = new HashMap<String, Collection<ClassReference>>();
	private String symbolMapKey;
	private int graphSize;
	private int index;
	private String fileDir;

	private x10CodeGenerator(
			ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis2, int size,
			int index, String fileDir, String classname) {
		this.buf = new StringBuffer();
		this.x10Map = new x10Mapping();
		this.analysis = analysis2;
		this.graphSize = graphSize;
		this.index = index;
		this.fileDir = fileDir;
		buf.append("public class " + classname + " {\n");

		((TIRNode) analysis2.getNodeList().get(index).getAnalysis().getTree())
				.tirAnalyze(this);

	}

	public static String x10CodePrinter(
			ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis2,
			int graphSize, int index, String fileDir, String classname) {
		return new x10CodeGenerator(analysis2, graphSize, index, fileDir,
				classname).buf.toString();

	}

	@Override
	public void caseASTNode(ASTNode node) {
		System.out.println("came in heeeere");
		if (node instanceof TIRAbstractAssignToVarStmt) {
			System.out.println("came in hereeee");
			caseTIRAbstractAssignStmt((TIRAbstractAssignStmt) node);
		}

	}

	@Override
	public void caseTIRFunction(TIRFunction node) {
		// if (index==0){
		String indent = node.getIndent();
		boolean first = true;
		ArrayList<String> inArgs = new ArrayList<String>();
		buf.append(indent + "public static def ");
		// TODO - CHANGE IT TO DETERMINE RETURN TYPE

		buf.append(node.getName());

		buf.append("(");
		first = true;
		for (Name param : node.getInputParams()) {
			if (!first) {
				buf.append(", ");
			}
			buf.append(param.getPrettyPrinted()
					+ ": "
					+ x10Map.getX10TypeMapping(getArgumentType(analysis,
							this.index, node, param.getID())));
			symbolMap
					.put(param.getID().toString(),
							getAnalysisValue(analysis, this.index, node,
									param.getID()));
			first = false;
		}
		buf.append(") {\n");
		printStatements(node.getStmts());
		// Write code for nested functions here
		buf.append(indent + "}//end of function\n}//end of class\n");
		// }
	}

	@Override
	public void caseTIRAbstractAssignStmt(TIRAbstractAssignStmt node) {
		System.out.println("in caseTIRAbstractAssignStmt");
		if (node instanceof TIRAbstractAssignToVarStmt) {
			handleTIRAbstractAssignToVarStmt(node);
		}
		// else if (node instanceof TIRAbstractAssignToListStmt){
		// // for(ast.Name name :
		// ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
		// // vars.add(name.getID());
		// handleTIRAbstractAssignToListStmt(node);
		//
		// }

		// TODO implement other cases here - refer to ValueAnalysisPrinter
		/*
		 * else if (node instanceof TIRAbstractAssignToListStmt){ for(ast.Name
		 * name :
		 * ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
		 * vars.add(name.getID()); } } else if (node instanceof
		 * TIRArraySetStmt){
		 * vars.add(((TIRArraySetStmt)node).getArrayName().getID()); } else if
		 * (node instanceof TIRCellArraySetStmt){
		 * vars.add(((TIRCellArraySetStmt)node).getCellArrayName().getID()); }
		 * else if (node instanceof TIRDotSetStmt){
		 * vars.add(((TIRDotSetStmt)node).getDotName().getID()); };
		 */
		// printVars(analysis.getOutFlowSets().get(node), vars);
	}

	public void handleTIRAbstractAssignToListStmt(TIRAbstractAssignStmt node) {

		String LHS;

		ArrayList<String> vars = new ArrayList<String>();
		for (ast.Name name : ((TIRAbstractAssignToListStmt) node).getTargets()
				.asNameList()) {
			vars.add(name.getID());
		}

		if (1 == vars.size()) { // only one variable on LHS
			symbolMapKey = vars.get(0);
			LHS = symbolMapKey;

			if (true == symbolMap.containsKey(symbolMapKey)) // variable already
																// defined and
																// analyzed
			{
				// buf.append(((TIRAbstractAssignToVarStmt)node).getPrettyPrintedLessComments());
				buf.append(LHS + " = ");
			} else {
				buf.append("val "
						+ LHS.toString()
						+ ": "
						+ x10Map.getX10TypeMapping(getLHSType(analysis,
								this.index, node, LHS)) + " = ");
				// use varname to get the name of the method/operator/Var
			}
			makeExpression(node);
			symbolMap.put(node.getLHS().getNodeString(),
					getAnalysisValue(analysis, this.index, node, LHS));

		} else if (0 == vars.size()) {
			// TODO
			makeExpression(node);
		}

	}

	public void makeExpression(TIRAbstractAssignStmt node) {
		/*
		 * Change for built-ins with n args Currently it handles general case
		 * built-ins with one or two args only
		 */

		int RHStype;
		String RHS;
		String Operand1, Operand2, prefix = "";
		String ArgsListasString;
		RHStype = getRHSType(node);
		RHS = getRHSExp(node);
		// TODO

		Operand1 = getOperand1(node);
		Operand2 = getOperand2(node);

		ArrayList<String> Args = new ArrayList<String>();

		if (Operand2 != "" && Operand2 != null)
			prefix = ", ";
		switch (RHStype) {
		case 1:
			buf.append(Operand1 + " " + RHS + " " + Operand2 + " ;");
			break;
		case 2:
			buf.append(RHS + "" + Operand1 + " ;"); // TODO test this
			break;
		case 3:
			Args = GetArgs(node);
			ArgsListasString = GetArgsListasString(Args);
			buf.append(RHS + "(" + ArgsListasString + ");");
			break;
		case 4:
			Args = GetArgs(node);
			ArgsListasString = GetArgsListasString(Args);
			buf.append(RHS + "(" + ArgsListasString + ");");
			break;
		case 5:
			buf.append(RHS + ";");
			break;
		default:
			buf.append("//is it an error?");
			break;
		}
	}

	public String getOperand1(TIRAbstractAssignStmt node) {
		if (node.getRHS().getChild(1).getNumChild() >= 1)
			return node.getRHS().getChild(1).getChild(0).getNodeString();
		else
			return "";
	}

	public String getOperand2(TIRAbstractAssignStmt node) {
		if (node.getRHS().getChild(1).getNumChild() >= 2)
			return node.getRHS().getChild(1).getChild(1).getNodeString();
		else
			return "";
	}

	public int getRHSType(TIRAbstractAssignStmt node) {
		String RHSName;
		RHSName = node.getRHS().getVarName();
		if (true == x10Map.isBinOperator(RHSName)) {
			return 1; // "binop";
		} else if (true == x10Map.isUnOperator(RHSName)) {
			return 2; // "unop";
		}

		else if (true == x10Map.isX10DirectBuiltin(RHSName)) {
			return 3; // "builtin";
		} else if (true == x10Map.isMethod(RHSName)) {
			return 4; // "method";
		} else if (true == x10Map.isBuiltinConst(RHSName)) {
			return 5; // "builtin";
		} else {
			return 0; // "default";
		}
	}

	public String getRHSExp(TIRAbstractAssignStmt node) {
		String RHS = null;
		String RHSName;
		RHSName = node.getRHS().getVarName();
		if (true == x10Map.isBinOperator(RHSName)) {
			RHS = x10Map.getX10BinOpMapping(RHSName);
		} else if (true == x10Map.isUnOperator(RHSName)) {
			RHS = x10Map.getX10UnOpMapping(RHSName);
		}

		else if (true == x10Map.isX10DirectBuiltin(RHSName)) {
			RHS = x10Map.getX10DirectBuiltinMapping(RHSName);
		} else if (true == x10Map.isBuiltinConst(RHSName)) {
			RHS = x10Map.getX10BuiltinConstMapping(RHSName);
		} else if (true == x10Map.isMethod(RHSName)) {
			RHS = x10Mapping.getX10MethodMapping(RHSName);
		} else {
			RHS = "//cannot process it yet";
		}
		return RHS;
	}

	public void handleTIRAbstractAssignToVarStmt(TIRAbstractAssignStmt node) {

		System.out.println("came in heeeere");

		// vars.add(((TIRAbstractAssignToVarStmt)node).getTargetName().getID());
		// if already present in symbolMap=>has been analyzed else define
		String LHS;
		// ArrayList<String> vars = new ArrayList<String>();
		symbolMapKey = ((TIRAbstractAssignToVarStmt) node).getTargetName()
				.getID();
		LHS = symbolMapKey;
		if (true == symbolMap.containsKey(symbolMapKey)) // variable already
															// defined and
															// analyzed
		{
			buf.append(((TIRAbstractAssignToVarStmt) node)
					.getPrettyPrintedLessComments());
		} else {

			String type = "type"; // x10Map.getX10TypeMapping(getLHSType(analysis,this.index,
									// node,LHS ));
			buf.append("val " + node.getLHS().getNodeString() + ": " + type
					+ " = ");
			if (type == "String") {
				// type = makeX10StringLiteral(type);
				buf.append(makeX10StringLiteral(node.getRHS().getNodeString())
						+ ";");
			} else
				buf.append(node.getRHS().getNodeString() + ";");
			// TODO check for expression on RHS
			// TODO check for built-ins
			// TODO check for operators
			// add to symbol Map
			symbolMap.put(node.getLHS().getNodeString(),
					getAnalysisValue(analysis, this.index, node, LHS));

		}

	}

	ArrayList<String> GetArgs(TIRAbstractAssignStmt node) {
		ArrayList<String> Args = new ArrayList<String>();
		int numArgs = node.getRHS().getChild(1).getNumChild();
		for (int i = 0; i < numArgs; i++) {
			Args.add(node.getRHS().getChild(1).getChild(i).getNodeString());
		}

		return Args;
	}

	String GetArgsListasString(ArrayList<String> Args) {
		String prefix = "";
		String ArgListasString = "";
		for (String arg : Args) {
			ArgListasString = ArgListasString + prefix + arg;
			prefix = ", ";
		}
		return ArgListasString;
	}

	/********************** HELPER METHODS ***********************************/
	private String getLHSType(ValueAnalysis<?> analysis, int graphIndex,
			TIRAbstractAssignStmt node, String SymbolMapKey) {
		// node.getTargetName().getID()
		return analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(SymbolMapKey)
				.getMatlabClasses().toArray()[0].toString();

	}

	private static String getArgumentType(ValueAnalysis<?> analysis,
			int graphIndex, TIRFunction node, String paramID) {
		System.out.println(analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(paramID).toString());// .getOutFlowSets().get(node).get(paramID).toString());

		return analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(paramID).getMatlabClasses()
				.toArray()[0].toString();
	}

	// get analysis value for Function node
	private static Collection<ClassReference> getAnalysisValue(
			ValueAnalysis<?> analysis, int graphIndex, TIRFunction node,
			String ID) {
		return analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(ID).getMatlabClasses();

		// return
		// analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}

	// get analysis value for abstract assignment node
	private static Collection<ClassReference> getAnalysisValue(
			ValueAnalysis<?> analysis, int graphIndex,
			TIRAbstractAssignStmt node, String ID) {
		return analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(ID).getMatlabClasses();

		// return
		// analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}

	private String makeX10StringLiteral(String StrLit) {

		if (StrLit.charAt(0) == '\''
				&& StrLit.charAt(StrLit.length() - 1) == '\'') {

			return "\"" + (String) StrLit.subSequence(1, StrLit.length() - 1)
					+ "\"";

		} else
			return StrLit;
	}

	private void printStatements(ast.List<ast.Stmt> stmts) {
		for (ast.Stmt stmt : stmts) {
			int length = buf.length();
			((TIRNode) stmt).tirAnalyze(this);
			if (buf.length() > length)
				buf.append('\n');
		}
	}

}
