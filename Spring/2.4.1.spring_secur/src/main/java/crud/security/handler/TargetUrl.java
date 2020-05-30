package crud.security.handler;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletResponse;

public interface TargetUrl {
    String getTargetUrl(Authentication auth);
}
