
/*
Action info class
Author: Toheed Aslam
Date: May 01, 2010
*/

package aspectMatlab;

import ast.Function;

/*
 * A structure to keep action info
 */
public class ActionInfo {
	private String name;
	private String type;
	//private pattern patt;
	private String patt;
	private Function func;
	private String className;

	public ActionInfo(String nam, String typ, String pat, Function fun, String cName) {
		name = nam;
		type = typ;
		patt = pat;
		func = fun;
		className = cName;
	}

	public void setName(String nam) { name = nam; }
	public void setType(String typ) { type = typ; }
	public void setPattern(String pat) { patt = pat; }
	public void setFunction(Function fun) { func = fun; }
	public void setClassName(String name) { className = name; }

	public String getName() { return name; }
	public String getType() { return type; }
	public String getPattern() { return patt; }
	public Function getFunction() { return func; }
	public String getClassName() { return className; }
}
