package chenjun.test.controller;

import chenjun.test.auth.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication API", description = "API for user authentication and authorization")
@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Get user details", security = @SecurityRequirement(name = "JWT"))
    @GetMapping("/user")
    public String getUser() {
        return "User details";
    }

    @Operation(summary = "User login")
    @GetMapping("/login")
    public String login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtUtil.generateToken(username);
    }
}
