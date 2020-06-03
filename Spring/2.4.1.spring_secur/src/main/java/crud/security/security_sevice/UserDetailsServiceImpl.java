package crud.security.security_sevice;

import crud.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDetailsDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userDetailsDao.findUserByUsername(username);
    }
}
