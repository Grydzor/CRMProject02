package service;

import entity.Product;
import entity.Storage;

public interface StorageService extends Service<Storage> {
    Integer getAvailability(Product product);
}
