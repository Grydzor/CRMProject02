package web.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ProductDAO;
import web.entity.Product;

import java.util.List;

@Service("productService")
public class ProductServiceImpl extends ServiceImpl<Product, Long> implements ProductService {
    @Autowired
    @Qualifier("productDAO")
    private ProductDAO productDAO;

    private ProductServiceImpl() {}

    @Override
    @Transactional
    public List<Product> findInRange(Integer from, Integer limit, String by, Boolean asc) {
        List<Product> products = productDAO.findInRange(from, limit, by, asc);
        for (Product product : products) {
            Hibernate.initialize(product.getPictureList());
        }
        return products;
    }

    @Override
    @Transactional
    public List<Product> searchInRange(String query, Integer from, Integer limit, String by, Boolean asc) {
        return productDAO.searchInRange(query, from, limit, by, asc);
    }

    @Override
    public Product readWithPictures(Long id) {
        Product product = productDAO.read(id);
        Hibernate.initialize(product.getPictureList());
        return product;
    }
}
