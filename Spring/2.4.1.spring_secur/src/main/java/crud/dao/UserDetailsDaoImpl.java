package crud.dao;

import crud.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.TypedQuery;

public class UserDetailsDaoImpl implements UserDetailsDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User where name = :name");
        query.setParameter("name", username);
        return query.getSingleResult();
    }
}
