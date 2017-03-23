package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import web.dao.ItemDAO;
import web.entity.Item;

@Service("itemService")
public class ItemServiceImpl extends ServiceImpl<Item> implements ItemService {
    @Autowired
    @Qualifier("itemDAO")
    private ItemDAO itemDAO;

    private ItemServiceImpl() {}
}
