package com.ansysan.task_management_system.repository;

import com.ansysan.task_management_system.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}