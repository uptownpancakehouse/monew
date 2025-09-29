package com.uphouse.monew.domain.article.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String source;

    private String sourceUrl;

    private String title;

    private LocalDateTime publishDate;

    private String summary;

    private Integer commentCount;

    private Long viewCount;
}
