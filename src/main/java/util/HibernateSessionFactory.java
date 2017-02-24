package util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSessionFactory {
    private static final SessionFactory factory = build();
    private static StandardServiceRegistry registry;

    private HibernateSessionFactory() {
    }

    private static SessionFactory build() {
        registry = new StandardServiceRegistryBuilder()
                .configure("/hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry)
                .getMetadataBuilder()
                .build();

        return metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
