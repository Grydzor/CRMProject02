package web.service;

import java.io.Serializable;
import java.util.List;

public interface Service<T, PK extends Serializable> {
    PK create(T entity);

    T read(PK id);

    void update(T entity);

    void delete(T entity);

    List<T> findAll();
}
