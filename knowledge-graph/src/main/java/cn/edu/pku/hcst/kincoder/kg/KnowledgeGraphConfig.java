package cn.edu.pku.hcst.kincoder.kg;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KnowledgeGraphConfig {
    @Builder.Default private String uri = "bolt://162.105.88.99";
    @Builder.Default private String username = "neo4j";
    @Builder.Default private String password = "neo4jpoi";
}
