package cn.edu.pku.hcst.kincoder.kg.builder;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KnowledgeGraphBuilderConfig {
    private String jdkPath;
    private String projectPath;
}
