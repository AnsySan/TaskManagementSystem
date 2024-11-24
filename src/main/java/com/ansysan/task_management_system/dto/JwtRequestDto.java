package com.ansysan.task_management_system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtRequestDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
