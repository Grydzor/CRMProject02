package service;

import dao.ProductDAO;
import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl extends ServiceImpl<Product> implements ProductService {
    @Autowired
    @Qualifier("productDAO")
    private ProductDAO productDAO;

    private ProductServiceImpl() {}
}
