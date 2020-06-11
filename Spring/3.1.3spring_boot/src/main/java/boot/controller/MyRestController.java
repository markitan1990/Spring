package boot.controller;

import boot.model.Role;
import boot.model.User;
import boot.servise.UserService;
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
    private UserService userService;

    @PostMapping(value = "/addUser")
    public ResponseEntity<?> addUser(@ModelAttribute User user, @RequestParam(value = "n_roles", defaultValue = "user") List<String> roles) {
        Set<Role> roleSet = roles.stream().map(a -> new Role(a)).collect(Collectors.toSet());
        user.setRoles(roleSet);
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/admin")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<List<User>>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/header")
    public ResponseEntity<User> getUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") Long id) {
        User client = userService.findUserById(id);
        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/editUser/{id}")
    public ResponseEntity<?> editUser(
            @PathVariable(name = "id") Long id,
            @ModelAttribute User user,
            @RequestParam(value = "e_roles", defaultValue = "user") List<String> roles) {
        Set<Role> roleSet = roles.stream().map(a -> new Role(a)).collect(Collectors.toSet());
        user.setRoles(roleSet);
        boolean updated = userService.editUser(user);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        boolean deleted = userService.delete(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
