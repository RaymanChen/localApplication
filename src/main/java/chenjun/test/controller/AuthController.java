package chenjun.test.controller;

import chenjun.test.auth.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Authentication API", description = "API for user authentication and authorization")
@RestController
public class AuthController {

    @Operation(summary = "User login")
    @PostMapping("/login")
    public ResponseEntity<?> login(String username, String password) {
        return ResponseEntity.ok(Map.of("message", "Login Successful"));
    }

    @Operation(summary = "User logout")
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Logout Successful"));
    }
}
