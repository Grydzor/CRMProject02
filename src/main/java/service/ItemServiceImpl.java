package service;

import dao.ItemDAO;
import dao.ItemDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("itemService")
public class ItemServiceImpl extends ItemDAOImpl implements ItemService {

    @Autowired
    @Qualifier("itemDAO")
    private ItemDAO itemDAO;

    private ItemServiceImpl() {
        super();
    }

}
