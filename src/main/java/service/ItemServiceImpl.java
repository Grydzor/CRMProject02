package service;

import dao.ItemDAO;
import dao.ItemDAOImpl;
import entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import util.ApplicationContextFactory;

@Service("itemService")
public class ItemServiceImpl extends ServiceImpl<Item> implements ItemService {
    @Autowired
    @Qualifier("itemDAO")
    private ItemDAO itemDAO;

    private ItemServiceImpl() {}
}
