package natlab.backends.x10.codegen;

import ast.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import natlab.tame.classes.reference.ClassReference;
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
import natlab.tame.valueanalysis.ValueAnalysisPrinter;
import natlab.backends.x10.codegen.x10Mapping;

public class x10CodeGenerator extends TIRAbstractNodeCaseHandler{
	IntraproceduralValueAnalysis<?> analysis;
	private StringBuffer buf;
	private x10Mapping x10Map;
	private  HashMap<String, Collection<ClassReference>> symbolMap = new HashMap<String, Collection<ClassReference>>(); 
	private String symbolMapKey;	
	
	
	private x10CodeGenerator(IntraproceduralValueAnalysis<?> analysis, String classname) {
		this.buf = new StringBuffer();
		this.x10Map = new x10Mapping();
		this.analysis = analysis;
		buf.append("public class "+classname+" {\n");
		((TIRNode)analysis.getTree()).tirAnalyze(this);
	}
	
	
	public static String x10CodePrinter(
			IntraproceduralValueAnalysis<?> analysis, String classname){
		return new x10CodeGenerator(analysis, classname).buf.toString();
	
	}
	
	
	@Override
	public void caseASTNode(ASTNode node) {
		// TODO Auto-generated method stub
		
	}
	public void caseTIRFunction(TIRFunction node){
		String indent = node.getIndent();
		boolean first = true;
		ArrayList<String> inArgs = new ArrayList<String>();
		buf.append(indent + "public static def " );
		/* TODO - CHANGE IT TO DETERMINE RETURN TYPE
		//buf.append(" [");
		//boolean first = true;
		//for(Name param : node.getOutputParams()) {
			if(!first) {
				buf.append(", ");
			}
			buf.append(param.getPrettyPrinted());
			
			first = false;
		}
		*/
				
		buf.append(node.getName());
					
		buf.append("(");
		first = true;
		for(Name param : node.getInputParams()) {
			if(!first) {
				buf.append(", ");
			}
			buf.append(param.getPrettyPrinted()+": "+getArgumentType(analysis, node, param.getID()) );
			symbolMap.put(param.getID().toString(), getAnalysisValue(analysis, node, param.getID()));
			first = false;
		}
		buf.append(") {\n");
		printStatements(node.getStmts());
		//Write code for nested functions here
		buf.append(indent + "}//end of function\n}//end of class\n");
	}
	
	@Override
	public void caseTIRAbstractAssignStmt(TIRAbstractAssignStmt node) {
	   
		if (node instanceof TIRAbstractAssignToVarStmt){
			handleTIRAbstractAssignToVarStmt(node);
		}
		else if (node instanceof TIRAbstractAssignToListStmt){
		//	for(ast.Name name : ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
		//		vars.add(name.getID());
			handleTIRAbstractAssignToListStmt(node);		
			
			}
					
		//TODO implement other cases here - refer to ValueAnalysisPrinter
		/*
		else if (node instanceof TIRAbstractAssignToListStmt){
			for(ast.Name name : ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
				vars.add(name.getID());				
			}
		} else if (node instanceof TIRArraySetStmt){
			vars.add(((TIRArraySetStmt)node).getArrayName().getID());
		} else if (node instanceof TIRCellArraySetStmt){
			vars.add(((TIRCellArraySetStmt)node).getCellArrayName().getID());
		} else if (node instanceof TIRDotSetStmt){
			vars.add(((TIRDotSetStmt)node).getDotName().getID());
		};
		*/
		//printVars(analysis.getOutFlowSets().get(node), vars);
	}
	
	public void handleTIRAbstractAssignToListStmt(TIRAbstractAssignStmt node){
		
		
		String LHS, RHS;
		int RHStype;
		String Operand1, Operand2;
		ArrayList<String> vars = new ArrayList<String>();
		for(ast.Name name : ((TIRAbstractAssignToListStmt)node).getTargets().asNameList()){
		 vars.add(name.getID());
		}
		
		if (1==vars.size()){ //only one variable on LHS
			symbolMapKey = vars.get(0);
			LHS = symbolMapKey;
			
			if(true == symbolMap.containsKey(symbolMapKey)) //variable already defined and analyzed
			{
				//buf.append(((TIRAbstractAssignToVarStmt)node).getPrettyPrintedLessComments());
				buf.append(LHS + " = ");
			}
			else
			{
				buf.append("val "+LHS.toString()+": "+x10Map.getX10TypeMapping(getLHSType(analysis,node,LHS ))+" = ");
				//use varname to get the name of the method/operator/Var
			}
			
				RHStype = getRHSType(node);
				RHS = getRHSExp(node);
				Operand1 = getOperand1(node);
				Operand2 = getOperand2(node);
				
				switch(RHStype)
				{
				case 1:
					buf.append(Operand1+" "+RHS+" "+Operand2+" ;");
					break;
				case 2:
					buf.append(RHS+" "+Operand1+" ;"); //TODO test this
					break;
				case 3:
					buf.append(RHS+"("+Operand1+", "+Operand2+");");
					break;
				case 4:
					buf.append(RHS+"("+Operand1+", "+Operand2+");");
					break;
				default:
					buf.append("//is it an error?");	
					break;
				}
				
				symbolMap.put(node.getLHS().getNodeString(), getAnalysisValue(analysis, node,LHS));
			
			
			
		}
		else if(0==vars.size()){
			//TODO
			RHStype = getRHSType(node);
			RHS = getRHSExp(node);
			//TODO
			/*
			 * This is an extremely ugly hack !!!!
			 * huh....What was I thinking ????
			 * make an array of operands - that will handle 0,1 or 2 operands .
			 * Also it will make it easier to print in code without extra "," .
			 * 
			 */
			Operand1 = getOperand1(node);
			Operand2 = getOperand2(node);
			
			switch(RHStype)
			{
			case 1:
				buf.append(Operand1+" "+RHS+" "+Operand2+" ;");
				break;
			case 2:
				buf.append(RHS+" "+Operand1+" ;"); //TODO test this
				break;
			case 3:
				buf.append(RHS+"("+Operand1+", "+Operand2+");");
				break;
			case 4:
				buf.append(RHS+"("+Operand1+", "+Operand2+");");
				break;
			default:
				buf.append("//is it an error?");	
				break;
			}
		}
		
		
	}
	
	
	
	public String getOperand1(TIRAbstractAssignStmt node){
		if(node.getRHS().getChild(1).getChild(0) != null)
			return node.getRHS().getChild(1).getChild(0).getNodeString();
		else
			return " ";
	}
	
	public String getOperand2(TIRAbstractAssignStmt node){
		if(node.getRHS().getChild(1).getNumChild() == 2)
			return node.getRHS().getChild(1).getChild(1).getNodeString();
		else
			return " ";
	}
	
	public int getRHSType(TIRAbstractAssignStmt node){
		String RHSName;
		RHSName = node.getRHS().getVarName();
		if (true==x10Map.isBinOperator(RHSName))
		{
			return 1; //"binop";
		}
		else if (true==x10Map.isUnOperator(RHSName))
		{
		   return 2; //"unop";
		}
		
		else if (true==x10Map.isBuiltin(RHSName))
		{
			return 3; // "builtin";
		}
		else if (true == x10Map.isMethod(RHSName))
		{
			return 4; // "method";
		}
		else
		{
			return 0; // "default";
		}
	}
	
	public String getRHSExp(TIRAbstractAssignStmt node){
		String RHS = null;
		String RHSName;
		RHSName = node.getRHS().getVarName();
		if (true==x10Map.isBinOperator(RHSName))
		{
			RHS= x10Map.getX10BinOpMapping(RHSName);
		}
		else if (true==x10Map.isUnOperator(RHSName))
		{
		   RHS= x10Map.getX10UnOpMapping(RHSName);
		}
		
		else if (true==x10Map.isBuiltin(RHSName))
		{
			RHS= x10Map.getX10BuiltinMapping(RHSName);
		}
		else if (true == x10Map.isMethod(RHSName))
		{
			RHS= x10Mapping.getX10MethodMapping(RHSName);
		}
		else
		{
			RHS = "//cannot process it yet";
		}
		return RHS;
	}
	
	
	public void handleTIRAbstractAssignToVarStmt(TIRAbstractAssignStmt node){
		//vars.add(((TIRAbstractAssignToVarStmt)node).getTargetName().getID());
		//if already present in symbolMap=>has been analyzed else define
		String LHS;
		ArrayList<String> vars = new ArrayList<String>();
		symbolMapKey = ((TIRAbstractAssignToVarStmt)node).getTargetName().getID();
		LHS = symbolMapKey;
		if(true == symbolMap.containsKey(symbolMapKey)) //variable already defined and analyzed
		{
			buf.append(((TIRAbstractAssignToVarStmt)node).getPrettyPrintedLessComments());
		}
		else 
		{   
			
			buf.append("val "+node.getLHS().getNodeString()+": "+x10Map.getX10TypeMapping(getLHSType(analysis,node,LHS ))+" = ");
			buf.append(node.getRHS().getNodeString() + ";");
			//TODO check for expression on RHS
			//TODO check for built-ins
			//TODO check for operators
			//add to symbol Map
			symbolMap.put(node.getLHS().getNodeString(), getAnalysisValue(analysis, node,LHS));
			
			
		}
		
	}
		
		
		
		
		
		
	
	/**********************HELPER METHODS***********************************/
	private String getLHSType(IntraproceduralValueAnalysis<?> analysis,
			TIRAbstractAssignStmt node, String SymbolMapKey) {
		//node.getTargetName().getID()
		return analysis.getOutFlowSets().get(node).get(SymbolMapKey).getMatlabClasses().toArray()[0].toString();
		
	}


	
	private static String getArgumentType(IntraproceduralValueAnalysis<?> analysis, TIRFunction node, String paramID){
		//System.out.println(analysis.getOutFlowSets().get(node).get(paramID).toString());

		return analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}
	
	//get analysis value for Function node
	private static Collection<ClassReference> getAnalysisValue(IntraproceduralValueAnalysis<?> analysis, TIRFunction node, String ID){
		return analysis.getOutFlowSets().get(node).get(ID).getMatlabClasses();

		//return analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}
	
	
	//get analysis value for abstract assignment node
	private static Collection<ClassReference> getAnalysisValue(IntraproceduralValueAnalysis<?> analysis, TIRAbstractAssignStmt node, String ID){
		return analysis.getOutFlowSets().get(node).get(ID).getMatlabClasses();

		//return analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}
	
	private void printStatements(ast.List<ast.Stmt> stmts){
		for(ast.Stmt stmt : stmts) {
			int length = buf.length();
			((TIRNode)stmt).tirAnalyze(this);
			if (buf.length() > length) buf.append('\n');
		}
	}
	
}
