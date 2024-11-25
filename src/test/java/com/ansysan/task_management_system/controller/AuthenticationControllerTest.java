package com.ansysan.task_management_system.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ansysan.task_management_system.dto.JwtResponseDto;
import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService service;

    @InjectMocks
    private AuthenticationController authenticationController;

    private UserCreateDto userCreateDto;
    private JwtResponseDto jwtResponseDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание тестовых данных
        userCreateDto = new UserCreateDto();
        userCreateDto.setUsername("testuser");
        userCreateDto.setPassword("password123");

        jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setTokenType("sample.jwt.token");
    }

    @Test
    public void testRegister_Success() {
        when(service.register(any(UserCreateDto.class))).thenReturn(jwtResponseDto);

        ResponseEntity<JwtResponseDto> response = authenticationController.register(userCreateDto);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(jwtResponseDto, response.getBody());
        assertTrue(response.getHeaders().getLocation().toString().contains("testuser"));
    }

    @Test
    public void testRegister_Failure() {
        when(service.register(any(UserCreateDto.class))).thenThrow(new RuntimeException("Registration failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationController.register(userCreateDto);
        });

        assertEquals("Registration failed", exception.getMessage());
    }

    @Test
    public void testAuthenticate_Success() {
        when(service.authenticate(any(UserCreateDto.class))).thenReturn(jwtResponseDto);

        ResponseEntity<JwtResponseDto> response = authenticationController.authenticate(userCreateDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(jwtResponseDto, response.getBody());
    }

    @Test
    public void testAuthenticate_Failure() {
        when(service.authenticate(any(UserCreateDto.class))).thenThrow(new RuntimeException("Authentication failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationController.authenticate(userCreateDto);
        });

        assertEquals("Authentication failed", exception.getMessage());
    }
}
