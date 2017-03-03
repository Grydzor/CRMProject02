package service;

import dao.StorageDAO;
import dao.StorageDAOImpl;
import entity.Product;
import entity.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ApplicationContextFactory;

@Service("storageService")
public class StorageServiceImpl extends ServiceImpl<Storage> implements StorageService {
    @Autowired
    @Qualifier("storageDAO")
    private StorageDAO storageDAO;

    private StorageServiceImpl() {}

    @Override
    @Transactional
    public Integer getAvailability(Product product) {
        return storageDAO.getAmount(product);
    }
}
