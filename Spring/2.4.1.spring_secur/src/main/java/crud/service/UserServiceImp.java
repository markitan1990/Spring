package crud.service;

import crud.dao.RoleDao;
import crud.dao.UserDao;
import crud.model.Role;
import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void add(User user) {
        updateUserRoles(user)
                .setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }


    @Override
    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void edit(User user) {
        userDao.editUser(updateUserRoles(user));
    }

    public User updateUserRoles(User user){
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
