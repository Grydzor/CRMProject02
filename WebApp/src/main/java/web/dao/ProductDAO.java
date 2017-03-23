package web.dao;

import web.entity.Product;

import java.util.List;

public interface ProductDAO extends DAO<Product> {
    List<Product> findInRange(Integer from, Integer to, String by, Boolean asc);
    List<Product> searchInRange(String query, Integer from, Integer limit, String by, Boolean asc);
}
