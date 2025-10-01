package com.uphouse.monew.domain.interest.repository;

import com.uphouse.monew.domain.interest.domain.Keywords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface KeywordRepository extends JpaRepository<Keywords, Long> {
    Optional<Keywords> findByKeyword(String word);

    @Query("SELECT k FROM Keywords k WHERE k.keyword IN :keywords")
    List<Keywords> findByKeywords(@Param("keywords") Set<String> keywords);

    boolean existsByKeyword(String keyword);
}
