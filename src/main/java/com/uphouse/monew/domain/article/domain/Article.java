package com.uphouse.monew.domain.article.domain;

import com.uphouse.monew.domain.article.dto.SourceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity @Getter
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private SourceType source;

    private String sourceUrl;

    private String title;

    private LocalDateTime publishDate;

    private String summary;

    private Integer commentCount;

    private Long viewCount;
}
