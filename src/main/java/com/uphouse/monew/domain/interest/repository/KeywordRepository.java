package com.uphouse.monew.domain.interest.repository;

import com.uphouse.monew.domain.interest.domain.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keywords, Long> {
    Optional<Keywords> findByKeyword(String word);
}
