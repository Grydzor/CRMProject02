package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.entity.Product;

@Repository("productDAO")
public class ProductDAOImpl extends DAOImpl<Product> implements ProductDAO {
    @Autowired
    protected ProductDAOImpl() {}
}
