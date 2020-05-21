package crud.dao;

import crud.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sessionFactory.getCurrentSession()
                .createQuery("delete from User where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void editUser(User user) {
        sessionFactory.getCurrentSession()
                .update(user);
    }
}
