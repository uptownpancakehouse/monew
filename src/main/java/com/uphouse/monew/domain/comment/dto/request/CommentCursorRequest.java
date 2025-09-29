package com.uphouse.monew.domain.comment.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentCursorRequest(
    UUID articleId,
    CommentOrderBy orderBy,
    CommentSortDirection direction,
    LocalDateTime cursor,
    LocalDateTime after,
    Integer limit
) {}