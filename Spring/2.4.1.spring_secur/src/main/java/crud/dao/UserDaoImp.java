package crud.dao;

import crud.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        List<User> list = sessionFactory.getCurrentSession().createQuery("from User").getResultList();
        return list;
    }

    @Override
    public void delete(Long id) {
        User user = sessionFactory.getCurrentSession().find(User.class, id);
        sessionFactory.getCurrentSession().remove(user);
    }

    @Override
    public void editUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User where name = :name");
        query.setParameter("name", username);
        return query.getSingleResult();
    }
}
