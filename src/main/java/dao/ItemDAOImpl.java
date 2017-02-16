package dao;

import entity.Item;
import org.hibernate.SessionFactory;
import util.HibernateSessionFactory;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ItemDAOImpl extends DAOImpl<Item> implements ItemDAO {
    SessionFactory factory;

    public ItemDAOImpl() {
        super(Item.class);
        factory = HibernateSessionFactory.getSessionFactory();
    }
}
