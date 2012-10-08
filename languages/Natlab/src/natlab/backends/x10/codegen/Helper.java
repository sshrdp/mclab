package natlab.backends.x10.codegen;

import java.util.ArrayList;
import java.util.Collection;

import ast.Name;

import natlab.backends.x10.IRx10.ast.IDInfo;
import natlab.backends.x10.IRx10.ast.Type;
import natlab.tame.classes.reference.ClassReference;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRForStmt;
import natlab.tame.tir.TIRFunction;
import natlab.tame.valueanalysis.ValueAnalysis;
import natlab.tame.valueanalysis.advancedMatrix.AdvancedMatrixValue;
import natlab.tame.valueanalysis.aggrvalue.AggrValue;

public class Helper {
	/********************** HELPER METHODS ***********************************/
	static String getLHSType(ValueAnalysis<?> analysis, int graphIndex,
			TIRAbstractAssignStmt node, String SymbolMapKey) {
		// node.getTargetName().getID()
		if (analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).isViable())
			return analysis.getNodeList().get(graphIndex).getAnalysis()
					.getOutFlowSets().get(node).get(SymbolMapKey)
					.getMatlabClasses().toArray()[0].toString();
		else
			return "double";
		/**
		 * TODO check if there is something better than "double"
		 */

	}

	static String getArgumentType(ValueAnalysis<?> analysis, int graphIndex,
			TIRFunction node, String paramID) {
		if (analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).isViable())
			return analysis.getNodeList().get(graphIndex).getAnalysis()
					.getOutFlowSets().get(node).get(paramID).getMatlabClasses()
					.toArray()[0].toString();
		else
			return "double";
		/**
		 * TODO check if there is something better than "double"
		 */
	}

	// get analysis value for Function node
	static Collection<ClassReference> getAnalysisValue(
			ValueAnalysis<?> analysis, int graphIndex, TIRFunction node,
			String ID) {

		if (analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).isViable())
			return analysis.getNodeList().get(graphIndex).getAnalysis()
					.getOutFlowSets().get(node).get(ID).getMatlabClasses();
		else
			return null;

		// return
		// analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}

	// get analysis value for abstract assignment node
	static Collection<ClassReference> getAnalysisValue(
			ValueAnalysis<?> analysis, int graphIndex,
			TIRAbstractAssignStmt node, String ID) {
		if (analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).isViable())
			return analysis.getNodeList().get(graphIndex).getAnalysis()
					.getOutFlowSets().get(node).get(ID).getMatlabClasses();
		else
			return null;
		// return
		// analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}

	static Type getReturnType(
			ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis,
			int graphIndex, TIRFunction node, String ID) {

		String retMClassName = ((AdvancedMatrixValue) (analysis.getNodeList()
				.get(graphIndex).getAnalysis().getOutFlowSets().get(node)
				.get(ID).getSingleton())).getMatlabClass().getName();

		return x10Mapping.getX10TypeMapping(retMClassName);

	}

	static IDInfo generateIDInfo(
			ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis,
			int graphIndex, TIRAbstractAssignStmt node, String ID) {

		if (analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).isViable()) {
			AdvancedMatrixValue temp = ((AdvancedMatrixValue) (analysis
					.getNodeList().get(graphIndex).getAnalysis()
					.getOutFlowSets().get(node).get(ID).getSingleton()));

			IDInfo id_info = new IDInfo();
			if (null != temp.getMatlabClass()) {
				id_info.setType(x10Mapping.getX10TypeMapping(temp
						.getMatlabClass().getName()));
			}
			if (null != temp.getShape()){
				id_info.setShape((ArrayList<Integer>) temp.getShape()
						.getDimensions());
			}
			if (null != temp.getisComplexInfo()){
				id_info.setisComplex(temp.getisComplexInfo().toString());
			}
			return id_info;
		}

		else {
			return new IDInfo();
		}

	}

	static IDInfo generateIDInfo(
			ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis,
			int graphIndex, TIRForStmt node, String ID) {
		if (analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).isViable()) {
			AdvancedMatrixValue temp = ((AdvancedMatrixValue) (analysis
					.getNodeList().get(graphIndex).getAnalysis()
					.getOutFlowSets().get(node).get(ID).getSingleton()));

			IDInfo id_info = new IDInfo();
			id_info.setType(x10Mapping.getX10TypeMapping(temp.getMatlabClass()
					.getName()));
			id_info.setShape((ArrayList<Integer>) temp.getShape()
					.getDimensions());
			id_info.setisComplex(temp.getisComplexInfo().toString());

			return id_info;
		}

		else {
			return new IDInfo();
		}
	}

}
