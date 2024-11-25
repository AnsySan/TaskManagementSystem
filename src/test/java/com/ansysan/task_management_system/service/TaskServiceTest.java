package com.ansysan.task_management_system.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ansysan.task_management_system.dto.TaskCreateDto;
import com.ansysan.task_management_system.dto.TaskReadDto;
import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.dto.UserReadDto;
import com.ansysan.task_management_system.entity.Task;
import com.ansysan.task_management_system.entity.User;
import com.ansysan.task_management_system.entity.enums.Priority;
import com.ansysan.task_management_system.entity.enums.Status;
import com.ansysan.task_management_system.exception.TaskException;
import com.ansysan.task_management_system.exception.UsernameNotFoundException;
import com.ansysan.task_management_system.mapper.TaskMapper;
import com.ansysan.task_management_system.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserService userService;

    private Task task;
    private TaskCreateDto createDto;
    private TaskReadDto readDto;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание тестовых данных
        user = new User();
        user.setId(1L);
        user.setUsername("Test User");

        task = new Task();
        task.setId(1L);
        task.setHeader("Test Task");
        task.setDescription("Test Description");
        task.setStatus(Status.COMPLETED);
        task.setPriority(Priority.LOW);
        task.setPerformer(user);

        createDto = new TaskCreateDto();
        createDto.setHeader("Updated Test Task");
        createDto.setDescription("Updated Description");
        createDto.setStatus("PROGRESS");
        createDto.setPriority("HIGH");

        readDto = new TaskReadDto();
        readDto.setId(1L);
        readDto.setHeader(createDto.getHeader());
        readDto.setDescription(createDto.getDescription());
        readDto.setStatus(String.valueOf(Status.PROGRESS));
        readDto.setPriority(String.valueOf(Priority.HIGH));
        readDto.setPerformer(user);
    }


    @Test
    public void testCreateTask_Success() {
        when(userService.findByEmail(anyString())).thenReturn(user);
        when(userService.findById(anyLong())).thenReturn(user);
        when(taskMapper.toEntity(createDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(readDto);

        TaskReadDto result = taskService.createTask(createDto, "test@example.com");

        assertEquals(readDto, result);
        verify(taskRepository).save(task);
    }

    @Test
    public void testUpdateTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userService.findById(anyLong())).thenReturn(user);
        when(taskMapper.toDto(task)).thenReturn(readDto);

        TaskReadDto result = taskService.updateTask(1L, createDto);

        assertEquals(readDto, result);
        assertEquals("Updated Test Task", task.getHeader());
        assertEquals("Updated Description", task.getDescription());
        assertEquals(Status.PROGRESS, task.getStatus());
        assertEquals(Priority.HIGH, task.getPriority());
        assertEquals(user, task.getPerformer());

        verify(taskRepository).findById(1L);
        verify(userService).findById(1L);
        verify(taskRepository).save(task);
    }

    @Test
    public void testDeleteTask_Success() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(readDto);

        TaskReadDto result = taskService.deleteTask(1L);

        assertEquals(readDto, result);
        verify(taskRepository).delete(task);
    }

    @Test
    public void testGetAllTasks_Success() {
        Page<Task> taskPage = new PageImpl<>(List.of(task));
        when(taskRepository.findAll(any(Pageable.class))).thenReturn(taskPage);
        when(taskMapper.toDto(task)).thenReturn(readDto);

        Page<TaskReadDto> result = taskService.getAllTasks(0, 10);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(readDto, result.getContent().get(0));
    }

    @Test
    public void testCreateTask_UserNotFound() {
        when(userService.findByEmail(anyString())).thenThrow(new UsernameNotFoundException("User not found"));
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            taskService.createTask(createDto, "test@example.com");
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testUpdateTask_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TaskException.class, () -> {
            taskService.updateTask(1L, createDto);
        });

        assertEquals("Task 1 not found", exception.getMessage());
    }

    @Test
    public void testDeleteTask_TaskNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(TaskException.class, () -> {
            taskService.deleteTask(1L);
        });

        assertEquals("Task 1 not found", exception.getMessage());
    }
}
