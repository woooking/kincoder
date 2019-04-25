package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class AssignExpr implements Expr {
    private final Expr source;
    private final Expr target;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
