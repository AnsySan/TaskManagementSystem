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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @Operation(
            summary = "Создание задачи",
            description = "Позволяет создать новую задачу в системе"
    )
    @PostMapping
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskReadDto createTask(@Valid @RequestBody TaskCreateDto taskDto, String email){
        return taskService.createTask(taskDto, email);
    }

    @Operation(
            summary = "Редактирование задачи",
            description = "Позволяет изменять существующую задачу по ее id"
    )
    @PatchMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public TaskReadDto changeTask(@PathVariable("id") Long taskId,
                           @Valid @RequestBody TaskCreateDto taskDto){

       return taskService.updateTask(taskId, taskDto);
    }

    @Operation(
            summary = "Удаление задачи",
            description = "Позволяет удалять задачу по ее id"
    )
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TaskReadDto removeTask(@PathVariable("id") Long taskId){
        return taskService.deleteTask(taskId);
    }

    @Operation(
            summary = "Поиск всех задач",
            description = "Позволяет найти все существующие задачи в системе"
    )
    @GetMapping
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskReadDto> getAllTasks(@NotNull @RequestParam(value = "offset", defaultValue = "0") int page,
                                         @NotNull @RequestParam(value = "limit") int size){
        return taskService.getAllTasks(page, size);
    }

    @Operation(
            summary = "Поиск задачи",
            description = "Позволяет найти задачу по ее id"
    )
    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public TaskReadDto getTaskById(@PathVariable("id") Long taskId){
        return taskService.getTask(taskId);
    }

    @Operation(
            summary = "Поиск задач автора",
            description = "Позволяет найти все задачи, созданные автором по его id"
    )
    @GetMapping("/author/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskReadDto> getTasksByAuthorId(@PathVariable("id") Long authorId,
                                                 @NotNull @RequestParam(value = "offset", defaultValue = "0") int page,
                                                 @NotNull @RequestParam(value = "limit") int size){
        return taskService.getTasksByAuthorId(page, size, authorId);
    }

    @Operation(
            summary = "Поиск задач исполнителя",
            description = "Позволяет найти все задачи, назначенные на исполнителя по его id"
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
