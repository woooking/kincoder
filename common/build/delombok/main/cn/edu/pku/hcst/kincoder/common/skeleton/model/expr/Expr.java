package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;

public interface Expr<E extends Expr<?>> extends Node<E> {
    <A> E accept(HomoVisitor<A> visitor, A arg);
}
