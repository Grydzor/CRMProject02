package web.dao;

import java.util.List;

public interface DAO<T> {
    Long create(T entity);

    T read(Long id);

    void update(T entity);

    void delete(T entity);

    List<T> findAll();
}
