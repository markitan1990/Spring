package boot.repository;

import boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

//    User findUserByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
}
