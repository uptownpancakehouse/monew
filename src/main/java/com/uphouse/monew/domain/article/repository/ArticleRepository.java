package com.uphouse.monew.domain.article.repository;

import com.uphouse.monew.domain.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
