package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import web.dao.DAO;

import java.util.List;

public class ServiceImpl<T> implements Service<T> {
    @Autowired
    private DAO<T> dao;

    public ServiceImpl() {}

    @Override
    @Transactional
    public Long create(T entity) {
        return dao.create(entity);
    }

    @Override
    @Transactional
    public T read(Long id) {
        return dao.read(id);
    }

    @Override
    @Transactional
    public void update(T entity) {
        dao.update(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    @Transactional
    public List<T> findAll() {
        return dao.findAll();
    }
}
