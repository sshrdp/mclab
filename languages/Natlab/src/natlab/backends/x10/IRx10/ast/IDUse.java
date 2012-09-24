package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:12
 */
public class IDUse extends Exp implements Cloneable {
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
  public IDUse clone() throws CloneNotSupportedException {
    IDUse node = (IDUse)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public IDUse copy() {
      try {
        IDUse node = (IDUse)clone();
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
  public IDUse fullCopy() {
    IDUse res = (IDUse)copy();
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
  public IDUse() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public IDUse(String p0) {
    setID(p0);
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
   * Setter for lexeme ID
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setID(String value) {
    tokenString_ID = value;
  }
  /**   * @apilevel internal   * @ast method 
   * @declaredat irx10.ast:8
   */
  
  /**   * @apilevel internal   */  protected String tokenString_ID;
  /**
   * Getter for lexeme ID
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  public String getID() {
    return tokenString_ID != null ? tokenString_ID : "";
  }
}
