package com.uphouse.monew.domain.article.repository;

import com.uphouse.monew.domain.article.domain.Article;
import com.uphouse.monew.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
