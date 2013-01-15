package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @apilevel internal
 * @ast class
 * @declaredat :0
 */
public class ASTNode$State extends java.lang.Object {

/**
 * @apilevel internal
 */
  public boolean IN_CIRCLE = false;


/**
 * @apilevel internal
 */
  public int CIRCLE_INDEX = 1;


/**
 * @apilevel internal
 */
  public boolean CHANGE = false;


/**
 * @apilevel internal
 */
  public boolean LAST_CYCLE = false;


/**
 * @apilevel internal
 */
  public boolean RESET_CYCLE = false;


  /**
   * @apilevel internal
   */
  static public class CircularValue {
    Object value;
    int visited = -1;
  }


  public java.util.Set circularEvalSet = new java.util.HashSet(4);


  public java.util.Stack circularEvalStack = new java.util.Stack();


  /**
   * @apilevel internal
   */
  static class CircularEvalEntry {
    ASTNode node;
    String attrName;
    Object parameters;
    public CircularEvalEntry(ASTNode node, String attrName, Object parameters) {
      this.node = node;
      this.attrName = attrName;
      this.parameters = parameters;
    }
    public boolean equals(Object rhs) {
      CircularEvalEntry s = (CircularEvalEntry) rhs;
      if (parameters == null && s.parameters == null)
        return node == s.node && attrName.equals(s.attrName);
      else if (parameters != null && s.parameters != null)
        return node == s.node && attrName.equals(s.attrName) && parameters.equals(s.parameters);
      else
        return false;
    }
    public int hashCode() {
      return node.hashCode();
    }
  }


  public void addEvalEntry(ASTNode node, String attrName, Object parameters) {
    circularEvalSet.add(new CircularEvalEntry(node,attrName,parameters));
  }


  public boolean containsEvalEntry(ASTNode node, String attrName, Object parameters) {
    return circularEvalSet.contains(new CircularEvalEntry(node,attrName,parameters));
  }


  /**
   * @apilevel internal
   */
  static class CircularStackEntry {
    java.util.Set circularEvalSet;
    boolean changeValue;
    public CircularStackEntry(java.util.Set set, boolean change) {
      circularEvalSet = set;
      changeValue = change;
    }
  }


  public void pushEvalStack() {
    circularEvalStack.push(new CircularStackEntry(circularEvalSet, CHANGE));
    circularEvalSet = new java.util.HashSet(4);
    CHANGE = false;
  }


  public void popEvalStack() {
    CircularStackEntry c = (CircularStackEntry) circularEvalStack.pop();
    circularEvalSet = c.circularEvalSet;
    CHANGE = c.changeValue;
  }


  /**
   * @apilevel internal
   */
  static class IdentityHashSet extends java.util.AbstractSet implements java.util.Set {
    public IdentityHashSet(int initialCapacity) {
      map = new java.util.IdentityHashMap(initialCapacity);
      }
    private java.util.IdentityHashMap map;
    private static final Object PRESENT = new Object();
    public java.util.Iterator iterator() { return map.keySet().iterator(); }
    public int size() { return map.size(); }
    public boolean isEmpty() { return map.isEmpty(); }
    public boolean contains(Object o) { return map.containsKey(o); }
    public boolean add(Object o) { return map.put(o, PRESENT)==null; }
    public boolean remove(Object o) { return map.remove(o)==PRESENT; }
    public void clear() { map.clear(); }
  }

public void reset() {
    IN_CIRCLE = false;
    CIRCLE_INDEX = 1;
    CHANGE = false;
    LAST_CYCLE = false;
    circularEvalSet = new java.util.HashSet(4);
    circularEvalStack = new java.util.Stack();
  }


}
