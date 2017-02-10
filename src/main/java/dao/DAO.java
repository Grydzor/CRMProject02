package dao;

import entity.User;

import java.util.List;

/**
 * Created by eriol4ik on 06/02/2017.
 */
public interface DAO {
    <T> Long create(T entity);

    <T> T read(Class<T> entityClass, Long id);

    <T> Boolean update(T entity);

    <T> Boolean delete(T entity);

    <T> List<T> findAll(Class<T> type);

    User find(String login);
}
