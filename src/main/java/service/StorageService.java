package service;

import entity.Product;
import entity.Storage;

/**
 * Created by eriol4ik on 24.02.2017.
 */
public interface StorageService extends Service<Storage> {
    Integer getAvailability(Product product);
}
