package crud.controller;

import crud.model.Role;
import crud.model.User;
import crud.security.handler.TargetUrlimpl;
import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@ControllerAdvice
@RequestMapping("/")
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "admin")
    public ModelAndView getListUsers(ModelMap model) {
        List<User> list = userService.listUsers();
        List<String> checkbox = new ArrayList<>();
        checkbox.add("admin");
        checkbox.add("user");
        checkbox.add("anonim");
        model.addAttribute("users", list);
        model.addAttribute("userRole", checkbox);
        model.addAttribute("newUser", new User());
        return new ModelAndView("admin", model);
    }

    @PostMapping(value = "/admin/addUser")
    public String saveUser(@ModelAttribute("newUser") User user) {
        if (user.getRoles().size() == 0) {
            Set<Role> set = new HashSet<>();
            set.add(new Role("anonim"));
            user.setRoles(set);
        }
        userService.add(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "admin/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestParam(value = "Delete") Long id, HttpServletResponse response) throws IOException {
        userService.delete(id);
        response.sendRedirect("/admin");
        return "admin";
    }


    @GetMapping(value = "home")
    public ModelAndView home(ModelMap model) {
        return new ModelAndView("/home", model);
    }

    @PostMapping(value = "/admin/editUser")
    public String editUser(@ModelAttribute User user) {
        if (user.getRoles().size() == 0) {
            Set<Role> set = new HashSet<>();
            set.add(new Role("anonim"));
            user.setRoles(set);
        }
        userService.edit(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "hello")
    public String printWelcome(ModelMap model, Authentication authentication) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello " + authentication.getName());
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping(value = "login")
    public String loginPage(Authentication authentication) {
//        if (authentication != null) {
//            return "redirect:/" + TargetUrlimpl.getInstance().getTargetUrl(authentication);
//        }
        return "login";
    }
}
