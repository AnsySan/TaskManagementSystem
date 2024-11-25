package com.ansysan.task_management_system.controller;

import com.ansysan.task_management_system.dto.TaskCreateDto;
import com.ansysan.task_management_system.dto.TaskReadDto;
import com.ansysan.task_management_system.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a task",
            description = "Allows you to create a new task in the system"
    )
    @PostMapping
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskReadDto createTask(@Valid @RequestBody TaskCreateDto taskDto, String email){
        return taskService.createTask(taskDto, email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Edit task",
            description = "Allows you to edit an existing task by its id"
    )
    @PatchMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public TaskReadDto changeTask(@PathVariable("id") Long taskId,
                           @Valid @RequestBody TaskCreateDto taskDto){

       return taskService.updateTask(taskId, taskDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete task",
            description = "Allows you to delete a task by its id"
    )
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TaskReadDto removeTask(@PathVariable("id") Long taskId){
        return taskService.deleteTask(taskId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Search all tasks",
            description = "Allows you to find all existing tasks in the system"
    )
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskReadDto> getAllTasks(@NotNull @RequestParam(value = "offset", defaultValue = "0") int page,
                                         @NotNull @RequestParam(value = "limit") int size){
        return taskService.getAllTasks(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Search task",
            description = "Allows you to find a task by its id"
    )
    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public TaskReadDto getTaskById(@PathVariable("id") Long taskId){
        return taskService.getTask(taskId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Search for author tasks",
            description = "Allows you to find all tasks created by the author by his id"
    )
    @GetMapping("/author/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskReadDto> getTasksByAuthorId(@PathVariable("id") Long authorId,
                                                 @NotNull @RequestParam(value = "offset", defaultValue = "0") int page,
                                                 @NotNull @RequestParam(value = "limit") int size){
        return taskService.getTasksByAuthorId(page, size, authorId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Search for tasks by performer",
            description = "Allows you to find all tasks assigned to a performer by their id"
    )
    @GetMapping("/performer/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskReadDto> getTasksByPerformerId(@PathVariable("id") Long performerId,
                                                    @NotNull @RequestParam(value = "offset", defaultValue = "0") int page,
                                                    @NotNull @RequestParam(value = "limit") int size){
        return taskService.getTasksByPerformedId(page, size, performerId);
    }
}
