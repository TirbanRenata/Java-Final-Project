package com.example.JavaFinalProject.repository;

import com.example.JavaFinalProject.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
