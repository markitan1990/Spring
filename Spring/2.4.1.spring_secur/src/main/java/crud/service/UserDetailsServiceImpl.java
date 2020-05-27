package crud.service;

import crud.dao.UserDetailsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDetailsDao userDetailsDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {

        UserDetails userPrincipal = userDetailsDao.findUserByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = (Set<GrantedAuthority>) userPrincipal.getAuthorities();
        return new org.springframework.security.core.userdetails.User(
                userPrincipal.getUsername(),
                userPrincipal.getPassword(),
                userPrincipal.isEnabled(),
                userPrincipal.isAccountNonExpired(),
                userPrincipal.isCredentialsNonExpired(),
                userPrincipal.isAccountNonLocked(),
                grantedAuthorities
        );
    }
}
