package com.ansysan.task_management_system.controller;

import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.dto.UserReadDto;
import com.ansysan.task_management_system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Register a new user",
            description = "Allows you to register a new user in the system"
    )
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto registerNewUser(@Valid @RequestBody UserCreateDto userDto) {
        return userService.createUser(userDto);
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Error updating the user")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public UserReadDto update(@RequestBody @Valid UserCreateDto userDto, long userId) {
        log.info("Updating user: {}", userDto);
        return userService.updateUser(userDto, userId);
    }

    @Operation(summary = "Delete user by ID")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/user/delete")
    public UserReadDto delete(@RequestParam long userId) {
        log.info("Deleting user: {}", userId);
        return userService.deleteUser(userId);
    }
}
