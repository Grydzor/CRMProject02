package web.service;

import web.entity.User;

public interface UserService extends Service<User> {
    User find(String login);
}
