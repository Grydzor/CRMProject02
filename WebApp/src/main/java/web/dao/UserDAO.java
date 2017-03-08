package web.dao;

import web.entity.*;

import java.util.List;

public interface UserDAO {
    Long create(User user);
    User read(Long id);
    void update(User user);
    void delete(User user);
    
    List<User> findAll();
}
