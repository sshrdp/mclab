package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:79
 */
public abstract class Exp extends ASTNode<ASTNode> implements Cloneable {
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
  public Exp clone() throws CloneNotSupportedException {
    Exp node = (Exp)super.clone();
    return node;
  }
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public Exp() {
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
