package sep3.webshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.service.Impl.AdminService;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        String captchaInput = loginData.get("captchaInput");

        if (username == null || password == null || captchaInput == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username, password, and CAPTCHA are required.");
        }

        // Authenticate user
        if (adminService.authenticate(username, password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAdmin() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out");
    }
}
