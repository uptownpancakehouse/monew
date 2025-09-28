package com.uphouse.monew.domain.comment.service;

import com.uphouse.monew.domain.comment.domain.Comment;
import com.uphouse.monew.domain.comment.domain.CommentLike;
import com.uphouse.monew.domain.comment.dto.response.CommentCreateResponse;
import com.uphouse.monew.domain.comment.dto.response.CommentLikeCreateResponse;
import com.uphouse.monew.domain.comment.repository.CommentLikeRepository;
import com.uphouse.monew.domain.comment.repository.CommentRepository;
import com.uphouse.monew.domain.user.domain.User;
import com.uphouse.monew.domain.user.repository.UserRepository;
import com.uphouse.monew.global.exception.ExceptionCode;
import com.uphouse.monew.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentLikeCreateResponse create(UUID commentId, UUID userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ExceptionCode.COMMENT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ExceptionCode.USER_NOT_FOUND));

        CommentLike commentLike = new CommentLike(commentId, userId);
        CommentLike newCommentLike = commentLikeRepository.save(commentLike);

        return CommentLikeCreateResponse.from(newCommentLike, comment, user.getNickname());
    }
}
