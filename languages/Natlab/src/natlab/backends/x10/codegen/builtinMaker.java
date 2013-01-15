package natlab.backends.x10.codegen;

import java.io.BufferedWriter;
import java.io.File;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import natlab.tame.valueanalysis.ValueAnalysisPrinter;
import ast.Expr;

public class builtinMaker {
	
	public builtinMaker(){
		
	}

	public static boolean isBuiltin(String callName) {
		/*
		 * This method finds out whether the method is builtin or not(user-defined)
		 */
		ArrayList<String> builtinList = new ArrayList<String>();
		builtinList.add("plus");
		builtinList.add("minus");
		
		if(builtinList.contains(callName))
		return true;
		else
			return false;
	}

	public static void makeBuiltin(Expr natlabExp) {
		/*
		 * This is the method that picks up the right method from the xml 
		 * and creates a x10 method ("literally" node in the mix10 class
		 */
		String builtin = new String();
		builtin = getBuiltinFromXml(natlabExp);
		
		
		try {
			StringBuffer mix10Builtins = new StringBuffer();
			BufferedWriter out = new BufferedWriter(new FileWriter("/home/2011/vkumar5/mclab/Project/languages/Natlab/src/natlab/backends/x10/benchmarks/unit/mix10.x10", false));
			out.close();
			BufferedWriter outAgain = new BufferedWriter(new FileWriter("/home/2011/vkumar5/mclab/Project/languages/Natlab/src/natlab/backends/x10/benchmarks/unit/mix10.x10", true));
			outAgain.write(builtin);
			outAgain.close();
		} catch (IOException e) {
			System.out.println("File IOException - cannot create mix10 class ");

		}
	}

	private static String getBuiltinFromXml(Expr natlabExp) {
		// TODO Auto-generated method stub
		
		int arrayOrGml = specialize(natlabExp);
		String exprType = getExprType(natlabExp);
		try {
			String builtin;
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse (new File("/home/2011/vkumar5/mclab/Project/languages/Natlab/src/natlab/backends/x10/codegen/mix10_builtins.xml"));
		doc.getDocumentElement ().normalize ();
		
		NodeList builtinList = doc.getElementsByTagName(natlabExp.getVarName());
		Element builtinArrayOrGml = (Element)builtinList.item(arrayOrGml);
		 builtin = ((Node)(
				 ((Element)(builtinArrayOrGml.getElementsByTagName(exprType).item(0))).getChildNodes().item(0)
				 )).getNodeValue().trim();
		
		System.out.println(builtin);
		return builtin;
		
		
		
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private static String getExprType(Expr natlabExp) {
		/*
		 * Returns what type of expression it is according
		 * to XML.
		 * type1 - all scalar
		 * type2 - all arrays
		 * type3 - one scalar, other arrays
		 * type4 - others
		 */
		return "type1";
	}

	private static int specialize(Expr natlabExp) {
		// TODO Write specialization code here
		/*
		 * This function decides the default 
		 * decision for looking up arrays or GML
		 * first
		 * 0 - arrays
		 * 1 - gml
		 */
		return 0;//return "arrays";
	}

}
