package service;

import dao.ItemDAO;
import dao.ItemDAOImpl;
import entity.Item;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ItemServiceImpl extends ServiceImpl<Item> implements ItemService {
    ItemDAO itemDAO;

    public ItemServiceImpl() {
        super(Item.class);
        itemDAO = new ItemDAOImpl();
    }
}
