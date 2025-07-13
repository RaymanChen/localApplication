package chenjun.test.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.util.Map;

@Component
@Log4j2
public class CustomLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter out = response.getWriter()) {
            Map<String, Object> map = Map.of(
                    "code", HttpServletResponse.SC_OK,
                    "message", "Logout Successful"
            );
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
