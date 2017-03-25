package web.dao;

import web.entity.User;

public interface UserDAO extends DAO<User, Long> {
    User find(String login);
}
