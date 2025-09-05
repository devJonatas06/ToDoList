package com.project.todolist.AuthBackEnd.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

public record RegisterRequestDto(
        @NotBlank(message = "Nome é obrigatório") String name,
        @Email(message = "Email inválido") @NotBlank(message = "Email é obrigatório") String email,
        @NotBlank(message = "Senha é obrigatória") String password) {
}
