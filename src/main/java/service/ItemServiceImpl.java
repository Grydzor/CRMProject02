package service;

import dao.ItemDAO;
import dao.ItemDAOImpl;
import entity.Item;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ItemServiceImpl extends ServiceImpl<Item> implements ItemService {
    private ItemDAO itemDAO;
    private static ItemServiceImpl singleton;

    private ItemServiceImpl() {
        super(Item.class);
        itemDAO = ItemDAOImpl.getInstance();
    }

    public static ItemServiceImpl getInstance() {
        if (singleton == null) singleton = new ItemServiceImpl();
        return singleton;
    }
}
