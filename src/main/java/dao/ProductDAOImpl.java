package dao;

import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("productDAO")
public class ProductDAOImpl extends DAOImpl<Product> implements ProductDAO {


    @Autowired
    protected ProductDAOImpl() {
        super(Product.class);
    }

}
