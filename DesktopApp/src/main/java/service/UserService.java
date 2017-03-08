package service;

import entity.User;

public interface UserService extends Service<User> {
    User find(String login);
}
