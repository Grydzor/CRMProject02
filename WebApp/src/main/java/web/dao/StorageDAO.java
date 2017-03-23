package web.dao;

import web.entity.Product;
import web.entity.Storage;

public interface StorageDAO extends DAO<Storage> {
    Integer getAmount(Product product);
}
