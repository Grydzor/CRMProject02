package web.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDAO;
import web.entity.User;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory factory;

    @Override
    @Transactional
    public Long create(User user) {
        return null;
    }

    @Override
    @Transactional
    public User read(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void update(User user) {

    }

    @Override
    @Transactional
    public void delete(User user) {

    }

    @Override
    @Transactional
    public List<User> findAll() {
        return factory.getCurrentSession().createQuery("FROM User u", User.class).list();
    }
}
