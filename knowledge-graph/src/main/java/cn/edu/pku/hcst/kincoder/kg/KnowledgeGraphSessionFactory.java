package cn.edu.pku.hcst.kincoder.kg;

import com.google.inject.Inject;
import org.jetbrains.annotations.Nullable;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.Objects;

public class KnowledgeGraphSessionFactory {
    private final Configuration configuration;
    @Nullable
    private volatile SessionFactory sessionFactory;

    @Inject
    public KnowledgeGraphSessionFactory(KnowledgeGraphConfig config) {
        this.configuration = new Configuration.Builder()
            .uri(config.getUri())
            .credentials(config.getUsername(), config.getPassword())
            .build();
    }

    private SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (KnowledgeGraphSessionFactory.class) {
                if (sessionFactory == null) {
                    sessionFactory = new SessionFactory(configuration, "cn.edu.pku.hcst.kincoder.kg.entity");
                }
            }
        }
        return sessionFactory;
    }

    public Session createSession() {
        return getSessionFactory().openSession();
    }

    public void close() {
        synchronized (KnowledgeGraphSessionFactory.class) {
            if (sessionFactory != null) {
                Objects.requireNonNull(sessionFactory).close();
                this.sessionFactory = null;
            }
        }
    }
}
