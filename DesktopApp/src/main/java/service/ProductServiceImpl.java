package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dao.ProductDAO;
import entity.Product;

import java.util.List;

@Service("productService")
public class ProductServiceImpl extends ServiceImpl<Product> implements ProductService {
    @Autowired
    @Qualifier("productDAO")
    private ProductDAO productDAO;

    private ProductServiceImpl() {}

    @Override
    @Transactional
    public List<Product> findInRange(Integer from, Integer limit, String by, Boolean asc) {
        return productDAO.findInRange(from, limit, by, asc);
    }

    @Override
    @Transactional
    public List<Product> searchInRange(String query, Integer from, Integer limit, String by, Boolean asc) {
        return productDAO.searchInRange(query, from, limit, by, asc);
    }
}
