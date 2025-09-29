package com.uphouse.monew.domain.comment.dto.request;

import com.uphouse.monew.domain.comment.domain.Comment;

import java.util.UUID;

public record CommentCreateRequest(
    UUID articleId,
    UUID userId,
    String content
) {
    public Comment toEntity() {
        return new Comment(articleId, userId, content, 0L, false);
    }
}
