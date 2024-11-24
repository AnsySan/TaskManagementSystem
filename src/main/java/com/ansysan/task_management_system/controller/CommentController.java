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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "Создание нового комментария",
            description = "Позволяет создать новый комментарий в системе"
    )
    @PostMapping("/comments")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentReadDto createComment(@Valid @RequestBody CommentCreateDto commentDto){
        return commentService.createComment(commentDto);
    }

    @Operation(
            summary = "Изменяет комментарий",
            description = "Позволяет изменять комментарий в системе"
    )
    @PostMapping("/comments/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.UPGRADE_REQUIRED)
    public CommentReadDto updateComment(@Valid @RequestBody CommentCreateDto commentCreateDto, Long userId, Long commentId){
        return commentService.updateComment(commentCreateDto,userId,commentId);
    }

    @Operation(
            summary = "Удаление комментария",
            description = "Позволяет удалять комментарий по его id"
    )
    @DeleteMapping("/comments/{id}")
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);
    }
}
