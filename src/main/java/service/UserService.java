package service;

import entity.User;

import java.util.List;

public interface UserService extends Service<User> {
    User find(String login);
}
