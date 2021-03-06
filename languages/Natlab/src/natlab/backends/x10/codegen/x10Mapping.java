package natlab.backends.x10.codegen;

import java.util.HashMap;

import natlab.backends.x10.IRx10.ast.Type;

public class x10Mapping {

	private static HashMap<String, Type> x10TypeMap = new HashMap<String, Type>();
	private static HashMap<String, String> x10BinOperatorMap = new HashMap<String, String>();
	private static HashMap<String, String> x10UnOperatorMap = new HashMap<String, String>();
	private static HashMap<String, String> x10DirectBuiltinMap = new HashMap<String, String>();
	private static HashMap<String, String> x10BuiltinConstMap = new HashMap<String, String>();
	private static HashMap<String, String> x10MethodMap = new HashMap<String, String>();

	public x10Mapping() {
		makex10TypeMap();
		makex10BinOperatorMap();
		makex10UnOperatorMap();
		makex10DirectBuiltinMap();
		makex10BuiltinConstMap();
		makex10MethodMap();
	}

	private void makex10TypeMap() {
		x10TypeMap.put("char", new Type("String"));
		x10TypeMap.put("double", new Type("Double"));
		x10TypeMap.put("single", new Type("Float"));
		x10TypeMap.put("int8", new Type("Byte"));
		x10TypeMap.put("int16", new Type("Short"));
		x10TypeMap.put("int32", new Type("Int"));
		x10TypeMap.put("int64", new Type("Long"));
		x10TypeMap.put("uint8", new Type("UByte"));
		x10TypeMap.put("uint16", new Type("UShort"));
		x10TypeMap.put("uint32", new Type("UInt"));
		x10TypeMap.put("uint64", new Type("ULong"));
		x10TypeMap.put("logical", new Type("Boolean"));
		x10TypeMap.put(null, new Type("Double")); /*This is the default type*/
	}

	private void makex10BinOperatorMap() {
		x10BinOperatorMap.put("plus", "+");
		x10BinOperatorMap.put("minus", "-");
		x10BinOperatorMap.put("mtimes", "*");
		x10BinOperatorMap.put("mrdivide", "/");
		x10BinOperatorMap.put("mldivide", "\\");// may be as a method
		x10BinOperatorMap.put("mpower", "^");
		x10BinOperatorMap.put("times", ".*");// may be as a method
		x10BinOperatorMap.put("rdivide", "./");// may be as a method
		x10BinOperatorMap.put("ldivide", ".\\");// may be as a method
		x10BinOperatorMap.put("power", ".^");// may be as a method
		x10BinOperatorMap.put("and", "&");
		x10BinOperatorMap.put("or", "|");
		x10BinOperatorMap.put("lt", "<");
		x10BinOperatorMap.put("gt", ">");
		x10BinOperatorMap.put("le", "<=");
		x10BinOperatorMap.put("ge", ">=");
		x10BinOperatorMap.put("eq", "==");
		x10BinOperatorMap.put("ne", "!=");
		x10BinOperatorMap.put("transpose", ".'");// may be as a method
		x10BinOperatorMap.put("ctranspose", "'");// may be as a method
		
		x10BinOperatorMap.put("colon", ":");// may be as a method

	}

	private void makex10UnOperatorMap() {
		x10UnOperatorMap.put("uminus", "-");
		x10UnOperatorMap.put("uplus", "+");
		x10BinOperatorMap.put("not", "!");
	}

	private void makex10DirectBuiltinMap() {
		// TODO create a categorical map here
		x10DirectBuiltinMap.put("disp", "Console.OUT.println");
		x10DirectBuiltinMap.put("sqrt", "sqrt");
		x10DirectBuiltinMap.put("sin", "sin");
		x10DirectBuiltinMap.put("cos", "cos");

	}

	private void makex10BuiltinConstMap() {
		// TODO create a categorical map here

		x10BuiltinConstMap.put("pi", "Math.PI");
	}

	private void makex10MethodMap() {
		// TODO
	}

	public static Type getX10TypeMapping(String mclassasKey) {
		return x10TypeMap.get(mclassasKey);
	}

	public static Boolean isBinOperator(String expType) {
		if (true == x10BinOperatorMap.containsKey(expType))
			return true;
		else
			return false;
	}

	public static String getX10BinOpMapping(String Operator) {
		return x10BinOperatorMap.get(Operator);
	}

	public static Boolean isUnOperator(String expType) {
		if (true == x10UnOperatorMap.containsKey(expType))
			return true;
		else
			return false;
	}

	public static String getX10UnOpMapping(String Operator) {
		return x10UnOperatorMap.get(Operator);
	}

	public static Boolean isX10DirectBuiltin(String expType) {
		if (true == x10DirectBuiltinMap.containsKey(expType))
			return true;
		else
			return false;
	}

	public static String getX10DirectBuiltinMapping(String BuiltinName) {

		return x10DirectBuiltinMap.get(BuiltinName);

	}

	public static Boolean isBuiltinConst(String expType) {
		if (true == x10BuiltinConstMap.containsKey(expType))
			return true;
		else
			return false;
	}

	public static String getX10BuiltinConstMapping(String BuiltinName) {

		return x10BuiltinConstMap.get(BuiltinName);

	}

	public static Boolean isMethod(String expType) {
		if (true == x10MethodMap.containsKey(expType))
			return true;
		else
			return false;
	}

	public static String getX10MethodMapping(String MethodName) {
		return x10MethodMap.get(MethodName);
	}

}
