package dao;

import entity.Product;
import entity.Storage;

/**
 * Created by eriol4ik on 24.02.2017.
 */
public interface StorageDAO extends DAO<Storage> {
    Integer getAmount(Product product);
}
