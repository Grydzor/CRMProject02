package service;

import entity.Product;

import java.util.List;

public interface ProductService extends Service<Product> {
    List<Product> findInRange(Integer from, Integer limit);
}
