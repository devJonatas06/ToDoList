package com.project.todolist.AuthBackEnd.Controller;

import com.project.todolist.AuthBackEnd.Domain.User;
import com.project.todolist.AuthBackEnd.dto.LoginRequestDto;
import com.project.todolist.AuthBackEnd.dto.RegisterRequestDto;
import com.project.todolist.AuthBackEnd.dto.ResponseDto;
import com.project.todolist.AuthBackEnd.infra.security.TokenService;
import com.project.todolist.AuthBackEnd.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Profile("test")  // ← Adicione esta anotação
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthControllerTest {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto body) {
        Optional<User> userOpt = repository.findByEmail(body.email());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            return ResponseEntity.status(400).body("Invalid password");
        }

        String token = tokenService.genareteToken(user);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto body) {
        if (repository.findByEmail(body.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User newUser = new User();
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        repository.save(newUser);

        String token = tokenService.genareteToken(newUser);
        return ResponseEntity.ok(new ResponseDto(newUser.getId(), newUser.getName(), token));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.ok(false);
        }

        String token = authHeader.substring(7).trim(); // remove "Bearer "
        boolean isValid = tokenService.isValidToken(token);
        return ResponseEntity.ok(isValid);
    }
}
