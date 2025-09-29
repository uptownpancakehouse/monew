package com.uphouse.monew.domain.comment.dto.response;

import com.uphouse.monew.domain.comment.domain.CommentUser;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CommentCursorResponse(
    List<Content> content,
    String nextCursor,
    String nextAfter,
    Integer size,
    Integer totalElements,
    Boolean hasNext
) {

    public record Content(
        UUID id,
        UUID articleId,
        UUID userId,
        String userNickname,
        String content,
        Integer likeCount,
        Boolean likedByMe,
        LocalDateTime createdAt
    ) {
        public static List<Content> toContentList(List<CommentUser> comments) {
            return comments.stream().map(it ->
                new Content(
                    it.commentId(),
                    it.articleId(),
                    it.userId(),
                    it.nickname(),
                    it.content(),
                    it.likeCount(),
                    it.likedByMe(),
                    it.createdAt()
                )
            ).toList();
        }
    }
}
