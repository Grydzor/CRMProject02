package web.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, PK extends Serializable> {
    PK create(T entity);

    T read(PK id);

    void update(T entity);

    void delete(T entity);

    List<T> findAll();
}
