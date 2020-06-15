package boot.service;

import boot.dto.UserDto;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestTemplateService {

    private RestTemplate restTemplate = new RestTemplate();

    public void addUser(UserDto userDto) {
        restTemplate.postForObject("http://localhost:8080/addUser", new HttpEntity<UserDto>(userDto), UserDto.class);
    }

    public List<UserDto> getUsers() {
        return restTemplate.getForObject("http://localhost:8080/getUsers", List.class);
    }

    public UserDto findUserByEmail(String email) {
        return restTemplate.getForObject("http://localhost:8080/findUser?email=" + email, UserDto.class);
    }

    public boolean delete(Long id) {
        return restTemplate.getForObject("http://localhost:8080/deleteUser/" + id, UserDto.class) != null;
    }

    public boolean editUser(UserDto user) {
        return restTemplate.postForObject("http://localhost:8080/editUser/" + user.getId(),
                new HttpEntity<UserDto>(user),
                UserDto.class) != null;
    }
}
