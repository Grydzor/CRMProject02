package service;

import entity.Product;

import java.util.List;

public interface ProductService extends Service<Product> {
    List<Product> findInRange(Integer from, Integer limit, String by, Boolean asc);
    List<Product> searchInRange(String query, Integer from, Integer limit, String by, Boolean asc);
}
