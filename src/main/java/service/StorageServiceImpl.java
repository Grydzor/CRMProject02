package service;

import dao.StorageDAO;
import dao.StorageDAOImpl;
import entity.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import util.ApplicationContextFactory;

@Service("storageService")
public class StorageServiceImpl extends ServiceImpl<Storage> implements StorageService {
    @Autowired
    @Qualifier("storageDAO")
    private StorageDAO storageDAO;

    private StorageServiceImpl() {}
}
