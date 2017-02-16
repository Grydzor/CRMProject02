package dao;

import entity.Item;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ItemDAOImpl extends DAOImpl<Item> implements ItemDAO {
    public ItemDAOImpl() {
        super(Item.class);
    }
}
