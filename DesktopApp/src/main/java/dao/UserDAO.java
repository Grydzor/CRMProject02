package dao;

import entity.User;

public interface UserDAO extends DAO<User> {
    User find(String login);
}
