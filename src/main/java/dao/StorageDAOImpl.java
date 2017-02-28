package dao;

import entity.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("storageDAO")
public class StorageDAOImpl extends DAOImpl<Storage> implements StorageDAO {
    @Autowired
    private SessionFactory factory;

    protected StorageDAOImpl() {}

    @Override
    public Long create(Storage entity) {
        factory.getCurrentSession().save(entity);
        return entity.getProduct().getId();
    }
}
