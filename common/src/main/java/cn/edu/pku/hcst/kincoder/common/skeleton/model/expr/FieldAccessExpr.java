package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class FieldAccessExpr implements Expr {
    private final ReferenceType receiverType;
    private final Expr receiver;
    private final NameOrHole name;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
