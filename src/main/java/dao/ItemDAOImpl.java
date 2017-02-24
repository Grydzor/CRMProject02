package dao;

import entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("itemDAO")
public class ItemDAOImpl extends DAOImpl<Item> implements ItemDAO {


    @Autowired
    protected ItemDAOImpl() {
        super(Item.class);
    }


}
