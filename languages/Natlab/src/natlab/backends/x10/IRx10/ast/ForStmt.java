package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:70
 */
public class ForStmt extends Stmt implements Cloneable {
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
  public ForStmt clone() throws CloneNotSupportedException {
    ForStmt node = (ForStmt)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ForStmt copy() {
      try {
        ForStmt node = (ForStmt)clone();
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
  public ForStmt fullCopy() {
    ForStmt res = (ForStmt)copy();
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
  public ForStmt() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public ForStmt(AssignStmt p0, Exp p1, AdditiveExp p2, LoopBody p3) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
    setChild(p3, 3);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:16
   */
  protected int numChildren() {
    return 4;
  }
  /**
   * Setter for AssignStmt
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setAssignStmt(AssignStmt node) {
    setChild(node, 0);
  }
  /**
   * Getter for AssignStmt
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public AssignStmt getAssignStmt() {
    return (AssignStmt)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public AssignStmt getAssignStmtNoTransform() {
    return (AssignStmt)getChildNoTransform(0);
  }
  /**
   * Setter for Condition
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setCondition(Exp node) {
    setChild(node, 1);
  }
  /**
   * Getter for Condition
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public Exp getCondition() {
    return (Exp)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public Exp getConditionNoTransform() {
    return (Exp)getChildNoTransform(1);
  }
  /**
   * Setter for Stepper
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setStepper(AdditiveExp node) {
    setChild(node, 2);
  }
  /**
   * Getter for Stepper
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public AdditiveExp getStepper() {
    return (AdditiveExp)getChild(2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public AdditiveExp getStepperNoTransform() {
    return (AdditiveExp)getChildNoTransform(2);
  }
  /**
   * Setter for LoopBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setLoopBody(LoopBody node) {
    setChild(node, 3);
  }
  /**
   * Getter for LoopBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public LoopBody getLoopBody() {
    return (LoopBody)getChild(3);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public LoopBody getLoopBodyNoTransform() {
    return (LoopBody)getChildNoTransform(3);
  }
  
  
}
