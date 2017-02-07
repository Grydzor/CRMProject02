package service;

import entity.User;

import java.util.List;

public interface UserService {
    Long add(User user);

    User find(Long id);

    Boolean update(User user);

    Boolean delete(User user);

    User find(String login);

    List<User> findAll();
}
