package natlab.backends.x10.codegen;

import natlab.backends.x10.IRx10.ast.CommentStmt;
import natlab.backends.x10.IRx10.ast.StmtBlock;
import natlab.tame.tir.TIRCommentStmt;
import natlab.tame.tir.TIRForStmt;

public class Comments {

	public static void handleTIRComment(TIRCommentStmt node,
			IRx10ASTGenerator target, StmtBlock block) {
		CommentStmt comment = new CommentStmt();
		comment.setComment(node.getNodeString());
		block.addStmt(comment);

	}
}
