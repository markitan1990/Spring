package crud.dao;

import crud.model.User;

public interface UserDetailsDao {
    User findUserByUsername(String username);
}
