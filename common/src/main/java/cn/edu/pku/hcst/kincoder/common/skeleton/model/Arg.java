package cn.edu.pku.hcst.kincoder.common.skeleton.model;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.Expr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;

@Value
public class Arg implements Node {
    private final Type type;
    private final Expr value;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
