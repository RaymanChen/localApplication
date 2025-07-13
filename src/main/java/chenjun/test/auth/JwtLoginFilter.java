package chenjun.test.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        try {
            String username = obtainUsername(req);
            String password = obtainPassword(req);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(token);
        } catch (Exception e) {
            log.error("Authentication failed", e);
            Map<String, Object> errorResponse = Map.of(
                    "code", HttpServletResponse.SC_UNAUTHORIZED,
                    "message", "Authentication failed: " + e.getMessage()
            );
            writeReturnMessage(errorResponse, res);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) {
        String username = obtainUsername(req);
        String token = JwtUtil.generateToken(username);
        Map<String, Object> map = Map.of(
                "code", HttpServletResponse.SC_OK,
                "message","Login Successful",
                "data", username,
                "auth",token
        );
        writeReturnMessage(map, res);
    }

    private void writeReturnMessage(Map<String, Object> map, HttpServletResponse res) {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter out = res.getWriter()) {
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
