package com.project.todolist.AuthBackEnd.Controller;

import com.project.todolist.AuthBackEnd.Domain.User;
import com.project.todolist.AuthBackEnd.dto.LoginRequestDto;
import com.project.todolist.AuthBackEnd.dto.RegisterRequestDto;
import com.project.todolist.AuthBackEnd.dto.ResponseDto;
import com.project.todolist.AuthBackEnd.infra.security.TokenService;
import com.project.todolist.AuthBackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity login(@RequestBody LoginRequestDto body) {
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.genareteToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getName(), token));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDto body) {
        Optional<User> user = this.repository.findByEmail(body.email());
        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);
            String token = this.tokenService.genareteToken(newUser);
            return ResponseEntity.ok(new ResponseDto(newUser.getName(), token));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
