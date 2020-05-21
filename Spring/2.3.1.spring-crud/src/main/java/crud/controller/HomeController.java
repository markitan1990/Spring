package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private User user;

    @GetMapping(value = "/users")
    public String getListUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @PostMapping(value = "/addUser")
    @ResponseBody
    public ModelAndView getUsers(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "lastName") String lastName,
                                 @RequestParam(value = "password") String password,
                                 @RequestParam(value = "combobox") String role, ModelMap model) {
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRole(role);
        userService.add(user);

        return new ModelAndView("redirect:/users", model);
    }

    @GetMapping(value = "/deleteUser")
    @ResponseBody
    public ModelAndView deleteUser(@RequestParam(value = "Delete") Long id,
                                   ModelMap model) {
        System.out.println(id);
        userService.delete(id);

        return new ModelAndView("redirect:/users", model);
    }

    @PostMapping(value = "editUser")
    @ResponseBody
    public ModelAndView editUser(@RequestParam(value = "id") Long id,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "lastName") String lastName,
                                 @RequestParam(value = "password") String password,
                                 @RequestParam(value = "combobox") String role,
                                 ModelMap model) {
        user.setId(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setRole(role);
        userService.edit(user);

        return new ModelAndView("redirect:/users", model);
    }
}
