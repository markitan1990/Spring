package boot.controller;

import boot.model.User;
import boot.servise.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyRestController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/addUser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/getUsers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return users != null && !users.isEmpty()
                ? new ResponseEntity<List<User>>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/findUser")
    public ResponseEntity<User> getUser(@ModelAttribute User user) {
        User principal = userService.findUserByEmail(user.getEmail());
        return principal != null
                ? new ResponseEntity<User>(principal, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/editUser/{id}")
    public ResponseEntity<?> editUser(
            @PathVariable(name = "id") Long id,
            @RequestBody User user) {
        return userService.editUser(user)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/deleteUser/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        return userService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping(value = "/login")
    public void login(@RequestBody User user){
        System.out.println(user.getFirstName());
    }
}
