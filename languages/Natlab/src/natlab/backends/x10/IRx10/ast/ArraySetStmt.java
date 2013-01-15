package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:28
 */
public class ArraySetStmt extends Stmt implements Cloneable {
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
  public ArraySetStmt clone() throws CloneNotSupportedException {
    ArraySetStmt node = (ArraySetStmt)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ArraySetStmt copy() {
      try {
        ArraySetStmt node = (ArraySetStmt)clone();
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
  public ArraySetStmt fullCopy() {
    ArraySetStmt res = (ArraySetStmt)copy();
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
   * @declaredat ./astgen/pretty.jadd:77
   */
  String pp(String indent){
		StringBuffer x = new StringBuffer();
		
						
			x.append(getLHS().getValue().pp(indent));
			x.append(" = ");
			x.append(getRHS().pp("")+" ;\n");
		return x.toString();
	}
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public ArraySetStmt() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public ArraySetStmt(IDInfo p0, Exp p1) {
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
   * Setter for LHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setLHS(IDInfo node) {
    setChild(node, 0);
  }
  /**
   * Getter for LHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public IDInfo getLHS() {
    return (IDInfo)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public IDInfo getLHSNoTransform() {
    return (IDInfo)getChildNoTransform(0);
  }
  /**
   * Setter for RHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setRHS(Exp node) {
    setChild(node, 1);
  }
  /**
   * Getter for RHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public Exp getRHS() {
    return (Exp)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public Exp getRHSNoTransform() {
    return (Exp)getChildNoTransform(1);
  }
}
