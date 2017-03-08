package service;

import java.util.List;

public interface Service<T> {
    Long create(T entity);

    T read(Long id);

    void update(T entity);

    void delete(T entity);

    List<T> findAll();
}
