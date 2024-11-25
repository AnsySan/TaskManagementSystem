package com.ansysan.task_management_system.controller;

import com.ansysan.task_management_system.dto.CommentCreateDto;
import com.ansysan.task_management_system.dto.CommentReadDto;
import com.ansysan.task_management_system.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    /**
     * Создание нового комментария.
     *
     * Этот метод принимает данные для создания комментария,
     * валидирует их и вызывает сервис для создания комментария.
     *
     * @param commentDto данные для создания комментария
     * @return объект созданного комментария
     */
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Create a new comment",
            description = "Allows you to create a new comment in the system"
    )
    @PostMapping("/comments")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentReadDto createComment(@Valid @RequestBody CommentCreateDto commentDto){
        return commentService.createComment(commentDto);
    }

    /**
     * Изменение существующего комментария.
     *
     * Этот метод принимает данные для обновления комментария
     * и идентификатор комментария, который необходимо изменить.
     * Вызывает сервис для обновления комментария и возвращает
     * обновленный объект комментария.
     *
     * @param commentCreateDto данные для обновления комментария
     * @param commentId идентификатор комментария, который нужно изменить
     * @return объект обновленного комментария
     */
    @PreAuthorize("hasRole('USER')")
    @Operation(
            summary = "Changes the comment",
            description = "Allows you to change the comment in the system"
    )
    @PostMapping("/comments/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public CommentReadDto updateComment(@Valid @RequestBody CommentCreateDto commentCreateDto, Long userId, Long commentId){
        return commentService.updateComment(commentCreateDto,userId,commentId);
    }

    /**
     * Удаление комментария по его идентификатору.
     *
     * Этот метод принимает идентификатор комментария и вызывает
     * сервис для его удаления.
     *
     * @param id идентификатор комментария, который нужно удалить
     */
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete comment",
            description = "Allows you to delete a comment by its id"
    )
    @DeleteMapping("/comments/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
    }
}
