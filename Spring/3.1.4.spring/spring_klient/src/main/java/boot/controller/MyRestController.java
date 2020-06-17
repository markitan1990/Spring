package boot.controller;

import boot.dto.RoleDto;
import boot.dto.UserDto;
import boot.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class MyRestController {
    @Autowired
    private RestTemplateService restTemplateService;

    @GetMapping(value = "/addUser")
    public void addUser(@ModelAttribute UserDto user,
                        @RequestParam(value = "n_roles", defaultValue = "user") List<String> roles) {
        Set<RoleDto> roleSet = roles.stream().map(a -> new RoleDto(a)).collect(Collectors.toSet());
        user.setRoles(roleSet);
        restTemplateService.addUser(user);
    }

    @GetMapping(value = "/getUsers")
    public List<UserDto> getUsers() {
        return restTemplateService.getUsers();
    }

    @GetMapping(value = "/findUser")
    public UserDto getUser(Authentication authentication) {
        return (UserDto) authentication.getPrincipal();
    }

    @GetMapping(value = "/editUser/{id}")
    public void editUser(
            @PathVariable(name = "id") Long id,
            @ModelAttribute UserDto user,
            @RequestParam(value = "e_roles", defaultValue = "user") List<String> roles) {
        Set<RoleDto> roleSet = roles.stream().map(a -> new RoleDto(a)).collect(Collectors.toSet());
        user.setRoles(roleSet);
        restTemplateService.editUser(user);
    }

    @GetMapping(value = "/deleteUser/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        restTemplateService.delete(id);
    }
}
