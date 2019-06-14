package cn.edu.pku.hcst.kincoder.pattern.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PatternConfig {
    private final String clientCodeDir;
    private final boolean debug;
}
