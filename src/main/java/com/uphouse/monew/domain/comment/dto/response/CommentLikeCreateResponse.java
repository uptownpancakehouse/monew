package com.uphouse.monew.domain.comment.dto.response;

import com.uphouse.monew.domain.comment.domain.Comment;
import com.uphouse.monew.domain.comment.domain.CommentLike;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentLikeCreateResponse(
    UUID id,
    UUID likedBy,
    LocalDateTime createdAt,
    UUID commentId,
    UUID articleId,
    UUID commentUserId,
    String commentUserNickname,
    String commentContent,
    Long commentLikeCount,
    LocalDateTime commentCreatedAt
) {
    public static CommentLikeCreateResponse from(CommentLike commentLike, Comment comment, String userNickname) {
        return new CommentLikeCreateResponse(
            commentLike.getId(),
            commentLike.getUserId(),
            commentLike.getCreatedAt(), 
            comment.getId(),
            comment.getArticleId(),
            comment.getUserId(),
            userNickname,
            comment.getContent(),
            comment.getLikeCount(),
            comment.getCreatedAt()
        );
    }
}
