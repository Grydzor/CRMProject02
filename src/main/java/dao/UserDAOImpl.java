package dao;

import entity.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDAO")
public class UserDAOImpl extends DAOImpl<User> implements UserDAO {


    @Autowired
    protected UserDAOImpl() {
        super(User.class);
    }



    @Override
    public User find(String login) {
        Session session = factory.openSession();
        try {

            List<User> users = session
                    .createQuery("FROM User u WHERE u.login = :login", User.class)
                    .setParameter("login", login)
                    .list();
            return users != null && !users.isEmpty() ? users.get(0) : null;
        } finally {
            session.close();
        }
    }
}
