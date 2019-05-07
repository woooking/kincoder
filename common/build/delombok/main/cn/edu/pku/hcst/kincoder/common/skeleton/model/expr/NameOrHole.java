package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;

public interface NameOrHole<N extends NameOrHole<?>> extends Expr<N> {
    <A> N accept(HomoVisitor<A> visitor, A arg);
}
