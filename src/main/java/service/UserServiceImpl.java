package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ApplicationContextFactory;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<User> implements UserService {
    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    private UserServiceImpl() {}

    @Override
    @Transactional
    public User find(String login) {
        return userDAO.find(login);
    }
}
