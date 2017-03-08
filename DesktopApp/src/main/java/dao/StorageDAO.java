package dao;

import entity.Product;
import entity.Storage;

public interface StorageDAO extends DAO<Storage> {
    Integer getAmount(Product product);
}
