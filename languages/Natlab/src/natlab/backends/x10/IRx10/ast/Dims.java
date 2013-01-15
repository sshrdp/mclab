package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:15
 */
public class Dims extends ASTNode<ASTNode> implements Cloneable {
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
  public Dims clone() throws CloneNotSupportedException {
    Dims node = (Dims)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Dims copy() {
      try {
        Dims node = (Dims)clone();
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
  public Dims fullCopy() {
    Dims res = (Dims)copy();
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
  public Dims() {
    super();

    setChild(new Opt(), 0);

  }
  /**
   * @ast method 
   * @declaredat irx10.ast:8
   */
  public Dims(Opt<Exp> p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:14
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * Setter for ExpOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setExpOpt(Opt<Exp> opt) {
    setChild(opt, 0);
  }
  /**
   * Does this node have a Exp child?
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public boolean hasExp() {
    return getExpOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child Exp
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Exp getExp() {
    return (Exp)getExpOpt().getChild(0);
  }
  /**
   * Setter for optional child Exp
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void setExp(Exp node) {
    getExpOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Exp> getExpOpt() {
    return (Opt<Exp>)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Exp> getExpOptNoTransform() {
    return (Opt<Exp>)getChildNoTransform(0);
  }
}
