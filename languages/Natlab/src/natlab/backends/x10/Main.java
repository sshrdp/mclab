package natlab.backends.x10;

import java.io.*;

import natlab.tame.valueanalysis.*;
import natlab.tame.valueanalysis.advancedMatrix.AdvancedMatrixValue;
import natlab.tame.valueanalysis.aggrvalue.*;
import natlab.tame.AdvancedTamerTool;
import natlab.toolkits.filehandling.genericFile.GenericFile;
import natlab.toolkits.path.FileEnvironment;
import natlab.backends.x10.IRx10.ast.AssignStmt;
import natlab.backends.x10.IRx10.ast.DeclStmt;
import natlab.backends.x10.IRx10.ast.Program;
import natlab.backends.x10.codegen.*;

public class Main {

	public static void main(String[] args) {
		// String file =
		// "/home/2011/vkumar5/mclab/Project/languages/Natlab/src/natlab/backends/x10/testing/unitTests/ut8"
		// ;
		String file = "/home/2011/vkumar5/mclab/Project/languages/Natlab/src/natlab/backends/x10/benchmarks/mc_for_benchmarks/adpt/main";

		//String file = "/home/2011/vkumar5/hello1";
		String fileIn = file + ".m";
		String fileOut = file + ".x10";
		GenericFile gFile = GenericFile.create(fileIn);
		/* /home/xuli/test/hello.m */
		FileEnvironment env = new FileEnvironment(gFile); // get path
															// environment obj
		String x10Code = "";
		AdvancedTamerTool tool = new AdvancedTamerTool();
		// System.out.println(args[0]);
		ValueAnalysis<AggrValue<AdvancedMatrixValue>> analysis = tool.analyze(
				args, env);
		int size = analysis.getNodeList().size();

		System.out.println("\n------------------------------------\n");

		System.out.println("\n------------------------------------\n");
		

		
		
		System.out
		.println("\n~~~~~~~~~~~~~~~~X10 code~~~~~~~~~~~~~~~~~~~~~~~\n");
//		System.out.println("UNCOMMENT IN MAIN");
		Program irx10Program = new Program();
		irx10Program.setClassBlock(IRx10ASTGenerator.x10ClassMaker(analysis,
				size, "home/2011/vkumar5/", "testclass"));
		
		String x10Program = irx10Program.pp("","testclass");
		System.out.println(x10Program);
		System.out
		.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileOut));
			out.write(x10Code);
			out.close();
		} catch (IOException e) {
			System.out.println("Exception ");

		}

	}

}
