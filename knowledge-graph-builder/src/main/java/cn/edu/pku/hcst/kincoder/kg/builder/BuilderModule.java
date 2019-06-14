package cn.edu.pku.hcst.kincoder.kg.builder;

import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class BuilderModule extends AbstractModule {
    @Override
    public void configure() {
    }

    @Provides
    @Singleton
    public KnowledgeGraphConfig knowledgeGraphConfig() {
        return KnowledgeGraphConfig.builder()
            .uri("bolt://162.105.88.99")
            .username("neo4j")
            .password("neo4jpoi")
            .build();
    }
}
