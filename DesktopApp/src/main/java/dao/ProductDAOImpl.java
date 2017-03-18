package dao;

import dao.DAOImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import entity.Product;

import java.util.List;

@Repository("productDAO")
public class ProductDAOImpl extends DAOImpl<Product> implements ProductDAO {
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected ProductDAOImpl() {}

    @Override
    public List<Product> findInRange(Integer from, Integer limit) {
        return factory.getCurrentSession()
                .createQuery("FROM Product p ORDER BY p.id", Product.class)
                .setFirstResult(from)
                .setMaxResults(limit)
                .list();
    }
}
