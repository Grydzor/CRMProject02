package web.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Product;
import web.entity.Storage;

import java.util.List;

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

    @Override
    public Integer getAmount(Product product) {
        Query<Integer> query = factory.getCurrentSession()
                .createQuery("SELECT s.amount FROM Storage s where s.product = :product", Integer.class);
        query.setParameter("product", product);
        List<Integer> list = query.list();
        return list.isEmpty() ? null : list.get(0);
    }
}
