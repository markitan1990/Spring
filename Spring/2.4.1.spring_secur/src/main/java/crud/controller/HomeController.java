package crud.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/")
public class HomeController {

    @GetMapping(value = "home")
    public String home() {
        return "home";
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
        if (authentication != null) {
            for (GrantedAuthority role : authentication.getAuthorities()) {
                if (role.getAuthority().equals("admin")) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/hello";
                }
            }
        }
        return "login";
    }
}
