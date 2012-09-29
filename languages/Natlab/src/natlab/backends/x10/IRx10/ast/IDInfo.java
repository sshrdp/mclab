package natlab.backends.x10.IRx10.ast;

import java.util.ArrayList;


/**
 * @ast node
 * @declaredat irx10.ast:15
 */
public class IDInfo extends ASTNode<ASTNode> implements Cloneable {
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
  public IDInfo clone() throws CloneNotSupportedException {
    IDInfo node = (IDInfo)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public IDInfo copy() {
      try {
        IDInfo node = (IDInfo)clone();
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
  public IDInfo fullCopy() {
    IDInfo res = (IDInfo)copy();
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
  public IDInfo() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public IDInfo(Type p0, String p1, ArrayList p2, String p3, Exp p4) {
    setChild(p0, 0);
    setName(p1);
    setShape(p2);
    setisComplex(p3);
    setChild(p4, 1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:17
   */
  protected int numChildren() {
    return 2;
  }
  /**
   * Setter for Type
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setType(Type node) {
    setChild(node, 0);
  }
  /**
   * Getter for Type
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public Type getType() {
    return (Type)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public Type getTypeNoTransform() {
    return (Type)getChildNoTransform(0);
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
   * Setter for lexeme Shape
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setShape(ArrayList value) {
    tokenArrayList_Shape = value;
  }
  /**   * @apilevel internal   * @ast method 
   * @declaredat irx10.ast:8
   */
  
  /**   * @apilevel internal   */  protected ArrayList tokenArrayList_Shape;
  /**
   * Getter for lexeme Shape
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  public ArrayList getShape() {
    return tokenArrayList_Shape;
  }
  /**
   * Setter for lexeme isComplex
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setisComplex(String value) {
    tokenString_isComplex = value;
  }
  /**   * @apilevel internal   * @ast method 
   * @declaredat irx10.ast:8
   */
  
  /**   * @apilevel internal   */  protected String tokenString_isComplex;
  /**
   * Getter for lexeme isComplex
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  public String getisComplex() {
    return tokenString_isComplex != null ? tokenString_isComplex : "";
  }
  /**
   * Setter for Value
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setValue(Exp node) {
    setChild(node, 1);
  }
  /**
   * Getter for Value
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public Exp getValue() {
    return (Exp)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public Exp getValueNoTransform() {
    return (Exp)getChildNoTransform(1);
  }
}
