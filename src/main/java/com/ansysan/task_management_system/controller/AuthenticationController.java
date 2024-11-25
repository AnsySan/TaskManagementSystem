package com.ansysan.task_management_system.controller;

import com.ansysan.task_management_system.dto.JwtResponseDto;
import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    /**
     * Регистрация нового пользователя.
     *
     * Этот метод принимает данные пользователя, валидирует их
     * и вызывает сервис для регистрации. В случае успешной
     * регистрации возвращается ответ с кодом 201 (Created)
     * и URI созданного ресурса.
     *
     * @param request данные для создания пользователя
     * @return ответ с информацией о JWT токене и статусом регистрации
     */
    @Operation(
            summary = "Registration user",
            description = "Allows you to register a user"
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

    /**
     * Аутентификация пользователя.
     *
     * Этот метод принимает учетные данные пользователя,
     * валидирует их и вызывает сервис для аутентификации.
     * В случае успешной аутентификации возвращается ответ
     * с кодом 200 (OK) и информацией о JWT токене.
     *
     * @param request учетные данные пользователя для аутентификации
     * @return ответ с информацией о JWT токене и статусом аутентификации
     */
    @Operation(
            summary = "Authentication user",
            description = "Allows you to authentication a user"
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