package dao;

import entity.Item;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ItemDAOImpl extends DAOImpl<Item> implements ItemDAO {
    private SessionFactory factory;
    private static ItemDAOImpl singleton;

    private ItemDAOImpl() {
        super(Item.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }

    public static ItemDAOImpl getInstance() {
        if (singleton == null) singleton = new ItemDAOImpl();
        return singleton;
    }
}
