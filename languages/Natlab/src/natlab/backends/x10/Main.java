package natlab.backends.x10;

import java.util.Collections;
import java.io.*;

import org.w3c.dom.Document;

import natlab.tame.TamerTool;
import natlab.tame.classes.reference.PrimitiveClassReference;
import natlab.tame.tir.*;
import natlab.tame.valueanalysis.*;
import natlab.tame.valueanalysis.aggrvalue.*;
import natlab.tame.valueanalysis.simplematrix.*;
import natlab.tame.valueanalysis.value.Value;
import natlab.backends.x10.codegen.*;

public class Main {

	
	
	public static void main(String[] args) {
		String file = "/home/2011/vkumar5/mclab/Project/languages/Natlab/src/natlab/backends/x10/testing/unitTests/ut8" ;
	    String fileIn = file+".m";
	    String fileOut =  file+".x10";
	    String x10Code;
		TamerTool tool = new TamerTool();
		IntraproceduralValueAnalysis<AggrValue<SimpleMatrixValue>>  analysis = tool.tameMatlabToSingleFunctionFromClassReferences(
				new java.io.File(fileIn),
				Collections.singletonList(PrimitiveClassReference.DOUBLE));
		
	//	IntraproceduralValueAnalysis<AggrValue<SimpleMatrixValue>>  analysis = tool.tameMatlabToSingleFunctionFromClassReferences(
	//			new java.io.File(file),Collections.singletonList(PrimitiveClassReference.DOUBLE));
		
		TIRFunction function = analysis.getTree();
		
	//	System.out.println(function.getPrettyPrinted()); //print IR-ast
		
		System.out.println("\n------------------------------------\n");
		
		/*	
		System.out.println(function.getStmtList().getChild(5).getPrettyPrinted()); //prnt 6th stmt of function
		System.out.println(analysis.getOutFlowSets().get(function.getStmtList().getChild(5))); //get associated flow set, print	
		*/
		
		System.out.println("\n------------------------------------\n");
		System.out.println(ValueAnalysisPrinter.prettyPrint(analysis)); //combined flow analysis/ast print
		
		System.out.println("\n~~~~~~~~~~~~~~~~X10 code~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println(x10CodeGenerator.x10CodePrinter(analysis, "testclass"));
		x10Code = x10CodeGenerator.x10CodePrinter(analysis, "testclass");
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileOut));
			out.write(x10Code);
			out.close();
			}
			catch (IOException e)
			{
			System.out.println("Exception ");

			}
		
		/*
		ValueFlowMap<AggrValue<SimpleMatrixValue>> flowMap = analysis.getOutFlowSets().get(function.getStmtList().getChild(12));
		ast.Stmt stmt = function.getStmtList().getChild(12);
		function.getStmtList().getNumChild();
		
		System.out.println(stmt.getPrettyPrinted());
		System.out.println(flowMap);
		ValueFlowMap<AggrValue<SimpleMatrixValue>> flow2 = flowMap.newInstance();
		flow2.put("a", ValueSet.newInstance(new SimpleMatrixValueFactory().newMatrixValue(true)));
		flowMap = flowMap.merge(flow2);
		System.out.println(flowMap);
		*/
	}
}
