package com.ansysan.task_management_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import com.ansysan.task_management_system.dto.CommentCreateDto;
import com.ansysan.task_management_system.dto.CommentReadDto;
import com.ansysan.task_management_system.dto.TaskReadDto;
import com.ansysan.task_management_system.entity.Comment;
import com.ansysan.task_management_system.entity.Task;
import com.ansysan.task_management_system.exception.CommentException;
import com.ansysan.task_management_system.mapper.CommentMapper;
import com.ansysan.task_management_system.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

public class CommentServiceTest {

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;
    private CommentCreateDto commentCreateDto;
    private CommentReadDto commentReadDto;
    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        task = new Task();
        task.setId(1L);

        comment = new Comment();
        comment.setId(1L);
        comment.setTask(task);
        comment.setCreatedDate(LocalDateTime.now());
        comment.setUpdatedDate(LocalDateTime.now());

        commentCreateDto = new CommentCreateDto();
        commentCreateDto.setTaskId(new TaskReadDto());
        commentCreateDto.setText("Test Comment");

        commentReadDto = new CommentReadDto();
        commentReadDto.setId(1L);
        commentReadDto.setText("Test Comment");
    }

    @Test
    public void testCreateComment_Success() {
        when(taskService.checkTask(anyLong())).thenReturn(task);
        when(commentMapper.toEntity(commentCreateDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentReadDto);

        CommentReadDto result = commentService.createComment(commentCreateDto);

        assertEquals(commentReadDto, result);
        verify(taskService).checkTask(1L);
        verify(commentMapper).toEntity(commentCreateDto);
        verify(commentRepository).save(comment);
    }

    @Test
    public void testUpdateComment_Success() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(commentReadDto);

        CommentReadDto result = commentService.updateComment(commentCreateDto, 1L, 1L);

        assertEquals(commentReadDto, result);
        assertNotNull(comment.getUpdatedDate());
        verify(commentRepository).findById(1L);
        verify(commentRepository).save(comment);
    }

    @Test
    public void testUpdateComment_CommentNotFound() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CommentException.class, () -> {
            commentService.updateComment(commentCreateDto, 1L, 1L);
        });

        verify(commentRepository).findById(1L);
    }

    @Test
    public void testDeleteComment_Success() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(commentReadDto);

        CommentReadDto result = commentService.deleteComment(1L);

        assertEquals(commentReadDto, result);
        verify(commentRepository).delete(comment);
    }

    @Test
    public void testDeleteComment_CommentNotFound() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CommentException.class, () -> {
            commentService.deleteComment(1L);
        });

        verify(commentRepository).findById(1L);
    }
}
