package dao;

import entity.Product;

import java.util.List;

public interface ProductDAO extends DAO<Product> {
    List<Product> findInRange(Integer from, Integer to);
}
