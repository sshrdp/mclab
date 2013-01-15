package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:1
 */
public class Program extends ASTNode<ASTNode> implements Cloneable {
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
  public Program clone() throws CloneNotSupportedException {
    Program node = (Program)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Program copy() {
      try {
        Program node = (Program)clone();
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
  public Program fullCopy() {
    Program res = (Program)copy();
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
   * @declaredat ./astgen/pretty.jadd:10
   */
  public String pp(String indent, String className){
	return getClassBlock().pp(indent, className);
}
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public Program() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public Program(ClassBlock p0) {
    setChild(p0, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  protected int numChildren() {
    return 1;
  }
  /**
   * Setter for ClassBlock
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setClassBlock(ClassBlock node) {
    setChild(node, 0);
  }
  /**
   * Getter for ClassBlock
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public ClassBlock getClassBlock() {
    return (ClassBlock)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public ClassBlock getClassBlockNoTransform() {
    return (ClassBlock)getChildNoTransform(0);
  }
}
