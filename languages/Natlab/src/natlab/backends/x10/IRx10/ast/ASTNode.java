package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;


// Generated with JastAdd II (http://jastadd.org) version R20110902

/**
 * @ast node
 * @declaredat ASTNode.ast:0
 */
public class ASTNode<T extends ASTNode> implements Cloneable, Iterable<T> {
  /**
   * @apilevel low-level
   */
  public void flushCache() {
  }
  /**
   * @apilevel internal
   */
  public void flushCollectionCache() {
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ASTNode<T> clone() throws CloneNotSupportedException {
    ASTNode node = (ASTNode)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public ASTNode<T> copy() {
      try {
        ASTNode node = (ASTNode)clone();
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
  public ASTNode<T> fullCopy() {
    ASTNode res = (ASTNode)copy();
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
   * @declaredat ./astgen/pretty.jadd:8
   */
  String pp(String indent){return indent;}
  /**
   * @ast method 
   * @declaredat ASTNode.ast:1
   */
  public ASTNode() {
    super();


  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat ASTNode.ast:10
   */
  

  /**
   * @apilevel internal
   */
  public static final boolean generatedWithCircularEnabled = true;
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat ASTNode.ast:14
   */
  
  /**
   * @apilevel internal
   */
  public static final boolean generatedWithCacheCycle = true;
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat ASTNode.ast:18
   */
  
  /**
   * @apilevel internal
   */
  public static final boolean generatedWithComponentCheck = true;
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat ASTNode.ast:22
   */
  
  /**
   * @apilevel internal
   */
  protected static ASTNode$State state = new ASTNode$State();
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat ASTNode.ast:26
   */
  public final ASTNode$State state() { return state; }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:30
   */
  @SuppressWarnings("cast")
  public T getChild(int i) {
    return (T)getChildNoTransform(i);
  }
  /**
   * @apilevel internal
   * @ast method 
   * @declaredat ASTNode.ast:37
   */
  
  /**
   * @apilevel internal
   */
  private int childIndex;
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:41
   */
  public int getIndexOfChild(ASTNode node) {
    if(node != null && node.childIndex < getNumChildNoTransform() && node == getChildNoTransform(node.childIndex))
      return node.childIndex;
    for(int i = 0; i < getNumChildNoTransform(); i++)
      if(getChildNoTransform(i) == node) {
        node.childIndex = i;
        return i;
      }
    return -1;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:55
   */
  public void addChild(T node) {
    setChild(node, getNumChildNoTransform());
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:61
   */
  @SuppressWarnings("cast")
  public final T getChildNoTransform(int i) {
    return (T)children[i];
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:68
   */
  
  /**
   * @apilevel low-level
   */
  protected int numChildren;
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:72
   */
  protected int numChildren() {
    return numChildren;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:78
   */
  public int getNumChild() {
    return numChildren();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:84
   */
  public final int getNumChildNoTransform() {
    return numChildren();
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:90
   */
  public void setChild(ASTNode node, int i) {
  if(children == null) {
      children = new ASTNode[i + 1];
  } else if (i >= children.length) {
      ASTNode c[] = new ASTNode[i << 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = node;
    if(i >= numChildren) numChildren = i+1;
    if(node != null) { node.setParent(this); node.childIndex = i; }
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:105
   */
  public void insertChild(ASTNode node, int i) {
    if(children == null) {
      children = new ASTNode[i + 1];
      children[i] = node;
    } else {
      ASTNode c[] = new ASTNode[children.length + 1];
      System.arraycopy(children, 0, c, 0, i);
      c[i] = node;
      if(i < children.length)
        System.arraycopy(children, i, c, i+1, children.length-i);
      children = c;
    }
    numChildren++;
    if(node != null) { node.setParent(this); node.childIndex = i; }
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:123
   */
  public void removeChild(int i) {
    if(children != null) {
      ASTNode child = (ASTNode)children[i];
      if(child != null) {
        child.setParent(null);
        child.childIndex = -1;
      }
      System.arraycopy(children, i+1, children, i, children.length-i-1);
      numChildren--;
    }
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:137
   */
  public ASTNode getParent() {
    return (ASTNode)parent;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:143
   */
  public void setParent(ASTNode node) {
    parent = node;
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:149
   */
  
  /**
   * @apilevel low-level
   */
  protected ASTNode parent;
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:153
   */
  
  /**
   * @apilevel low-level
   */
  protected ASTNode[] children;
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat ASTNode.ast:168
   */
  public java.util.Iterator<T> iterator() {
  return new java.util.Iterator<T>() {
      private int counter = 0;
      public boolean hasNext() {
        return counter < getNumChild();
      }
      @SuppressWarnings("unchecked") public T next() {
        if(hasNext())
          return (T)getChild(counter++);
        else
          return null;
      }
      public void remove() {
        throw new UnsupportedOperationException();
      }
  };
  }
}
