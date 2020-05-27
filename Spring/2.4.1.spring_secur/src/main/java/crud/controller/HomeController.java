package crud.controller;

import crud.config.handler.LoginSuccessHandler;
import crud.model.Role;
import crud.model.User;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private User user;
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @GetMapping(value = "admin")
    public ModelAndView getListUsers(ModelMap model) {
        List<User> list = userService.listUsers();
        model.addAttribute("users", list);
        return new ModelAndView("admin", model);
    }

    @PostMapping(value = "admin/addUser")
    @ResponseBody
    public String addUsers(@RequestParam(value = "name") String name,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "combobox") String role, ModelMap model, HttpServletResponse response) throws IOException {
        user.setName(name);
        user.setPassword(password);
        user.setUserRoles(getListOfUserRoles(role));
        userService.add(user);
        response.sendRedirect("/admin");
        return "admin";
    }

    @GetMapping(value = "admin/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam(value = "Delete") Long id, HttpServletResponse response) throws IOException {
        System.out.println(id);
        userService.delete(id);
        response.sendRedirect("/admin");
        return "admin";
    }

    @GetMapping(value = "home")
    public ModelAndView home(ModelMap model) {
        return new ModelAndView("/home", model);
    }

    @PostMapping(value = "admin/editUser")
    @ResponseBody
    public String editUser(@RequestParam(value = "id") Long id,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "combobox") String role,
                           HttpServletResponse response) throws IOException {
        user.setId(id);
        user.setName(name);
        user.setPassword(password);
        user.setUserRoles(getListOfUserRoles(role));
        userService.edit(user);
        response.sendRedirect("/admin");
        return "/admin";
    }

    public Set<Role> getListOfUserRoles(String role) {
        Role roleAdmin = new Role();
        roleAdmin.setName("admin");
        Role roleUser = new Role();
        roleUser.setName("user");
        Set<Role> setGrantedAuthorities = new HashSet<>();
        if (role.equals("Admin")) {
            setGrantedAuthorities.add(roleAdmin);
            setGrantedAuthorities.add(roleUser);
        } else if (role.equals("User")) {
            setGrantedAuthorities.add(roleUser);
        }
        return setGrantedAuthorities;
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Authentication authentication) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello " + authentication.getName());
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage(Authentication authentication, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        if (authentication != null) {
            loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
        return "login";
    }
}
