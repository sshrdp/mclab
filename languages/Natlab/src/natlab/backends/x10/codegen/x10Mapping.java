package natlab.backends.x10.codegen;

import java.util.HashMap;

public class x10Mapping {
	
	private static HashMap<String, String> x10TypeMap = new HashMap();
	private static HashMap<String, String> x10BinOperatorMap = new HashMap();
	private static HashMap<String, String> x10UnOperatorMap = new HashMap();
	private static HashMap<String, String> x10BuiltinMap = new HashMap();
	private static HashMap<String, String> x10BuiltinConstMap = new HashMap();
	private static HashMap<String, String> x10MethodMap = new HashMap();
	
	public x10Mapping(){
		makex10TypeMap();
		makex10BinOperatorMap();
		makex10UnOperatorMap();
		makex10BuiltinMap();
		makex10BuiltinConstMap();
		makex10MethodMap();
	}
	
	private void makex10TypeMap()
	{
		x10TypeMap.put("char", "String");
		x10TypeMap.put("double", "Double");

	}
	
	private void makex10BinOperatorMap(){
		x10BinOperatorMap.put("plus", "+");
		x10BinOperatorMap.put("minus", "-");
		x10BinOperatorMap.put("mtimes", "*");
		x10BinOperatorMap.put("mrdivide", "/");
		x10BinOperatorMap.put("mldivide", "\\");//may be as a method 
		x10BinOperatorMap.put("mpower", "^");
		x10BinOperatorMap.put("times", ".*");//may be as a method 
		x10BinOperatorMap.put("rdivide", "./");//may be as a method 
		x10BinOperatorMap.put("ldivide", ".\\");//may be as a method 
		x10BinOperatorMap.put("power", ".^");//may be as a method 
		x10BinOperatorMap.put("and", "&");
		x10BinOperatorMap.put("or", "|");
		x10BinOperatorMap.put("lt", "<");
		x10BinOperatorMap.put("gt", ">");
		x10BinOperatorMap.put("le", "<=");
		x10BinOperatorMap.put("ge", ">=");
		x10BinOperatorMap.put("eq", "==");
		x10BinOperatorMap.put("ne", "!=");
		x10BinOperatorMap.put("transpose", ".'");//may be as a method 
		x10BinOperatorMap.put("ctranspose", "'");//may be as a method 
		x10BinOperatorMap.put("not", "~");
		x10BinOperatorMap.put("colon", ":");//may be as a method 
		
	}
	
	private void makex10UnOperatorMap(){
		x10UnOperatorMap.put("uminus", "-");
		x10UnOperatorMap.put("uplus", "+");
	}
	
	private void makex10BuiltinMap(){
	//TODO create a categorical map here 
	  x10BuiltinMap.put("disp", "Console.OUT.println");	
	 
	}
	
	private void makex10BuiltinConstMap(){
		//TODO create a categorical map here 
		 
		  x10BuiltinConstMap.put("pi", "Math.PI");	
		}
	
	private void makex10MethodMap(){
		//TODO
	}
	
	public static String getX10TypeMapping(String mclassasKey){
		return x10TypeMap.get(mclassasKey);
	}
	
	public static Boolean isBinOperator(String expType){
		if (true == x10BinOperatorMap.containsKey(expType))
			return true;
		else
			return false;
	}
	
	public static String getX10BinOpMapping(String Operator){
		return x10BinOperatorMap.get(Operator);
	}
	
	
	
	public static Boolean isUnOperator(String expType){
		if (true == x10UnOperatorMap.containsKey(expType))
			return true;
		else
			return false;
	}
	
	public static String getX10UnOpMapping(String Operator){
		return x10UnOperatorMap.get(Operator);
	}
	
	public static Boolean isBuiltin(String expType){
		if (true == x10BuiltinMap.containsKey(expType))
			return true;
		else
			return false;
	}
	
	public String getX10BuiltinMapping (String BuiltinName){
		
		 return x10BuiltinMap.get(BuiltinName);
		
	}
	
	public static Boolean isBuiltinConst(String expType){
		if (true == x10BuiltinConstMap.containsKey(expType))
			return true;
		else
			return false;
	}
	
	public String getX10BuiltinConstMapping (String BuiltinName){
		
		 return x10BuiltinConstMap.get(BuiltinName);
		
	}
	
	public static Boolean isMethod(String expType){
		if (true == x10MethodMap.containsKey(expType))
			return true;
		else
			return false;
	}
	
	public static String getX10MethodMapping(String MethodName){
		return x10MethodMap.get(MethodName);
	}
	
	
}
