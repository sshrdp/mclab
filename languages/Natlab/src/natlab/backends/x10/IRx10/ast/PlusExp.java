package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:21
 */
public class PlusExp extends UnaryExp implements Cloneable {
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
  public PlusExp clone() throws CloneNotSupportedException {
    PlusExp node = (PlusExp)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public PlusExp copy() {
      try {
        PlusExp node = (PlusExp)clone();
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
  public PlusExp fullCopy() {
    PlusExp res = (PlusExp)copy();
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
  public PlusExp() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public PlusExp(Exp p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * Setter for Operand
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setOperand(Exp node) {
    setChild(node, 0);
  }
  /**
   * Getter for Operand
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public Exp getOperand() {
    return (Exp)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public Exp getOperandNoTransform() {
    return (Exp)getChildNoTransform(0);
  }
}
