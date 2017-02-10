package service;

import dao.DAO;
import dao.DAOImpl;
import entity.User;

import java.util.List;

/**
 * Created by eriol4ik on 06/02/2017.
 */
public class ServiceImpl implements Service {
    private DAO dao;

    public ServiceImpl() {
        dao = new DAOImpl();
    }

    @Override
    public <T> Long add(T entity) {
        return dao.create(entity);
    }

    @Override
    public <T> T read(Class<T> type, Long id) {
        return dao.read(type, id);
    }

    @Override
    public <T> Boolean update(T entity) {
        return dao.update(entity);
    }

    @Override
    public <T> Boolean delete(T entity) {
        return dao.delete(entity);
    }

    @Override
    public <T> List<T> findAll(Class<T> type) {
        return dao.findAll(type);
    }

    @Override
    public User find(String login) {
        return dao.find(login);
    }
}
