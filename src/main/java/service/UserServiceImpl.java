package service;

import dao.UserDAO;
import dao.UserDAOImpl;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends UserDAOImpl implements UserService {

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;


    private UserServiceImpl() {
        super();
    }

    @Override
    public User find(String login) {
        return userDAO.find(login);
    }
}
