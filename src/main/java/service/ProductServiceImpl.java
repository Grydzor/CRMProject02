package service;

import dao.ProductDAO;
import dao.ProductDAOImpl;
import entity.Product;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ProductServiceImpl extends ServiceImpl<Product> implements ProductService {
    ProductDAO productDAO;

    public ProductServiceImpl() {
        super(Product.class);
        productDAO = new ProductDAOImpl();
    }
}
