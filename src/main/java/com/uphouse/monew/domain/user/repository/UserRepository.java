package com.uphouse.monew.domain.user.repository;

import com.uphouse.monew.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
