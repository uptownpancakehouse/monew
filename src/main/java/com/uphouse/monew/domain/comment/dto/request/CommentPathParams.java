package com.uphouse.monew.domain.comment.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentPathParams (
    UUID articleId,
    CommentOrderBy orderBy,
    CommentSortDirection direction,
    String cursor,
    LocalDateTime after,
    int limit
) {}
