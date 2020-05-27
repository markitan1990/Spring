package crud.service;

import crud.model.User;

import java.util.List;

public interface UserService {
    void add(User user);

    List<User> listUsers();

    void delete(Long id);

    void edit(User user);
}
