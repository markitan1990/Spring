package boot.controller;

import boot.dto.RoleDto;
import boot.dto.UserDto;
import boot.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class MyRestController {
    @Autowired
    private RestTemplateService restTemplateService;

    @PostMapping(value = "/addUser")
    public ResponseEntity<Void> addUser(@ModelAttribute UserDto user,
                                        @RequestParam(value = "n_roles", defaultValue = "user") List<String> roles) {
        Set<RoleDto> roleSet = roles.stream().map(a -> new RoleDto(a)).collect(Collectors.toSet());
        user.setRoles(roleSet);
        restTemplateService.addUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "/getUsers")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = restTemplateService.getUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<List<UserDto>>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/findUser")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        UserDto userDto = (UserDto) authentication.getPrincipal();
        return userDto != null
                ? new ResponseEntity<UserDto>(userDto, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/editUser/{id}")
    public ResponseEntity<Void> editUser(
            @PathVariable(name = "id") Long id,
            @ModelAttribute UserDto user,
            @RequestParam(value = "e_roles", defaultValue = "user") List<String> roles) {
        Set<RoleDto> roleSet = roles.stream().map(a -> new RoleDto(a)).collect(Collectors.toSet());
        user.setRoles(roleSet);
        restTemplateService.editUser(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        restTemplateService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
