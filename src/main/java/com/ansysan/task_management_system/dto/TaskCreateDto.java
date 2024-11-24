package com.ansysan.task_management_system.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateDto {
    @NotBlank
    private String header;

    @NotBlank
    private String description;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "(PENDING|PROGRESS|COMPLETED)")
    private String status;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(LOW|MIDDLE|HIGH)")
    private String priority;

    @NotNull
    @Positive
    private Long performerId;
}
