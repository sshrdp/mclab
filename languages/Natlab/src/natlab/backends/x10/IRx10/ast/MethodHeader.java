package natlab.backends.x10.IRx10.ast;


/**
 * @ast node
 * @declaredat irx10.ast:6
 */
public class MethodHeader extends ASTNode<ASTNode> implements Cloneable {
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
  public MethodHeader clone() throws CloneNotSupportedException {
    MethodHeader node = (MethodHeader)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public MethodHeader copy() {
      try {
        MethodHeader node = (MethodHeader)clone();
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
  public MethodHeader fullCopy() {
    MethodHeader res = (MethodHeader)copy();
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
  public MethodHeader() {
    super();

    setChild(new List(), 1);

  }
  /**
   * @ast method 
   * @declaredat irx10.ast:8
   */
  public MethodHeader(AccessVal p0, String p1, List<Args> p2) {
    setChild(p0, 0);
    setName(p1);
    setChild(p2, 1);
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
   * Setter for ReturnType
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setReturnType(AccessVal node) {
    setChild(node, 0);
  }
  /**
   * Getter for ReturnType
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public AccessVal getReturnType() {
    return (AccessVal)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public AccessVal getReturnTypeNoTransform() {
    return (AccessVal)getChildNoTransform(0);
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
  /**
   * Setter for ArgsList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setArgsList(List<Args> list) {
    setChild(list, 1);
  }
  /**
   * @return number of children in ArgsList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public int getNumArgs() {
    return getArgsList().getNumChild();
  }
  /**
   * Getter for child in list ArgsList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Args getArgs(int i) {
    return (Args)getArgsList().getChild(i);
  }
  /**
   * Add element to list ArgsList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void addArgs(Args node) {
    List<Args> list = (parent == null || state == null) ? getArgsListNoTransform() : getArgsList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:34
   */
  public void addArgsNoTransform(Args node) {
    List<Args> list = getArgsListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list ArgsList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:42
   */
  public void setArgs(Args node, int i) {
    List<Args> list = getArgsList();
    list.setChild(node, i);
  }
  /**
   * Getter for Args list.
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:50
   */
  public List<Args> getArgss() {
    return getArgsList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:56
   */
  public List<Args> getArgssNoTransform() {
    return getArgsListNoTransform();
  }
  /**
   * Getter for list ArgsList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Args> getArgsList() {
    List<Args> list = (List<Args>)getChild(1);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Args> getArgsListNoTransform() {
    return (List<Args>)getChildNoTransform(1);
  }
}
