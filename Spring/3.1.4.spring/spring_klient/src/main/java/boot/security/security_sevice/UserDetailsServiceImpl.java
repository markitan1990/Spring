package boot.security.security_sevice;

import boot.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private RestTemplateService restTemplateService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return restTemplateService.findUserByEmail(email);
    }
}
