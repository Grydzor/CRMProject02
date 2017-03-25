package web.service;

import web.entity.Product;
import web.entity.Storage;

public interface StorageService extends Service<Storage, Product> {
    Integer getAvailability(Product product);
}
