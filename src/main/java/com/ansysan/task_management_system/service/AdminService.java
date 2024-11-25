package com.ansysan.task_management_system.service;

import com.ansysan.task_management_system.dto.UserReadDto;
import com.ansysan.task_management_system.entity.User;
import com.ansysan.task_management_system.entity.enums.Role;
import com.ansysan.task_management_system.mapper.UserMapper;
import com.ansysan.task_management_system.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserReadDto setRoleAdmin(Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

        public UserReadDto setRoleUser (Long id){
            User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            user.setRole(Role.USER);
            userRepository.save(user);
            return userMapper.toDto(user);
        }

        public List<UserReadDto> getAllUsers () {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(userMapper::toDto)
                    .toList();
        }
    }

