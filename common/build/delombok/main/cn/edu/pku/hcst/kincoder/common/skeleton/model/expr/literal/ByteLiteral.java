package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class ByteLiteral implements LiteralExpr<Byte> {
    private Byte value;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> LiteralExpr<Byte> accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
