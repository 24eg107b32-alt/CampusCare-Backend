
package com.example.start;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:5174",
    "http://localhost:5175"
})
public class AdminAuthController {

    @PostMapping("/admin-login")
    public Map<String, String> adminLogin(
            @RequestBody Map<String, String> loginDetails) {

        String username = loginDetails.get("username");
        String password = loginDetails.get("password");

        if ("admin".equals(username) &&
            "admin123".equals(password)) {

            return Map.of(
                "message", "Login successful",
                "token", "campuscare-admin-session"
            );
        }

        throw new ResponseStatusException(
            HttpStatus.UNAUTHORIZED,
            "Invalid username or password"
        );
    }
}
