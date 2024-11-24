package com.ansysan.task_management_system.handler;

import com.ansysan.task_management_system.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAuthException(AuthException e) {
        log.error("Auth error: {}",e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(CommentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerCommentException(CommentException e) {
        log.error("Comment error: {}",e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(TaskException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerTaskException(TaskException e) {
        log.error("Task error: {}",e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerUserException(UserException e) {
        log.error("User error: {}",e.getMessage());
        return e.getMessage();
    }

    private ErrorResponse buildErrorResponse(Exception e, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .url(request.getRequestURI())
                .message(e.getMessage())
                .build();
    }
}
