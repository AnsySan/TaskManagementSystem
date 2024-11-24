package com.ansysan.task_management_system.service;

import com.ansysan.task_management_system.dto.CommentCreateDto;
import com.ansysan.task_management_system.dto.CommentReadDto;
import com.ansysan.task_management_system.entity.Comment;
import com.ansysan.task_management_system.entity.Task;
import com.ansysan.task_management_system.exception.CommentException;
import com.ansysan.task_management_system.mapper.CommentMapper;
import com.ansysan.task_management_system.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final TaskService taskService;

    @Transactional
    public CommentReadDto createComment(CommentCreateDto commentCreateDto){
        Task task = taskService.checkTask(commentCreateDto.getTaskId());

        Comment comment = commentMapper.toEntity(commentCreateDto);

        comment = commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    public CommentReadDto updateComment(CommentCreateDto commentCreateDto, Long userId, Long commentId){
        Comment comment = existsComment(commentId);
        comment.setUpdatedDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    public CommentReadDto deleteComment(Long id){
        Comment comment = existsComment(id);
        commentRepository.delete(comment);
        return commentMapper.toDto(comment);
    }

    private Comment existsComment(Long commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException("Comment with ID " + commentId + " not found"));
    }
}
