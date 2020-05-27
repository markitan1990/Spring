package crud.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        Authentication authentication) throws IOException, ServletException {
        handle(req, resp, authentication);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException {
        String targetUrl = determinateTargetUrl(authentication);
        resp.sendRedirect(targetUrl);
    }

    private String determinateTargetUrl(Authentication authentication) {
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("user", "/hello");
        roleTargetUrlMap.put("admin", "/admin");
        String userRole = "";
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                if (authorityName.equals("admin")) {
                    return roleTargetUrlMap.get(authorityName);
                }
                if (authorityName.equals("user")) {
                    userRole = authorityName;
                }
            }
        }
        return roleTargetUrlMap.get(userRole);
    }
}