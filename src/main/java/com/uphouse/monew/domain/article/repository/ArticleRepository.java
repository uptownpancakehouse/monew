package com.uphouse.monew.domain.article.repository;

import com.uphouse.monew.domain.article.domain.Article;
import com.uphouse.monew.domain.article.dto.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
    List<Article> findBySource(SourceType source);

    @Query("SELECT DISTINCT a.source FROM Article a")
    List<String> findSourcesList();
}
