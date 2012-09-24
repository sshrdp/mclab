package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:74
 */
public class IfElseStmt extends Stmt implements Cloneable {
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
  public IfElseStmt clone() throws CloneNotSupportedException {
    IfElseStmt node = (IfElseStmt)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public IfElseStmt copy() {
      try {
        IfElseStmt node = (IfElseStmt)clone();
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
  public IfElseStmt fullCopy() {
    IfElseStmt res = (IfElseStmt)copy();
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
  public IfElseStmt() {
    super();

    setChild(new Opt(), 2);

  }
  /**
   * @ast method 
   * @declaredat irx10.ast:8
   */
  public IfElseStmt(Exp p0, IfBody p1, Opt<ElseBody> p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:16
   */
  protected int numChildren() {
    return 3;
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
  /**
   * Setter for ElseBodyOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setElseBodyOpt(Opt<ElseBody> opt) {
    setChild(opt, 2);
  }
  /**
   * Does this node have a ElseBody child?
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public boolean hasElseBody() {
    return getElseBodyOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child ElseBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ElseBody getElseBody() {
    return (ElseBody)getElseBodyOpt().getChild(0);
  }
  /**
   * Setter for optional child ElseBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void setElseBody(ElseBody node) {
    getElseBodyOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<ElseBody> getElseBodyOpt() {
    return (Opt<ElseBody>)getChild(2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<ElseBody> getElseBodyOptNoTransform() {
    return (Opt<ElseBody>)getChildNoTransform(2);
  }
}
