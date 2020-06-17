package boot.dao;


import boot.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    List<User> getUsers();

    void delete(Long id);

    void editUser(User user);

    User findUserByEmail(String email);

}
