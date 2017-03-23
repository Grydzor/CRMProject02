package web.service;

import web.entity.Product;
import web.entity.Storage;

public interface StorageService extends Service<Storage> {
    Integer getAvailability(Product product);
}
