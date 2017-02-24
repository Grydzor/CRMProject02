package service;

import dao.StorageDAO;
import dao.StorageDAOImpl;
import entity.Storage;

/**
 * Created by eriol4ik on 24.02.2017.
 */
public class StorageServiceImpl extends ServiceImpl<Storage> implements StorageService {
    private StorageDAO storageDAO;
    private static StorageServiceImpl singleton;

    private StorageServiceImpl() {
        super(Storage.class);
        storageDAO = StorageDAOImpl.getInstance();
    }

    public static StorageServiceImpl getInstance() {
        if (singleton == null) singleton = new StorageServiceImpl();
        return singleton;
    }
}
