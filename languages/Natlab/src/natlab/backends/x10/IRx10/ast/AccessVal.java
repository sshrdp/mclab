package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:80
 */
public abstract class AccessVal extends Exp implements Cloneable {
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
  public AccessVal clone() throws CloneNotSupportedException {
    AccessVal node = (AccessVal)super.clone();
    return node;
  }
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public AccessVal() {
    super();


  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:10
   */
  protected int numChildren() {
    return 0;
  }
}
