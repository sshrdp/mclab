package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:24
 */
public abstract class LiteralExp extends Exp implements Cloneable {
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
  public LiteralExp clone() throws CloneNotSupportedException {
    LiteralExp node = (LiteralExp)super.clone();
    return node;
  }
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public LiteralExp() {
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
