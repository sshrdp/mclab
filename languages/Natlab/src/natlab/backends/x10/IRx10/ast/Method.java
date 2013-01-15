package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:7
 */
public class Method extends ASTNode<ASTNode> implements Cloneable {
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
  public Method clone() throws CloneNotSupportedException {
    Method node = (Method)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Method copy() {
      try {
        Method node = (Method)clone();
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
  public Method fullCopy() {
    Method res = (Method)copy();
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
   * @declaredat ./astgen/pretty.jadd:114
   */
  String pp(String indent)
{
	  StringBuffer x = new StringBuffer();
	  x.append(indent+ getMethodHeader().pp("")+"\n"+getMethodBlock().pp(indent+"    "));
	  
	  return x.toString();
}
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public Method() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public Method(MethodHeader p0, MethodBlock p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:14
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * Setter for MethodHeader
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setMethodHeader(MethodHeader node) {
    setChild(node, 0);
  }
  /**
   * Getter for MethodHeader
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public MethodHeader getMethodHeader() {
    return (MethodHeader)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public MethodHeader getMethodHeaderNoTransform() {
    return (MethodHeader)getChildNoTransform(0);
  }
  /**
   * Setter for MethodBlock
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setMethodBlock(MethodBlock node) {
    setChild(node, 1);
  }
  /**
   * Getter for MethodBlock
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public MethodBlock getMethodBlock() {
    return (MethodBlock)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public MethodBlock getMethodBlockNoTransform() {
    return (MethodBlock)getChildNoTransform(1);
  }
}
