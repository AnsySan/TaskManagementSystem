package com.ansysan.task_management_system.service;

import com.ansysan.task_management_system.dto.TaskCreateDto;
import com.ansysan.task_management_system.dto.TaskReadDto;
import com.ansysan.task_management_system.entity.Task;
import com.ansysan.task_management_system.entity.User;
import com.ansysan.task_management_system.entity.enums.Priority;
import com.ansysan.task_management_system.entity.enums.Status;
import com.ansysan.task_management_system.exception.TaskException;
import com.ansysan.task_management_system.mapper.TaskMapper;
import com.ansysan.task_management_system.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

    public Task checkTask(Long id){
       return taskRepository.findById(id)
                .orElseThrow(() -> new TaskException(String.format("Task %s not found", id)));

    }

    @Transactional
    public TaskReadDto createTask(TaskCreateDto createDto, String email){
        User user = userService.findByEmail(email);
        userService.findById(createDto.getPerformerId());
        Task task = taskMapper.toEntity(createDto);
        taskRepository.save(task);
        log.debug("Task created with id " + createDto.getPerformerId());
        return taskMapper.toDto(task);
    }

    @Transactional
    public TaskReadDto updateTask(Long id, TaskCreateDto createDto){
        Task checkTask = checkTask(id);

        User user = userService.findById(createDto.getPerformerId());

        Task task = checkTask;
        task.setHeader(createDto.getHeader());
        task.setDescription(createDto.getDescription());
        task.setStatus(Status.valueOf(createDto.getStatus()));
        task.setPriority(Priority.valueOf(createDto.getPriority()));
        task.setPerformer(user);
        taskRepository.save(task);

        log.debug("Task updated with id " + createDto.getPerformerId());
        return taskMapper.toDto(task);
    }

    @Transactional
    public TaskReadDto deleteTask(Long id){
        Task task = checkTask(id);
        taskRepository.delete(task);
        log.debug("Task deleted with id " + id);
        return taskMapper.toDto(task);
    }

    public Page<TaskReadDto> getAllTasks(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskRepository.findAll(pageable);

        doesTaskExist(tasks);

        log.debug("Tasks found");

        return tasks.map(taskMapper::toDto);
    }

    public TaskReadDto getTask(Long id){
        Task task = checkTask(id);
        log.debug("Task found with id " + id);
        return taskMapper.toDto(task);
    }

    public Page<TaskReadDto> getTasksByAuthorId(int page, int size, Long authorId){
        log.debug("Tasks found with author id " + authorId);
        return getTasksByCondition(authorId, page, size, taskRepository::findAllByAuthorId);
    }

    public Page<TaskReadDto> getTasksByPerformedId(int page, int size, Long performedId){
        log.debug("Tasks found with performed id " + performedId);
        return getTasksByCondition(performedId, page, size, taskRepository::findAllByPerformerId);
    }

    private Page<TaskReadDto> getTasksByCondition(Long performedId, int page, int size, BiFunction<Long, Pageable, Page<Task>> function){
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> foundTasks = function.apply(performedId, pageable);
        doesTaskExist(foundTasks);

        return foundTasks.map(taskMapper::toDto);
    }

    private void doesTaskExist(Page<Task> tasks){
        if(tasks.isEmpty()){
            throw new TaskException(String.format("Task %s not found", tasks.getContent().get(0).getId()));
        }
    }
}
