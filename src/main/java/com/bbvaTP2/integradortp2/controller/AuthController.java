package com.bbvaTP2.integradortp2.controller;

import com.bbvaTP2.integradortp2.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final String USERNAME = "miTPBBVA";
    //private final String PASSWORD = "$2a$10$wQwQwQwQwQwQwQwQwQwQwOQwQwQwQwQwQwQwQwQwQwQwQwQwQwQw"; // "1234" encriptado con BCrypt
    private final String PASSWORD = "$2a$10$Jds5u4OWQslcBXeB08LUuubDtqvzBwp8avKfEN6zUUZZA7eOQ2136"; // "teCompartomiclave" encriptado con BCrypt

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        if (USERNAME.equals(username) && passwordEncoder.matches(password, PASSWORD)) {
            String token = jwtUtil.generateToken(username);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
