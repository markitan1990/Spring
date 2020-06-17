package boot.service;

import boot.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestTemplateService {

    @Value(value = "${hostToServer}")
    private String serverAddress;


    private RestTemplate restTemplate;

    public RestTemplateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.basicAuthentication("admin", "admin").build();
    }

    public void addUser(UserDto userDto) {
        restTemplate.postForObject(serverAddress + "/addUser", new HttpEntity<UserDto>(userDto), UserDto.class);
    }

    public List<UserDto> getUsers() {
        return restTemplate.getForObject(serverAddress + "/getUsers", List.class);
    }

    public UserDto findUserByEmail(String email) {
        return restTemplate.getForObject(serverAddress + "/findUser?email=" + email, UserDto.class);
    }

    public void delete(Long id) {
        restTemplate.delete(serverAddress + "/deleteUser/" + id, UserDto.class);
    }

    public boolean editUser(UserDto user) {
        return restTemplate.exchange(serverAddress + "/editUser/" + user.getId(), HttpMethod.PUT,
                new HttpEntity<UserDto>(user),
                UserDto.class) != null;
    }
}
