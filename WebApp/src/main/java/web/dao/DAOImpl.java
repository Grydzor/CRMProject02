package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
public abstract class DAOImpl<T, PK extends Serializable> implements DAO<T, PK> {
    @Autowired
    private SessionFactory factory;

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public DAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public PK create(T entity) {
       return (PK) factory.getCurrentSession().save(entity);
    }

    @Override
    public T read(PK id) {
        return factory.getCurrentSession().get(entityClass, id);
    }

    @Override
    public void update(T entity) {
        factory.getCurrentSession().update(entity);
    }

    @Override
    public void delete(T entity) {
        factory.getCurrentSession().delete(entity);
    }

    @Override
    public List<T> findAll() {
        return factory.getCurrentSession()
                .createQuery("FROM " + entityClass.getSimpleName() + " t", entityClass)
                .list();
    }
}
