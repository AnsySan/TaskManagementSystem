package com.ansysan.task_management_system.mapper;

import com.ansysan.task_management_system.dto.TaskCreateDto;
import com.ansysan.task_management_system.dto.TaskReadDto;
import com.ansysan.task_management_system.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "createdDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "performer", source = "performedId")
    Task toEntity(TaskCreateDto taskCreateDto);
    TaskReadDto toDto(Task task);
}
