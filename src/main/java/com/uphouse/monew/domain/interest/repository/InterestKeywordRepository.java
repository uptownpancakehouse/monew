package com.uphouse.monew.domain.interest.repository;

import com.uphouse.monew.domain.interest.domain.InterestKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterestKeywordRepository extends JpaRepository<InterestKeyword, Long> {

    @Query("""
        SELECT COUNT(ik.interest.id)
        FROM InterestKeyword ik
        JOIN ik.interest i
        JOIN ik.keywords k
        WHERE k.keyword = :keyword
        """)
    long countInterestsByKeyword(@Param("keyword") String keyword);
}
