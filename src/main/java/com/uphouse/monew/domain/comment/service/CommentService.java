package com.uphouse.monew.domain.comment.service;

import com.uphouse.monew.domain.comment.domain.Comment;
import com.uphouse.monew.domain.comment.dto.request.*;
import com.uphouse.monew.domain.comment.dto.response.CommentCreateResponse;
import com.uphouse.monew.domain.comment.dto.response.CommentCursorResponse;
import com.uphouse.monew.domain.comment.repository.CommentRepository;
import com.uphouse.monew.domain.comment.repository.CommentUserRepository;
import com.uphouse.monew.domain.user.domain.User;
import com.uphouse.monew.domain.user.repository.UserRepository;
import com.uphouse.monew.global.exception.ExceptionCode;
import com.uphouse.monew.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentUserRepository commentUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentCreateResponse create(CommentCreateRequest request) {
        Comment comment = commentRepository.save(request.toEntity());
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new GlobalException(ExceptionCode.USER_NOT_FOUND));
        return CommentCreateResponse.from(comment, user.getNickname());
    }

    public CommentCursorResponse getComments(CommentCursorRequest request) {
        return commentUserRepository.findByArticleIdWithCursor(request);
    }
}
