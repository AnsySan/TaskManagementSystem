package com.ansysan.task_management_system.repository;

import com.ansysan.task_management_system.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByAuthorId(Long authorId, Pageable pageable);

    Page<Task> findAllByPerformerId(Long performerId, Pageable pageable);
}