package com.example.JavaFinalProject.service;

import com.example.JavaFinalProject.dtos.CreateUserDto;
import com.example.JavaFinalProject.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    Iterable<User> findAllUsers();
    void deleteUser(Long id);
    Optional<User> findUserByUsername(String username);
    Iterable<User> saveAll(List<User> users);
    User addUser(User user);
    User createUserFromDto(CreateUserDto userDto);
}
