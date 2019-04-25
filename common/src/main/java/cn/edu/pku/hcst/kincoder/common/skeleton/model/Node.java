package cn.edu.pku.hcst.kincoder.common.skeleton.model;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Visitor;

public interface Node {
    <A, R> R accept(Visitor<A, R> visitor, A arg);
}
