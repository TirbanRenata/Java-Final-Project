package com.example.JavaFinalProject.service;

import com.example.JavaFinalProject.dtos.CreateUserDto;
import com.example.JavaFinalProject.model.User;
import com.example.JavaFinalProject.repository.UserJpaRepository;
import com.example.JavaFinalProject.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserJpaRepository jpaRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    public Optional<User> findUserByUsername(String username) {

        return jpaRepository.findByUsername(username);
    }

    public User addUser(User user) {
        userRepository.save(user);
        return user;
    }


    public boolean deleteUser(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteUser(Long id) {

        jpaRepository.deleteById(id);
    }

    public Iterable<User> findAllUsers() {

        return jpaRepository.findAll();
    }

    public Iterable<User> saveAll(List<User> users) {

        return jpaRepository.saveAll(users);
    }

    public User createUserFromDto(CreateUserDto userDto) {

        User user = mapper.convertValue(userDto, User.class);

        User savedUser = jpaRepository.save(user);

        String subject = "Welcome to our service!";
        String body = "Dear " + savedUser.getName() + " Thank you for registration! \n your username is " + savedUser.getUsername();

        return savedUser;
    }

}
