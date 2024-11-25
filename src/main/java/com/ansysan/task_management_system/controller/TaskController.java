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

    /**
    * Создание новой задачи.
     *
     * Этот метод принимает данные для создания задачи и email,
     * валидирует их и вызывает сервис для создания задачи.
     *
    * @param taskDto данные для создания задачи
    * @param email email автора задачи
    * @return объект созданной задачи
    */


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

    /**
     * Изменение существующей задачи.
     *
     * Этот метод принимает идентификатор задачи и данные для обновления,
     * вызывает сервис для обновления задачи и возвращает
     * обновленный объект задачи.
     *
     * @param taskId идентификатор задачи, которую нужно изменить
     * @param taskDto данные для обновления задачи
     * @return объект обновленной задачи
     */
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

    /**
     * Удаление задачи по ее идентификатору.
     *
     * Этот метод принимает идентификатор задачи и вызывает
     * сервис для ее удаления. В случае успешного удаления
     * возвращается код 204 (No Content).
     *
     * @param taskId идентификатор задачи, которую нужно удалить
     */
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

    /**
     * Получение всех существующих задач.
     *
     * Этот метод возвращает все задачи с учетом пагинации.
     *
     * @param page номер страницы (смещение)
     * @param size размер страницы (количество задач на странице)
     * @return страница объектов задач
     */
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

    /**
     * Получение задачи по ее идентификатору.
     *
     * Этот метод возвращает задачу с указанным идентификатором.
     *
     * @param taskId идентификатор задачи, которую нужно получить
     * @return объект найденной задачи
     */
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

    /**
     * Получение всех задач, созданных автором по его идентификатору.
     *
     * Этот метод возвращает все задачи, созданные указанным автором,
     * с учетом пагинации.
     *
     * @param authorId идентификатор автора задач
     * @param page номер страницы (смещение)
     * @param size размер страницы (количество задач на странице)
     * @return страница объектов задач, созданных автором
     */
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

    /**
     * Получение всех задач, назначенных исполнителю по его идентификатору.
     *
     * Этот метод возвращает все задачи, назначенные указанному исполнителю,
     * с учетом пагинации.
     *
     * @param performerId идентификатор исполнителя задач
     * @param page номер страницы (смещение)
     * @param size размер страницы (количество задач на странице)
     * @return страница объектов задач, назначенных исполнителю
     */
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
