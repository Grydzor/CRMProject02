package service;

import java.util.List;

/**
 * Created by eriol4ik on 06/02/2017.
 */
public interface Service<T> {
    Long create(T entity);

    T read(Long id);

    Boolean update(T entity);

    Boolean delete(T entity);

    List<T> findAll();
}
