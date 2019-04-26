package cn.edu.pku.hcst.kincoder.core.impl.session;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SessionState {
    SessionState selectSkeleton(int id);

    SessionState answer(String answer);

    SessionState undo();


}
