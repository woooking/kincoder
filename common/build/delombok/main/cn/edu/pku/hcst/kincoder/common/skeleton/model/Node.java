package cn.edu.pku.hcst.kincoder.common.skeleton.model;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.HomoVisitor;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;

public interface Node<N extends Node<?>> {
    <A, R> R accept(Visitor<A, R> visitor, A arg);

    <A> N accept(HomoVisitor<A> visitor, A arg);
}
