package com.uphouse.monew.domain.article.dto;

import java.time.LocalDateTime;

public record RssArticle(
        String title,
        String link,
        String author,
        String description,
        LocalDateTime publishedDate
) {}
