package web.dao;

import web.entity.User;

public interface UserDAO extends DAO<User> {
    User find(String login);
}
