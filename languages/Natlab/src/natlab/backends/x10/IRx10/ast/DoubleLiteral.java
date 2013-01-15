package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:34
 */
public class DoubleLiteral extends Literal implements Cloneable {
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
  public DoubleLiteral clone() throws CloneNotSupportedException {
    DoubleLiteral node = (DoubleLiteral)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public DoubleLiteral copy() {
      try {
        DoubleLiteral node = (DoubleLiteral)clone();
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
  public DoubleLiteral fullCopy() {
    DoubleLiteral res = (DoubleLiteral)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public DoubleLiteral() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public DoubleLiteral(String p0) {
    setLiteral(p0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * Setter for lexeme Literal
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setLiteral(String value) {
    tokenString_Literal = value;
  }
  /**
   * Getter for lexeme Literal
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public String getLiteral() {
    return tokenString_Literal != null ? tokenString_Literal : "";
  }
}
