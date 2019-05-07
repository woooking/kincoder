package cn.edu.pku.hcst.kincoder.common.skeleton.model.expr;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;

public interface Callable {
    String getQualifiedSignature();

    int findArgIndex(Arg arg);
}
