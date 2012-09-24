package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:74
 */
public class IfElseStmt extends Stmt implements Cloneable {
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
  public IfElseStmt clone() throws CloneNotSupportedException {
    IfElseStmt node = (IfElseStmt)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public IfElseStmt copy() {
      try {
        IfElseStmt node = (IfElseStmt)clone();
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
  public IfElseStmt fullCopy() {
    IfElseStmt res = (IfElseStmt)copy();
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
  public IfElseStmt() {
    super();

    setChild(new List(), 0);
    setChild(new Opt(), 1);

  }
  /**
   * @ast method 
   * @declaredat irx10.ast:9
   */
  public IfElseStmt(List<IfElseIf> p0, Opt<ElseBody> p1) {
    setChild(p0, 0);
    setChild(p1, 1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:16
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * Setter for IfElseIfList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setIfElseIfList(List<IfElseIf> list) {
    setChild(list, 0);
  }
  /**
   * @return number of children in IfElseIfList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public int getNumIfElseIf() {
    return getIfElseIfList().getNumChild();
  }
  /**
   * Getter for child in list IfElseIfList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public IfElseIf getIfElseIf(int i) {
    return (IfElseIf)getIfElseIfList().getChild(i);
  }
  /**
   * Add element to list IfElseIfList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void addIfElseIf(IfElseIf node) {
    List<IfElseIf> list = (parent == null || state == null) ? getIfElseIfListNoTransform() : getIfElseIfList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:34
   */
  public void addIfElseIfNoTransform(IfElseIf node) {
    List<IfElseIf> list = getIfElseIfListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list IfElseIfList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:42
   */
  public void setIfElseIf(IfElseIf node, int i) {
    List<IfElseIf> list = getIfElseIfList();
    list.setChild(node, i);
  }
  /**
   * Getter for IfElseIf list.
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:50
   */
  public List<IfElseIf> getIfElseIfs() {
    return getIfElseIfList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:56
   */
  public List<IfElseIf> getIfElseIfsNoTransform() {
    return getIfElseIfListNoTransform();
  }
  /**
   * Getter for list IfElseIfList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<IfElseIf> getIfElseIfList() {
    List<IfElseIf> list = (List<IfElseIf>)getChild(0);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<IfElseIf> getIfElseIfListNoTransform() {
    return (List<IfElseIf>)getChildNoTransform(0);
  }
  /**
   * Setter for ElseBodyOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setElseBodyOpt(Opt<ElseBody> opt) {
    setChild(opt, 1);
  }
  /**
   * Does this node have a ElseBody child?
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public boolean hasElseBody() {
    return getElseBodyOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child ElseBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ElseBody getElseBody() {
    return (ElseBody)getElseBodyOpt().getChild(0);
  }
  /**
   * Setter for optional child ElseBody
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void setElseBody(ElseBody node) {
    getElseBodyOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<ElseBody> getElseBodyOpt() {
    return (Opt<ElseBody>)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<ElseBody> getElseBodyOptNoTransform() {
    return (Opt<ElseBody>)getChildNoTransform(1);
  }
}
