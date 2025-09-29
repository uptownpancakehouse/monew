package com.uphouse.monew.domain.interest.repository;

import com.uphouse.monew.domain.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    Optional<Interest> findByName(String name);

    @Query(
            value = "SELECT EXISTS (SELECT 1 FROM interests WHERE similarity(name, :name) >= 0.8)",
            nativeQuery = true
    )
    boolean existsBySimilarName(@Param("name") String name);
}
