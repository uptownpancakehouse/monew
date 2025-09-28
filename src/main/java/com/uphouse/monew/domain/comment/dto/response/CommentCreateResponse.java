package com.uphouse.monew.domain.comment.dto.response;

import com.uphouse.monew.domain.comment.domain.Comment;
import java.time.LocalDateTime;
import java.util.UUID;

public record CommentCreateResponse(
    UUID id,
    UUID articleId,
    UUID userId,
    String userNickname,
    String content,
    Long likeCount,
    Boolean likedByMe,
    LocalDateTime createdAt
) {
    public static CommentCreateResponse from(Comment comment, String userNickname) {
        return new CommentCreateResponse(
            comment.getId(),
            comment.getArticleId(),
            comment.getUserId(),
            userNickname,
            comment.getContent(),
            comment.getLikeCount(),
            comment.getLikedByMe(),
            comment.getCreatedAt()
        );
    }
}
