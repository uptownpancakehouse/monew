package com.uphouse.monew.domain.user.repository;

import com.uphouse.monew.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
}
