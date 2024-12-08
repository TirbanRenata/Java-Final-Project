package com.example.JavaFinalProject.repository;

import com.example.JavaFinalProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<Object> findById(Long userId);
    boolean existsByUsername(String username);

}
