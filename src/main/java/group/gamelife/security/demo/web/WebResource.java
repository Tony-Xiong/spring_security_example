package group.gamelife.security.demo.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by xiongyizhou on 2019/8/13 13:22 E-mail: xiongyizhou@powerpms.com
 * login page and index page / 主页和登录页
 * @author xiongyizhou
 */
@RestController
public class WebResource {

  @GetMapping("")
  public void defaultPage(HttpServletResponse response) throws IOException {
    response.sendRedirect("/index");
  }

  @PreAuthorize("")
  @GetMapping("/index")
  public String index(HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {

    Authentication auths = SecurityContextHolder.getContext().getAuthentication();
    Collection<? extends GrantedAuthority> authorities = auths.getAuthorities();
      return "<!DOCTYPE html>\n"
          + "<html lang=\"en\">\n"
          + "<head>\n"
          + "    <meta charset=\"UTF-8\">\n"
          + "    <title>Index</title>\n"
          + "</head>\n"
          + "<body>\n"
          + "<h1>welcome</h1>\n"
          + "<h2>you are already login the system!</h2>\n"
          + "<h3>Good!</h3>\n"
          + "<h4>"
          + "username:"
          + request.getUserPrincipal().getName()
          + "</h4>\n"
          + "<p>"
          + authorities
          + "</p>"
          + "<form action=\"/auth/logout\" method=\"POST\">\n"
          + "    <button type=\"submit\" >logout</button>\n"
          + "</form>"
          + "</body>\n"
          + "</html>";
  }

  @GetMapping("/login")
  public String loginPage() {
    return "<!DOCTYPE html>\n"
        + "<html lang=\"en\">\n"
        + "<head>\n"
        + "    <meta charset=\"UTF-8\">\n"
        + "    <title>Login</title>\n"
        + "</head>\n"
        + "<body>\n"
        + "\n"
        + "<h1>Login Page</h1>\n"
        + "<h3> default admin account: admin/admin </h3>"
        + "<h3> default user account: user1/123456 </h3>"
        + "<form action=\"/login\" method=\"post\">\n"
        + "    <div><p>username</p><input name=\"username\" type=\"text\"></div>\n"
        + "    <div><p>password</p><input name=\"password\" type=\"text\"></div>\n"
        + "    <button type=\"submit\">login</button>\n"
        + "</form>\n"
        + "\n"
        + "</body>\n"
        + "</html>";
  }
}
