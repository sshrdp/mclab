package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:25
 */
public class Literal extends LiteralExp implements Cloneable {
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
  public Literal clone() throws CloneNotSupportedException {
    Literal node = (Literal)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Literal copy() {
      try {
        Literal node = (Literal)clone();
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
  public Literal fullCopy() {
    Literal res = (Literal)copy();
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
  public Literal() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public Literal(String p0) {
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
  /**   * @apilevel internal   * @ast method 
   * @declaredat irx10.ast:8
   */
  
  /**   * @apilevel internal   */  protected String tokenString_Literal;
  /**
   * Getter for lexeme Literal
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  public String getLiteral() {
    return tokenString_Literal != null ? tokenString_Literal : "";
  }
}
