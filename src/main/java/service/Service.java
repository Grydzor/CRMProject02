package service;

import java.util.List;

/**
 * Created by eriol4ik on 06/02/2017.
 */
public interface Service {
    <T> Long add(T entity);

    <T> T read(Class<T> type, Long id);

    <T> Boolean update(T entity);

    <T> Boolean delete(T entity);

    <T> List<T> findAll(Class<T> type);
}
