package cn.edu.pku.hcst.kincoder.common.skeleton;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;

public class HoleFactory {
    private int nextId = 0;

    public HoleExpr create() {
        return new HoleExpr(nextId++);
    }
}
