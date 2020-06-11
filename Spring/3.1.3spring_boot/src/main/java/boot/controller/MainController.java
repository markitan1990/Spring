package boot.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping(value = "/")
    public String home() {
        return "/home";
    }

    @GetMapping(value = {"/login"})
    public String loginPage(Authentication authentication) {
        if (authentication != null) {
            return "redirect:/";
        }
        return "login";
    }
}
