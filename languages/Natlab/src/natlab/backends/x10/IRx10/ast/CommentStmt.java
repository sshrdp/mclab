package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:5
 */
public class CommentStmt extends Stmt implements Cloneable {
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
  public CommentStmt clone() throws CloneNotSupportedException {
    CommentStmt node = (CommentStmt)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public CommentStmt copy() {
      try {
        CommentStmt node = (CommentStmt)clone();
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
  public CommentStmt fullCopy() {
    CommentStmt res = (CommentStmt)copy();
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
   * @declaredat ./astgen/pretty.jadd:392
   */
  String pp(String indent)
{
	StringBuffer x = new StringBuffer();
	String trimmedComment = getComment().trim();
	if(!(("").equals(trimmedComment)))
	{
	x.append("//");
	x.append(getComment());
	x.append("\n");
	}
	return x.toString();
}
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public CommentStmt() {
    super();


  }
  /**
   * @ast method 
   * @declaredat irx10.ast:7
   */
  public CommentStmt(String p0) {
    setComment(p0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  protected int numChildren() {
    return 0;
  }
  /**
   * Setter for lexeme Comment
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setComment(String value) {
    tokenString_Comment = value;
  }
  /**   * @apilevel internal   * @ast method 
   * @declaredat irx10.ast:8
   */
  
  /**   * @apilevel internal   */  protected String tokenString_Comment;
  /**
   * Getter for lexeme Comment
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:13
   */
  public String getComment() {
    return tokenString_Comment != null ? tokenString_Comment : "";
  }
}
