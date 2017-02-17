package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;

import java.util.List;

public class UserServiceImpl extends ServiceImpl<User> implements UserService {
    private UserDAO userDAO;
    private static UserServiceImpl singleton;

    private UserServiceImpl() {
        super(User.class);
        userDAO = UserDAOImpl.getInstance();
    }

    public static UserServiceImpl getInstance() {
        if (singleton == null) singleton = new UserServiceImpl();
        return singleton;
    }

    @Override
    public User find(String login) {
        return userDAO.find(login);
    }
}
