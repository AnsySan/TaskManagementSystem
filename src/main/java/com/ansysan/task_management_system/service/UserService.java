package com.ansysan.task_management_system.service;

import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.dto.UserReadDto;
import com.ansysan.task_management_system.entity.User;
import com.ansysan.task_management_system.exception.UserException;
import com.ansysan.task_management_system.mapper.UserMapper;
import com.ansysan.task_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserException("User not found"));
    }

    @Transactional
    public UserReadDto createUser(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }
}