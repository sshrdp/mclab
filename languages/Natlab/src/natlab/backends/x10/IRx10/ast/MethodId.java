package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:83
 */
public abstract class MethodId extends AccessVal implements Cloneable {
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
  public MethodId clone() throws CloneNotSupportedException {
    MethodId node = (MethodId)super.clone();
    return node;
  }
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public MethodId() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public MethodId(String p0) {
    setName(p0);
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
   * Setter for lexeme Name
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setName(String value) {
    tokenString_Name = value;
  }
  /**   * @apilevel internal   * @ast method 
   * @declaredat irx10.ast:8
   */
  
  /**   * @apilevel internal   */  protected String tokenString_Name;
  /**
   * Getter for lexeme Name
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  public String getName() {
    return tokenString_Name != null ? tokenString_Name : "";
  }
}
