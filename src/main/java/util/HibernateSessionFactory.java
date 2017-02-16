package util;


import entity.Employee;
import entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static final SessionFactory factory = build();
    private static StandardServiceRegistry registry;

    private static SessionFactory build() {
        registry = new StandardServiceRegistryBuilder()
                .configure("/hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(User.class)
                .getMetadataBuilder()
                .build();

        return metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
