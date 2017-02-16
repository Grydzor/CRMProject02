package service;

import dao.DAO;
import dao.DAOImpl;
import entity.User;

import java.util.List;

/**
 * Created by eriol4ik on 06/02/2017.
 */
public class ServiceImpl<T> implements Service<T> {
    private DAO<T> dao;

    public ServiceImpl(Class<T> entityClass) {
        dao = new DAOImpl<>(entityClass);
    }

    @Override
    public Long add(T entity) {
        return dao.create(entity);
    }

    @Override
    public T read(Long id) {
        return dao.read(id);
    }

    @Override
    public Boolean update(T entity) {
        return dao.update(entity);
    }

    @Override
    public Boolean delete(T entity) {
        return dao.delete(entity);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }
}
