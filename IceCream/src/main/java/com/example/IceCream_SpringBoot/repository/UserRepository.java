package com.example.IceCream_SpringBoot.repository;

import com.example.IceCream_SpringBoot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}


