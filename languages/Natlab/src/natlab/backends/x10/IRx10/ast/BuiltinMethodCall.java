package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:88
 */
public class BuiltinMethodCall extends MethodCall implements Cloneable {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
    super.flushCache();
  }
  /**
   * @apilevel internal
   */
  public void flushCollectionCache() {
    super.flushCollectionCache();
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public BuiltinMethodCall clone() throws CloneNotSupportedException {
    BuiltinMethodCall node = (BuiltinMethodCall)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public BuiltinMethodCall copy() {
      try {
        BuiltinMethodCall node = (BuiltinMethodCall)clone();
        if(children != null) node.children = (ASTNode[])children.clone();
        return node;
      } catch (CloneNotSupportedException e) {
      }
      System.err.println("Error: Could not clone node of type " + getClass().getName() + "!");
      return null;
  }
  /**
   * @apilevel low-level
   */
  @SuppressWarnings({"unchecked", "cast"})
  public BuiltinMethodCall fullCopy() {
    BuiltinMethodCall res = (BuiltinMethodCall)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect PrettyPrinter
   * @declaredat ./astgen/pretty.jadd:363
   */
  String pp(String indent)
{
	  StringBuffer x = new StringBuffer();
	  x.append(indent);
	  x.append(getBuiltinMethodName().getName()+"(");
	  x.append(getArgumentList().getChild(0).pp(""));
	  for(int i=1; i<getArgumentList().getNumChild() ; i++)
	  {
		  x.append(", "+getArgumentList().getChild(i).pp(""));
	  }
	  x.append(")");
	  return x.toString();
}
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public BuiltinMethodCall() {
    super();

    setChild(new List(), 1);

  }
  /**
   * @ast method 
   * @declaredat irx10.ast:8
   */
  public BuiltinMethodCall(MethodId p0, List<Exp> p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:15
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * Setter for BuiltinMethodName
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setBuiltinMethodName(MethodId node) {
    setChild(node, 0);
  }
  /**
   * Getter for BuiltinMethodName
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public MethodId getBuiltinMethodName() {
    return (MethodId)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public MethodId getBuiltinMethodNameNoTransform() {
    return (MethodId)getChildNoTransform(0);
  }
  /**
   * Setter for ArgumentList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setArgumentList(List<Exp> list) {
    setChild(list, 1);
  }
  /**
   * @return number of children in ArgumentList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public int getNumArgument() {
    return getArgumentList().getNumChild();
  }
  /**
   * Getter for child in list ArgumentList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Exp getArgument(int i) {
    return (Exp)getArgumentList().getChild(i);
  }
  /**
   * Add element to list ArgumentList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void addArgument(Exp node) {
    List<Exp> list = (parent == null || state == null) ? getArgumentListNoTransform() : getArgumentList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:34
   */
  public void addArgumentNoTransform(Exp node) {
    List<Exp> list = getArgumentListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list ArgumentList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:42
   */
  public void setArgument(Exp node, int i) {
    List<Exp> list = getArgumentList();
    list.setChild(node, i);
  }
  /**
   * Getter for Argument list.
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:50
   */
  public List<Exp> getArguments() {
    return getArgumentList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:56
   */
  public List<Exp> getArgumentsNoTransform() {
    return getArgumentListNoTransform();
  }
  /**
   * Getter for list ArgumentList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Exp> getArgumentList() {
    List<Exp> list = (List<Exp>)getChild(1);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Exp> getArgumentListNoTransform() {
    return (List<Exp>)getChildNoTransform(1);
  }
}
