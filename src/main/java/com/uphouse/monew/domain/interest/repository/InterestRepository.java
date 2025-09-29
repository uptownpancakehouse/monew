package com.uphouse.monew.domain.interest.repository;

import com.uphouse.monew.domain.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InterestRepository extends JpaRepository<Interest, UUID> {
}
