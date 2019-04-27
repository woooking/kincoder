package cn.edu.pku.hcst.kincoder.core.nlp;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NlpServerConfig {
    private final String host;
    private final int port;
}
