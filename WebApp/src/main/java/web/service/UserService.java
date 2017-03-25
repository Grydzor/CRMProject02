package web.service;

import web.entity.User;

public interface UserService extends Service<User, Long> {
    User find(String login);
}
