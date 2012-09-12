package natlab.backends.Fortran.codegen.PPcaseHandler;

import ast.Name;
import natlab.backends.Fortran.codegen.*;
import natlab.tame.tir.*;

public class PPHandleCaseTIRArraySetStmt {

	static boolean Debug = false;
	
	public PPHandleCaseTIRArraySetStmt(){
		
	}
	
	public FortranCodePrettyPrinter getFortran(FortranCodePrettyPrinter fcg, TIRArraySetStmt node){
		if (Debug) System.out.println("in an arrayset statement!");
		fcg.buf.append(node.getArrayName().getVarName()+"("+node.getIndizes().toString().replace("[", "").replace("]", "")+")"+" = "+node.getValueName().getVarName()+";");
		for(Name index : node.getIndizes().asNameList()){
			fcg.arrayIndexParameter.add(index.getVarName());
		}
		return fcg;
	}
}
