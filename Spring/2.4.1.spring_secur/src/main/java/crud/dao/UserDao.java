package crud.dao;

import crud.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);

    List<User> listUsers();

    void delete(Long id);

    void editUser(User user);
}
