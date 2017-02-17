package service;

import dao.ProductDAO;
import dao.ProductDAOImpl;
import entity.Product;

/**
 * Created by eriol4ik on 16.02.2017.
 */
public class ProductServiceImpl extends ServiceImpl<Product> implements ProductService {
    private ProductDAO productDAO;
    private static ProductServiceImpl singleton;


    private ProductServiceImpl() {
        super(Product.class);
        productDAO = ProductDAOImpl.getInstance();
    }

    public static ProductServiceImpl getInstance() {
        if (singleton == null) singleton = new ProductServiceImpl();
        return singleton;
    }
}
