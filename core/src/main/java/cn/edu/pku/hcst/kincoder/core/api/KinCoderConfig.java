package cn.edu.pku.hcst.kincoder.core.api;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KinCoderConfig {
    @Builder.Default private int patternLimit = 5;
}
