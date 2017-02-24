package service;

import dao.StorageDAO;
import dao.StorageDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("storageService")
public class StorageServiceImpl extends StorageDAOImpl implements StorageService {
    @Autowired
    @Qualifier("storageDAO")
    private StorageDAO storageDAO;


    private StorageServiceImpl() {
        super();

    }

}
