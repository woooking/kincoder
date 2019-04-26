package cn.edu.pku.hcst.kincoder.core.impl.session;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Session {
    private SessionState state;

    public Session(String query, Map<String, String> variables, Set<String> extendedTypes, List<Pair<Skeleton, Double>> skeletons) {
        this.state = new InitState(query, variables, extendedTypes, skeletons);
    }

}
