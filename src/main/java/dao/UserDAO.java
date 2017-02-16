package dao;

import entity.User;

import java.util.List;

public interface UserDAO extends DAO<User> {
    User find(String login);
}
