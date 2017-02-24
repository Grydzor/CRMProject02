package dao;

import entity.Storage;
import org.springframework.stereotype.Repository;

@Repository("storageDAO")
public class StorageDAOImpl extends DAOImpl<Storage> implements StorageDAO {

    protected StorageDAOImpl() {
        super(Storage.class);
    }


}
