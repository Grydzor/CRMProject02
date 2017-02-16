package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;

import java.util.List;

public class UserServiceImpl extends ServiceImpl<User> implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl() {
        super(User.class);
        userDAO = new UserDAOImpl();
    }

    @Override
    public User find(String login) {
        return userDAO.find(login);
    }
}
