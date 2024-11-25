package com.ansysan.task_management_system.repository;

import com.ansysan.task_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findById(Long id);
    Optional<User> deleteUserById(Long id);
}
