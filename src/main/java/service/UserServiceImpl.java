package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    @Override
    public Long add(User user) {
        return userDAO.create(user);
    }

    @Override
    public User find(Long id) {
        return userDAO.read(id);
    }

    @Override
    public Boolean update(User user) {
        return userDAO.update(user);
    }

    @Override
    public Boolean delete(User user) {
        return userDAO.delete(user);
    }

    @Override
    public User find(String login) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }
}
