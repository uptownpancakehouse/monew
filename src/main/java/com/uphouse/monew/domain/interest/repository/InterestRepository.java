package com.uphouse.monew.domain.interest.repository;

import com.uphouse.monew.domain.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}
