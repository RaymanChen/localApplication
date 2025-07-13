package chenjun.test.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

@Log4j2
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        try {
            String header = req.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                chain.doFilter(req, res);
                return;
            }
            UsernamePasswordAuthenticationToken token = getAuthentication(req);
            if (token == null) {
                throw new Exception("Invalid token");
            }
            SecurityContextHolder.getContext().setAuthentication(token);
            chain.doFilter(req, res);
        } catch (Exception e) {
            respondMessage(res, "Please Login First");
            log.warn("JWT Authentication failed", e);
        }
    }

    private void respondMessage(HttpServletResponse res, String msg) {
        try (PrintWriter out = res.getWriter()) {
            res.setContentType("application/json;charset=UTF-8");
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            Map<String, Object> map = Map.of(
                    "code", HttpServletResponse.SC_FORBIDDEN,
                    "message", msg
            );
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
        } catch (Exception e) {
            log.error("Error writing response message", e);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        if (StringUtils.isNotEmpty(header)) {
            String token = header.replace("Bearer ", "");
            String username = JwtUtil.extractUsername(token);
            if (StringUtils.isNotEmpty(username)) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
        }
        return null;
    }
}
