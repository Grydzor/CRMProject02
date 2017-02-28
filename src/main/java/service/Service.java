package service;

import java.util.List;

/**
 * Created by eriol4ik on 06/02/2017.
 */
public interface Service<T> {
    Long create(T entity);

    T read(Long id);

    void update(T entity);

    void delete(T entity);

    List<T> findAll();
}
