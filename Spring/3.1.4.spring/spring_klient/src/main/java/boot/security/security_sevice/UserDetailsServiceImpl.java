package boot.security.security_sevice;

//import boot.repository.UserRepository;

import boot.service.RestTemplateService;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
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
        UserDetails userDetails = restTemplateService.findUserByEmail(email);
        return userDetails;
    }
}
