package com.project.todolist.AuthBackEnd.Controller;

import com.project.todolist.AuthBackEnd.Domain.User;
import com.project.todolist.AuthBackEnd.dto.LoginRequestDto;
import com.project.todolist.AuthBackEnd.dto.RegisterRequestDto;
import com.project.todolist.AuthBackEnd.dto.ResponseDto;
import com.project.todolist.AuthBackEnd.infra.security.TokenService;
import com.project.todolist.AuthBackEnd.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    //teste
    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDto body) {
        User user = this.repository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.genareteToken(user);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequestDto body) {
        Optional<User> user = this.repository.findByEmail(body.email());
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            this.repository.save(newUser);
            String token = this.tokenService.genareteToken(newUser);
            return ResponseEntity.ok(new ResponseDto(newUser.getName(), token));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.ok(false);
            }

            String token = authHeader.replace("Bearer ", "").trim();
            boolean isValid = tokenService.isValidToken(token);
            return ResponseEntity.ok(isValid);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}


