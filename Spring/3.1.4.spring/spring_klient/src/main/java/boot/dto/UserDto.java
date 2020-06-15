package boot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails{
    private Long id;
    private String firstName;
    private String lastName;
    private Long age;
    private String email;
    private String password;
    private Set<RoleDto> roles;

//    public UserDto(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }
//    public UserDto(String email ) {
//        this.email = email;
//    }
//
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities
                = new HashSet<>();
        for (RoleDto role : getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
