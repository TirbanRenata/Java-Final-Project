package com.example.JavaFinalProject.dtos;

import com.example.JavaFinalProject.model.Order;

import java.time.LocalDate;
import java.util.List;

public class CreateUserDto {
    private String name;
    private Integer age;
    private LocalDate registrationDate;
    private String email;
    private String username;
    private boolean active;
    private AddressDto address;
    private List<Order> orders;
}
