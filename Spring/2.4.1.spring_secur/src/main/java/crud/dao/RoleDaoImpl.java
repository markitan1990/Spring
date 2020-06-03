package crud.dao;

import crud.model.Role;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Role> getListRolesFromUser(Role role) {
        List<Role> roleList = sessionFactory.getCurrentSession()
                .createQuery("from Role where name = :name")
                .setParameter("name", role.getName())
                .getResultList();
        return roleList;
    }
}
