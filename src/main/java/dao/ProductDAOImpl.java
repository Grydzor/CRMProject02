package dao;

import entity.Product;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ProductDAOImpl extends DAOImpl<Product> implements ProductDAO {
    public ProductDAOImpl() {
        super(Product.class);
    }
}
