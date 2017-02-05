package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import util.HibernateSessionFactory;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    @Override
    public Long add(User user) {
        return userDAO.create(user);
    }
}
