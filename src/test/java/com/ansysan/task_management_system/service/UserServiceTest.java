package com.ansysan.task_management_system.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.dto.UserReadDto;
import com.ansysan.task_management_system.entity.User;
import com.ansysan.task_management_system.exception.UserException;
import com.ansysan.task_management_system.mapper.UserMapper;
import com.ansysan.task_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserCreateDto userCreateDto;
    private UserReadDto userReadDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        userCreateDto = new UserCreateDto();
        userCreateDto.setEmail("test@example.com");

        userReadDto = new UserReadDto();
        userReadDto.setId(1L);
        userReadDto.setEmail("test@example.com");
    }

    @Test
    public void testFindById_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    public void testFindById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserException.class, () -> {
            userService.findById(1L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(1L);
    }

    @Test
    public void testCreateUser_Success() {
        when(userMapper.toEntity(userCreateDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userReadDto);

        UserReadDto createdUser = userService.createUser(userCreateDto);

        assertNotNull(createdUser);
        assertEquals(userReadDto.getId(), createdUser.getId());
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toEntity(userCreateDto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userReadDto);

        UserReadDto updatedUser = userService.updateUser(userCreateDto, 1L);

        assertNotNull(updatedUser);
        assertEquals(userReadDto.getId(), updatedUser.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserException.class, () -> {
            userService.updateUser(userCreateDto, 1L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(1L);
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userReadDto);

        UserReadDto deletedUser = userService.deleteUser(1L);

        assertNotNull(deletedUser);
        assertEquals(userReadDto.getId(), deletedUser.getId());
        verify(userRepository).deleteUserById(1L);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserException.class, () -> {
            userService.deleteUser(1L);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userRepository).findById(1L);
    }
}
