package com.uphouse.monew.domain.article.domain;

import com.uphouse.monew.domain.article.dto.SourceType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Getter
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SourceType source;

    private String sourceUrl;

    private String title;

    private LocalDateTime publishDate;

    private String summary;

    private Integer commentCount;

    private Long viewCount;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
