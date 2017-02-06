package dao;

import entity.User;

import java.util.List;

public interface UserDAO {
    Long create(User user);

    User read(Long id);

    Boolean update(User user);

    Boolean delete(User user);

    List<User> findAll();
}
