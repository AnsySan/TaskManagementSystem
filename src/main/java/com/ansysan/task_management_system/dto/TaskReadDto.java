package com.ansysan.task_management_system.dto;

import com.ansysan.task_management_system.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskReadDto {
    private Long id;
    private String header;
    private String description;
    private String status;
    private String priority;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private User author;
    private User performer;
    private List<CommentReadDto> comments;
}
