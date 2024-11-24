package com.ansysan.task_management_system.controller;

import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.dto.UserReadDto;
import com.ansysan.task_management_system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Позволяет зарегистрировать нового пользователя в системе"
    )
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto registerNewUser(@Valid @RequestBody UserCreateDto userDto) {
        return userService.createUser(userDto);
    }
}
