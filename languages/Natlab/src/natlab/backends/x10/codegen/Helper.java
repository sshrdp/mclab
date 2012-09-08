package natlab.backends.x10.codegen;

import java.util.Collection;

import natlab.tame.classes.reference.ClassReference;
import natlab.tame.tir.TIRAbstractAssignStmt;
import natlab.tame.tir.TIRFunction;
import natlab.tame.valueanalysis.ValueAnalysis;

public class Helper {
	/********************** HELPER METHODS ***********************************/
	static String getLHSType(ValueAnalysis<?> analysis, int graphIndex,
			TIRAbstractAssignStmt node, String SymbolMapKey) {
		// node.getTargetName().getID()
		return analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(SymbolMapKey)
				.getMatlabClasses().toArray()[0].toString();

	}

	static String getArgumentType(ValueAnalysis<?> analysis, int graphIndex,
			TIRFunction node, String paramID) {
		// System.out.println(analysis.getNodeList().get(graphIndex).getAnalysis().getOutFlowSets().get(node).get(paramID).toString());//.getOutFlowSets().get(node).get(paramID).toString());

		return analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(paramID).getMatlabClasses()
				.toArray()[0].toString();
	}

	// get analysis value for Function node
	static Collection<ClassReference> getAnalysisValue(
			ValueAnalysis<?> analysis, int graphIndex, TIRFunction node,
			String ID) {
		return analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(ID).getMatlabClasses();

		// return
		// analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}

	// get analysis value for abstract assignment node
	static Collection<ClassReference> getAnalysisValue(
			ValueAnalysis<?> analysis, int graphIndex,
			TIRAbstractAssignStmt node, String ID) {
		return analysis.getNodeList().get(graphIndex).getAnalysis()
				.getOutFlowSets().get(node).get(ID).getMatlabClasses();

		// return
		// analysis.getOutFlowSets().get(node).get(paramID).getMatlabClasses().toArray()[0].toString();
	}

}
