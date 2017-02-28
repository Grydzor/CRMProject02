package dao;

import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("userDAO")
public class UserDAOImpl extends DAOImpl<User> implements UserDAO {
    @Autowired
    private SessionFactory factory;

    @Autowired
    protected UserDAOImpl() {}

    @Override

    public User find(String login) {
        return factory.getCurrentSession()
                    .createQuery("FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult();
    }
}
