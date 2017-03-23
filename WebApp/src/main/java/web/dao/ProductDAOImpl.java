package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Repository("productDAO")
public class ProductDAOImpl extends DAOImpl<Product> implements ProductDAO {
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected ProductDAOImpl() {}

    @Override
    public List<Product> findInRange(Integer from, Integer limit, String by, Boolean asc) {
        if (!by.equals("id") && !by.equals("name") && !by.equals("price")) {
            return new ArrayList<>();
        }

        return factory.getCurrentSession()
                .createQuery("FROM Product p ORDER BY p." + by + (asc ? " ASC" : " DESC"), Product.class)
                .setFirstResult(from)
                .setMaxResults(limit)
                .list();
    }

    @Override
    public List<Product> searchInRange(String query, Integer from, Integer limit, String by, Boolean asc) {
        if (!by.equals("id") && !by.equals("name") && !by.equals("price")) {
            return new ArrayList<>();
        }

        return factory.getCurrentSession()
                .createQuery("FROM Product p WHERE p.name LIKE :query" + " ORDER BY p." + by + (asc ? " ASC " : " DESC "), Product.class)
                .setParameter("query", "%" + query + "%")
                .setFirstResult(from)
                .setMaxResults(limit)
                .list();
    }
}
