package cn.edu.pku.hcst.kincoder.core.impl.session;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InitState implements SessionState {
    private final String query;
    private final Map<String, String> variables;
    private final Set<String> extendedTypes;
    private final List<Pair<Skeleton, Double>> skeletons;

    @Override
    public SessionState selectSkeleton(int id) {

    }

    @Override
    public SessionState answer(String answer) {
        throw new UnsupportedOperationException("Select Skeleton First");
    }

    @Override
    public SessionState undo() {
        throw new UnsupportedOperationException("Select Skeleton First");
    }
}
