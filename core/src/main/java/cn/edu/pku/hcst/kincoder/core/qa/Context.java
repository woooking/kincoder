package cn.edu.pku.hcst.kincoder.core.qa;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpContext;
import cn.edu.pku.hcst.kincoder.core.utils.CodeUtil;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class Context {
    private final String query;
    private final Set<Pair<String, Type>> variables;
    @Wither
    private final Skeleton skeleton;
    private final List<String> extendedTypes;
    private final NlpContext nlpCtx;

    public Set<String> findVariables(Type type) {
        return variables.stream()
            .filter(p -> CodeUtil.isAssignable(p.getRight(), type))
            .map(Pair::getLeft)
            .collect(Collectors.toSet());
    }
}
