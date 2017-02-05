package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static final SessionFactory factory = build();
    private static StandardServiceRegistry registry;

    private static SessionFactory build() {
        Configuration config = new Configuration().
                configure();

        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder();
        builder.applySettings(config.getProperties());

        registry = builder.build();

        return config.buildSessionFactory(registry);
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
