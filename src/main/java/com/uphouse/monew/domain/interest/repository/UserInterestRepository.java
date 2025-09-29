package com.uphouse.monew.domain.interest.repository;

import com.uphouse.monew.domain.interest.domain.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
}
