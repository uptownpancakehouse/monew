package com.uphouse.monew.domain.comment.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentUser(
    UUID commentId,
    UUID articleId,
    String content,
    int likeCount,
    Boolean likedByMe,
    LocalDateTime createdAt,
    UUID userId,
    String nickname
) {
}