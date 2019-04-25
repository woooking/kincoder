package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class StaticFieldAccessExpr implements Expr {
    private final Type receiverType;
    private final Type targetType;
    private final NameOrHole name;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
