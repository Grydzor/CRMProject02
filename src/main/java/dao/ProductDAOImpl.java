package dao;

import entity.Product;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ProductDAOImpl extends DAOImpl<Product> implements ProductDAO {
    private SessionFactory factory;
    private static ProductDAOImpl singleton;

    private ProductDAOImpl() {
        super(Product.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }

    public static ProductDAOImpl getInstance() {
        if (singleton == null) singleton = new ProductDAOImpl();
        return singleton;
    }
}
