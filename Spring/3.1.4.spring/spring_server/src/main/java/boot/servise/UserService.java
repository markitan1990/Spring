package boot.servise;

import boot.model.User;

import java.util.List;

public interface UserService {
    public List<User> getUsers();

    public void addUser(User user);

    public boolean editUser(User user);

    public boolean delete(Long id);

    public User updateUserRoles(User user);

    public User findUserByEmail(String email);
}
