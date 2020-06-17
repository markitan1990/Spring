package boot.servise;

import boot.dao.UserDao;
import boot.model.Role;
import boot.model.User;
import boot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Transactional
    public void addUser(User user) {
        userDao.add(updateUserRoles(user));
    }

    @Transactional
    public boolean editUser(User user) {
        try {
            userDao.editUser(updateUserRoles(user));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean delete(Long id) {
        try {
            userDao.delete(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public User updateUserRoles(User user) {
        Set<Role> set = new HashSet<>(); // If lost of roles is empty, will be add default role "user"
        if (user.getRoles().size() == 0) {
            set.add(new Role("user"));
            user.setRoles(set);
        }
        Set<Role> userRoles = new HashSet<>();
        for (Role role : user.getRoles()) { // If those roles already exist in DB will return roles from DB
            List<Role> roleList = roleRepository.findByRoleName(role.getRoleName());
            if (roleList.size() == 0) {
                roleRepository.save(role);
                userRoles.add(role);
            } else {
                userRoles.add(roleList.get(0));
            }
        }
        user.setRoles(userRoles);
        return user;
    }

    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
}
