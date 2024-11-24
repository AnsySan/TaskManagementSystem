package com.ansysan.task_management_system.exception;

public class AuthException extends RuntimeException{
    public AuthException(String message) {
        super(message);
    }
}