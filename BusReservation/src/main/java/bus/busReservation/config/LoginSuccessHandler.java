package bus.busReservation.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();

        List<String> roleNames = new ArrayList<>();
        authentication.getAuthorities().forEach(authority->{
            roleNames.add(authority.getAuthority());
        });

        if(roleNames.contains("USER")) {
            response.sendRedirect("/");
            return;
        } else if (roleNames.contains("BUS")) {
            //session.setAttribute("user_id",authentication.getName() + "님 반갑습니다.");
            response.sendRedirect("/bus/selection");
            return;
        }

        response.sendRedirect("/");
    }
}
