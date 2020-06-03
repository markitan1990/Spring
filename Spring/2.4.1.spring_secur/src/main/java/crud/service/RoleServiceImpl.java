package crud.service;

import crud.dao.RoleDao;
import crud.model.Role;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Transactional
    @Override
    public User updateUserRoles(User user) {
        Set<Role> set = new HashSet<>(); // If lost of roles is empty, will be add default role "user"
        if (user.getRoles().size() == 0) {
            set.add(new Role("user"));
            user.setRoles(set);
        }
        Set<Role> userRoles = new HashSet<>();
        for (Role role : user.getRoles()) { // If those roles already exist in DB will return roles from DB
            List<Role> roleList = roleDao.getListRolesFromUser(role);
            if (roleList.size() == 0) {
                userRoles.add(new Role(role.getName()));
            } else {
                userRoles.add(roleList.get(0));
            }
        }
        user.setRoles(userRoles);
        return user;
    }
}
