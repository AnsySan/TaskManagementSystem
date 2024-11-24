package com.ansysan.task_management_system.controller;

import com.ansysan.task_management_system.dto.JwtResponseDto;
import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Tag(name = "AuthenticationController", description = "Контроллер регистрации/авторизации")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    @PostMapping(value = "/registration")
    public ResponseEntity<JwtResponseDto> register(
            @Valid
            @RequestBody UserCreateDto request) {
        JwtResponseDto response = service.register(request);
        UriComponentsBuilder componentsBuilder = UriComponentsBuilder.newInstance();
        return ResponseEntity.created(componentsBuilder
                        .path("/{username}")
                        .build(Map.of("username", request.getUsername())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

    @Operation(
            summary = "Авторизация пользователя",
            description = "Позволяет авторизовать пользователя"
    )
    @PostMapping(value = "/authentication")
    public ResponseEntity<JwtResponseDto> authenticate(
            @Valid
            @RequestBody UserCreateDto request) {

        JwtResponseDto response = service.authenticate(request);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}