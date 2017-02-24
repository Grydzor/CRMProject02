package dao;

import entity.Storage;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

/**
 * Created by eriol4ik on 24.02.2017.
 */
public class StorageDAOImpl extends DAOImpl<Storage> implements StorageDAO {
    private SessionFactory factory;
    private static StorageDAOImpl singleton;

    private StorageDAOImpl() {
        super(Storage.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }

    public static StorageDAOImpl getInstance() {
        if (singleton == null) singleton = new StorageDAOImpl();
        return singleton;
    }
}
