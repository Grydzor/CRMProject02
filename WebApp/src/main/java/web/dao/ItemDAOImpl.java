package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Item;

@Repository("itemDAO")
public class ItemDAOImpl extends DAOImpl<Item, Long> implements ItemDAO {
    @Autowired
    protected ItemDAOImpl() {}

}
