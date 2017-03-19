package dao;

import dao.DAOImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import entity.Product;

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
            System.out.println(by.equals("id"));
            System.out.println(by.equals("name"));
            System.out.println(by.equals("price"));

            return new ArrayList<>();
        }

        System.out.println("here");
        return factory.getCurrentSession()
                .createQuery("FROM Product p ORDER BY p." + by + (asc ? " ASC" : " DESC"), Product.class)
                .setFirstResult(from)
                .setMaxResults(limit)
                .list();
    }
}
