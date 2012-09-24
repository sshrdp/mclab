package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:71
 */
public class WhileStmt extends Stmt implements Cloneable {
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
  public WhileStmt clone() throws CloneNotSupportedException {
    WhileStmt node = (WhileStmt)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public WhileStmt copy() {
      try {
        WhileStmt node = (WhileStmt)clone();
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
  public WhileStmt fullCopy() {
    WhileStmt res = (WhileStmt)copy();
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
  public WhileStmt() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public WhileStmt(Exp p0, LoopBody p1) {
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
   * Setter for LoopBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setLoopBody(LoopBody node) {
    setChild(node, 1);
  }
  /**
   * Getter for LoopBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public LoopBody getLoopBody() {
    return (LoopBody)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public LoopBody getLoopBodyNoTransform() {
    return (LoopBody)getChildNoTransform(1);
  }
}
