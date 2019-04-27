package cn.edu.pku.hcst.kincoder.common.skeleton;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.BlockStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.NodeCollector;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ParentCollector;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ReplaceNodeVisitor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Skeleton {
    private final HoleFactory holeFactory;
    private final BlockStmt stmts;
    private final Map<Node, Node> parentMap;
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

}
