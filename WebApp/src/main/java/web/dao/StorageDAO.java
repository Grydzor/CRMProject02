package web.dao;

import web.entity.Product;
import web.entity.Storage;

public interface StorageDAO extends DAO<Storage, Product> {
    Integer getAmount(Product product);
}
