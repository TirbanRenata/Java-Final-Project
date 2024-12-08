package com.example.JavaFinalProject.repository;

import com.example.JavaFinalProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

//    List<User> findAllByOrderByUsernameAsc();
////    List<User> findByActiveOrderByUsernameDesc(boolean active);
//    List<User> findByRegistrationDateBetween(LocalDate start, LocalDate end);
//    List<User> findByAgeGreaterThanEqual(int age);
//    List<User> findByUsernameContaining(String text);
//    List<User> findByUsernameStartingWith(String text);
//
//    @Query("SELECT u FROM User u WHERE u.age > 18")
//    List<User> findUsersOver18();
//    @Query(value = "SELECT u FROM users u WHERE u.age > 18", nativeQuery = true)
//    List<User> findUsersOver18NativeQuery();

}