package crud.controller;

import crud.model.User;
import crud.service.RoleService;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping
    public void getListUsers(ModelMap model) {
        List<User> list = userService.listUsers();
        List<String> checkbox = new ArrayList<>();
        checkbox.add("admin");
        checkbox.add("user");
        model.addAttribute("users", list);
        model.addAttribute("userRole", checkbox);
        model.addAttribute("newUser", new User());
    }

    @PostMapping(value = "/addUser")
    public String saveUser(@ModelAttribute("newUser") User user) {
        userService.add(roleService.updateUserRoles(user));
        return "redirect:/admin";
    }


    @GetMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam(value = "Delete") Long id){
        userService.delete(id);
        return "redirect:/admin";
    }
    @PostMapping(value = "/editUser")
    public String editUser(@ModelAttribute User user) {
        userService.edit(roleService.updateUserRoles(user));
        return "redirect:/admin";
    }
}
