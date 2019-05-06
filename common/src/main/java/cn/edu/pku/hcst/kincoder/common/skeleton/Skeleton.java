package cn.edu.pku.hcst.kincoder.common.skeleton;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.BlockStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.Stmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.NodeCollector;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ParentCollector;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ReplaceNodeVisitor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Skeleton {
	@Getter private final HoleFactory holeFactory;
	@Getter private final BlockStmt stmts;
	private final Map<Node<?>, Node<?>> parentMap;
	private final List<HoleExpr> holes;

	public static Skeleton create(HoleFactory holeFactory, BlockStmt stmts) {
		var parentMap = ParentCollector.INSTANCE.collect(stmts);

		var holes = NodeCollector.INSTANCE.collect(stmts, HoleExpr.class);

		return new Skeleton(holeFactory, stmts, parentMap, holes);
	}

	public Skeleton fillHole(HoleExpr hole, Expr expr) {
		var visitor = new ReplaceNodeVisitor(hole, expr);
		var newStmts = stmts.accept(visitor, null);
		return Skeleton.create(holeFactory, newStmts);
	}

	public Node<?> parentOf(Node<?> node) {
		return parentMap.get(node);
	}

	public Stmt<?> parentStmtOf(Node<?> node) {
		var parent = parentOf(node);
		if (parent instanceof Stmt<?>) {
			return (Stmt<?>) parent;
		}
		return parentStmtOf(parent);
	}

}
