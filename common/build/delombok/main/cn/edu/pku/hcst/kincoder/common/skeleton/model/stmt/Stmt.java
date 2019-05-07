package cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;

public interface Stmt<S extends Stmt<?>> extends Node<S> {
    <A> S accept(HomoVisitor<A> visitor, A arg);
}
