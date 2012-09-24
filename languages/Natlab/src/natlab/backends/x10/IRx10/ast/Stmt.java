package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:3
 */
public abstract class Stmt extends ASTNode<ASTNode> implements Cloneable {
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
  public Stmt clone() throws CloneNotSupportedException {
    Stmt node = (Stmt)super.clone();
    return node;
  }
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public Stmt() {
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
