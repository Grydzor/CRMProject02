package dao;

import entity.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository("storageDAO")
public class StorageDAOImpl extends DAOImpl<Storage> implements StorageDAO {

    protected StorageDAOImpl() {
        super(Storage.class);
    }

    @Override
    public Long create(Storage entity) {
        Session session = factory.openSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity.getProduct().getId();
        } catch (HibernateException he) {
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
        }
    }
}
