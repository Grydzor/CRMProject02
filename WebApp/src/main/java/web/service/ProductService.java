package web.service;

import web.entity.Product;

import java.util.List;

public interface ProductService extends Service<Product, Long> {
    List<Product> findInRange(Integer from, Integer limit, String by, Boolean asc);
    List<Product> searchInRange(String query, Integer from, Integer limit, String by, Boolean asc);
    Product readWithPictures(Long id);
}
