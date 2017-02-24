package service;

import dao.ProductDAO;
import dao.ProductDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl extends ProductDAOImpl implements ProductService {
    @Autowired
    @Qualifier("productDAO")
    private ProductDAO productDAO;


    private ProductServiceImpl() {
        super();
    }

}
