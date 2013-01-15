package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:18
 */
public class MultiAssignLHS extends Exp implements Cloneable {
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
  public MultiAssignLHS clone() throws CloneNotSupportedException {
    MultiAssignLHS node = (MultiAssignLHS)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public MultiAssignLHS copy() {
      try {
        MultiAssignLHS node = (MultiAssignLHS)clone();
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
  public MultiAssignLHS fullCopy() {
    MultiAssignLHS res = (MultiAssignLHS)copy();
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
  public MultiAssignLHS() {
    super();

    setChild(new List(), 0);

  }
  /**
   * @ast method 
   * @declaredat irx10.ast:8
   */
  public MultiAssignLHS(List<IDInfo> p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:14
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * Setter for IDInfoList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setIDInfoList(List<IDInfo> list) {
    setChild(list, 0);
  }
  /**
   * @return number of children in IDInfoList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public int getNumIDInfo() {
    return getIDInfoList().getNumChild();
  }
  /**
   * Getter for child in list IDInfoList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public IDInfo getIDInfo(int i) {
    return (IDInfo)getIDInfoList().getChild(i);
  }
  /**
   * Add element to list IDInfoList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void addIDInfo(IDInfo node) {
    List<IDInfo> list = (parent == null || state == null) ? getIDInfoListNoTransform() : getIDInfoList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:34
   */
  public void addIDInfoNoTransform(IDInfo node) {
    List<IDInfo> list = getIDInfoListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list IDInfoList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:42
   */
  public void setIDInfo(IDInfo node, int i) {
    List<IDInfo> list = getIDInfoList();
    list.setChild(node, i);
  }
  /**
   * Getter for IDInfo list.
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:50
   */
  public List<IDInfo> getIDInfos() {
    return getIDInfoList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:56
   */
  public List<IDInfo> getIDInfosNoTransform() {
    return getIDInfoListNoTransform();
  }
  /**
   * Getter for list IDInfoList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<IDInfo> getIDInfoList() {
    List<IDInfo> list = (List<IDInfo>)getChild(0);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<IDInfo> getIDInfoListNoTransform() {
    return (List<IDInfo>)getChildNoTransform(0);
  }
}
