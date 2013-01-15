package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:3
 */
public class ClassBlock extends ASTNode<ASTNode> implements Cloneable {
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
  public ClassBlock clone() throws CloneNotSupportedException {
    ClassBlock node = (ClassBlock)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ClassBlock copy() {
      try {
        ClassBlock node = (ClassBlock)clone();
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
  public ClassBlock fullCopy() {
    ClassBlock res = (ClassBlock)copy();
    for(int i = 0; i < getNumChildNoTransform(); i++) {
      ASTNode node = getChildNoTransform(i);
      if(node != null) node = node.fullCopy();
      res.setChild(node, i);
    }
    return res;
    }
  /**
   * @ast method 
   * @aspect PrettyPrinter
   * @declaredat ./astgen/pretty.jadd:14
   */
  String pp(String indent,String className){
	StringBuffer x = new StringBuffer();
	x.append("class "+className+"{\n");
	for(Stmt decl_stmt : getDeclStmtList()){
		x.append(decl_stmt.pp(indent+"	"));
	}
	for (Method method : getMethodList()){
		x.append(method.pp(indent+"	"));
	x.append("\n}");

	}
	return x.toString();
}
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public ClassBlock() {
    super();

    setChild(new List(), 0);
    setChild(new List(), 1);

  }
  /**
   * @ast method 
   * @declaredat irx10.ast:9
   */
  public ClassBlock(List<Stmt> p0, List<Method> p1) {
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
   * Setter for DeclStmtList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setDeclStmtList(List<Stmt> list) {
    setChild(list, 0);
  }
  /**
   * @return number of children in DeclStmtList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public int getNumDeclStmt() {
    return getDeclStmtList().getNumChild();
  }
  /**
   * Getter for child in list DeclStmtList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Stmt getDeclStmt(int i) {
    return (Stmt)getDeclStmtList().getChild(i);
  }
  /**
   * Add element to list DeclStmtList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void addDeclStmt(Stmt node) {
    List<Stmt> list = (parent == null || state == null) ? getDeclStmtListNoTransform() : getDeclStmtList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:34
   */
  public void addDeclStmtNoTransform(Stmt node) {
    List<Stmt> list = getDeclStmtListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list DeclStmtList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:42
   */
  public void setDeclStmt(Stmt node, int i) {
    List<Stmt> list = getDeclStmtList();
    list.setChild(node, i);
  }
  /**
   * Getter for DeclStmt list.
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:50
   */
  public List<Stmt> getDeclStmts() {
    return getDeclStmtList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:56
   */
  public List<Stmt> getDeclStmtsNoTransform() {
    return getDeclStmtListNoTransform();
  }
  /**
   * Getter for list DeclStmtList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Stmt> getDeclStmtList() {
    List<Stmt> list = (List<Stmt>)getChild(0);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Stmt> getDeclStmtListNoTransform() {
    return (List<Stmt>)getChildNoTransform(0);
  }
  /**
   * Setter for MethodList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setMethodList(List<Method> list) {
    setChild(list, 1);
  }
  /**
   * @return number of children in MethodList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public int getNumMethod() {
    return getMethodList().getNumChild();
  }
  /**
   * Getter for child in list MethodList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Method getMethod(int i) {
    return (Method)getMethodList().getChild(i);
  }
  /**
   * Add element to list MethodList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void addMethod(Method node) {
    List<Method> list = (parent == null || state == null) ? getMethodListNoTransform() : getMethodList();
    list.addChild(node);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:34
   */
  public void addMethodNoTransform(Method node) {
    List<Method> list = getMethodListNoTransform();
    list.addChild(node);
  }
  /**
   * Setter for child in list MethodList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:42
   */
  public void setMethod(Method node, int i) {
    List<Method> list = getMethodList();
    list.setChild(node, i);
  }
  /**
   * Getter for Method list.
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:50
   */
  public List<Method> getMethods() {
    return getMethodList();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:56
   */
  public List<Method> getMethodsNoTransform() {
    return getMethodListNoTransform();
  }
  /**
   * Getter for list MethodList
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:63
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Method> getMethodList() {
    List<Method> list = (List<Method>)getChild(1);
    list.getNumChild();
    return list;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:72
   */
  @SuppressWarnings({"unchecked", "cast"})
  public List<Method> getMethodListNoTransform() {
    return (List<Method>)getChildNoTransform(1);
  }
}
