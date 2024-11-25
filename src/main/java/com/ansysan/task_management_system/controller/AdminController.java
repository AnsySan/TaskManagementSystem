package com.ansysan.task_management_system.controller;

import com.ansysan.task_management_system.dto.UserReadDto;
import com.ansysan.task_management_system.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * Назначает роль администратора пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль администратора.
     * @return Ответ с сообщением об успешном изменении роли пользователя.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set/admin/{id}")
    @Operation(
            summary = "Set the administrator role for the user",
            description = "Update the user role by specifying its id. The response is a message about the successful changed a role",
            tags = "post"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403"),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "500")})
    public UserReadDto setAdmin(@PathVariable("id") Long id) {
       return adminService.setRoleUser(id);
    }

    /**
     * Назначает роль журналиста пользователю с указанным идентификатором.
     *
     * @param id Идентификатор пользователя, которому нужно назначить роль журналиста.
     * @return Ответ с сообщением об успешном изменении роли пользователя.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set/user/{id}")
    public UserReadDto setUser(@PathVariable Long id) {
        return adminService.setRoleAdmin(id);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return Ответ со списком объектов {@link UserReadDto}, представляющих пользователей.
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Retrieve all users",
            description = "Collect all users. The answer is an array of users with identifier, username and role for each of the array element",
            tags = "get"
    )
    public List<UserReadDto> getAllUsers() {
        return adminService.getAllUsers();
    }
}