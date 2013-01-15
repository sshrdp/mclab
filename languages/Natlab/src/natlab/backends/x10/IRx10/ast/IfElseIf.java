package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:81
 */
public class IfElseIf extends ASTNode<ASTNode> implements Cloneable {
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
  public IfElseIf clone() throws CloneNotSupportedException {
    IfElseIf node = (IfElseIf)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public IfElseIf copy() {
      try {
        IfElseIf node = (IfElseIf)clone();
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
  public IfElseIf fullCopy() {
    IfElseIf res = (IfElseIf)copy();
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
  public IfElseIf() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public IfElseIf(Exp p0, IfBody p1) {
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
   * Setter for Condition
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setCondition(Exp node) {
    setChild(node, 0);
  }
  /**
   * Getter for Condition
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public Exp getCondition() {
    return (Exp)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public Exp getConditionNoTransform() {
    return (Exp)getChildNoTransform(0);
  }
  /**
   * Setter for IfBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setIfBody(IfBody node) {
    setChild(node, 1);
  }
  /**
   * Getter for IfBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public IfBody getIfBody() {
    return (IfBody)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public IfBody getIfBodyNoTransform() {
    return (IfBody)getChildNoTransform(1);
  }
}
