package crud.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class TargetUrlimpl implements TargetUrl {

    private static TargetUrlimpl instance;


    private TargetUrlimpl() {

    }

    public static TargetUrlimpl getInstance() {
        if (instance == null) {
            return new TargetUrlimpl();
        }
        return instance;
    }

    @Override
    public String getTargetUrl(Authentication auth) {
        String url = "login";
        for (GrantedAuthority s : auth.getAuthorities()) {
            if (s.getAuthority().equals("admin")) {
                url = "admin";
                break;
            }
            if (s.getAuthority().equals("user")) {
                url = "hello";
            }
        }
        return url;
    }
}
