package cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class WhileStmt implements Stmt {
    private final Expr cond;
    private final BlockStmt body;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
