package natlab.backends.x10.IRx10.ast;

import natlab.backends.x10.IRx10.ast.Args;
import natlab.backends.x10.IRx10.ast.List;
import natlab.backends.x10.IRx10.ast.PPHelper;
import natlab.backends.x10.IRx10.ast.Stmt;
import java.util.*;

/**
 * @ast node
 * @declaredat irx10.ast:6
 */
public class DeclStmt extends Stmt implements Cloneable {
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
  public DeclStmt clone() throws CloneNotSupportedException {
    DeclStmt node = (DeclStmt)super.clone();
    return node;
  }
  /**
   * @apilevel internal
   */
  @SuppressWarnings({"unchecked", "cast"})
  public DeclStmt copy() {
      try {
        DeclStmt node = (DeclStmt)clone();
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
  public DeclStmt fullCopy() {
    DeclStmt res = (DeclStmt)copy();
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
   * @declaredat ./astgen/pretty.jadd:28
   */
  String pp(String indent){
		StringBuffer x = new StringBuffer();

		if (null != getLHS()) {
			/**
			 * It is not a list assignment
			 */

			if (null == getLHS().getShape()) {
				/* Scalar variable */
				x.append("var " + getLHS().getName() + ": "
						+ getLHS().getType().getName());
				if (hasRHS()){
				x.append(" = ");
				x.append(getRHS().pp(""));
				}
				x.append(";\n");

			}

			else if (null != getLHS().getShape()) {
				boolean tf = true;
				for (int i = 0; i < getLHS().getShape().size(); i++) {
					if(null != getLHS().getShape().get(i))
					tf &= ("1").equals(getLHS().getShape().get(i).toString());
				}
				if (tf) {

					/* Scalar variable */
					x.append("var " + getLHS().getName() + ": "
							+ getLHS().getType().getName());
					if (hasRHS()){
						x.append(" = ");
						x.append(getRHS().pp(""));
						}
						x.append(";\n");
				}

			 else // array
			{

				x.append("val " + getLHS().getName() + " = " + "new Array["
						+ getLHS().getType().getName() + "]" + "("
						+ PPHelper.makeRange(getLHS().getShape()) + ", "
						+ getRHS().pp("") + ");\n");
			}
		} //FIX THIS IN aspect
			

		} else {
			/**
			 * TODO Case when assigned to a list
			 */
		}
		return x.toString();
	}
  /**
   * @ast method 
   * @declaredat irx10.ast:1
   */
  public DeclStmt() {
    super();

    setChild(new Opt(), 0);
    setChild(new Opt(), 2);

  }
  /**
   * @ast method 
   * @declaredat irx10.ast:9
   */
  public DeclStmt(Opt<MultiDeclLHS> p0, IDInfo p1, Opt<Exp> p2) {
    setChild(p0, 0);
    setChild(p1, 1);
    setChild(p2, 2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:17
   */
  protected int numChildren() {
    return 3;
  }
  /**
   * Setter for MultiDeclLHSOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setMultiDeclLHSOpt(Opt<MultiDeclLHS> opt) {
    setChild(opt, 0);
  }
  /**
   * Does this node have a MultiDeclLHS child?
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public boolean hasMultiDeclLHS() {
    return getMultiDeclLHSOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child MultiDeclLHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public MultiDeclLHS getMultiDeclLHS() {
    return (MultiDeclLHS)getMultiDeclLHSOpt().getChild(0);
  }
  /**
   * Setter for optional child MultiDeclLHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void setMultiDeclLHS(MultiDeclLHS node) {
    getMultiDeclLHSOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<MultiDeclLHS> getMultiDeclLHSOpt() {
    return (Opt<MultiDeclLHS>)getChild(0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<MultiDeclLHS> getMultiDeclLHSOptNoTransform() {
    return (Opt<MultiDeclLHS>)getChildNoTransform(0);
  }
  /**
   * Setter for LHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setLHS(IDInfo node) {
    setChild(node, 1);
  }
  /**
   * Getter for LHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public IDInfo getLHS() {
    return (IDInfo)getChild(1);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:18
   */
  public IDInfo getLHSNoTransform() {
    return (IDInfo)getChildNoTransform(1);
  }
  /**
   * Setter for RHSOpt
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:5
   */
  public void setRHSOpt(Opt<Exp> opt) {
    setChild(opt, 2);
  }
  /**
   * Does this node have a RHS child?
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:12
   */
  public boolean hasRHS() {
    return getRHSOpt().getNumChild() != 0;
  }
  /**
   * Getter for optional child RHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:19
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Exp getRHS() {
    return (Exp)getRHSOpt().getChild(0);
  }
  /**
   * Setter for optional child RHS
   * @apilevel high-level
   * @ast method 
   * @declaredat irx10.ast:27
   */
  public void setRHS(Exp node) {
    getRHSOpt().setChild(node, 0);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:37
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Exp> getRHSOpt() {
    return (Opt<Exp>)getChild(2);
  }
  /**
   * @apilevel low-level
   * @ast method 
   * @declaredat irx10.ast:44
   */
  @SuppressWarnings({"unchecked", "cast"})
  public Opt<Exp> getRHSOptNoTransform() {
    return (Opt<Exp>)getChildNoTransform(2);
  }
}
