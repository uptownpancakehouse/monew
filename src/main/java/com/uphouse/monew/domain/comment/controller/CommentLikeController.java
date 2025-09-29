package com.uphouse.monew.domain.comment.controller;

import com.uphouse.monew.domain.comment.service.CommentLikeService;
import com.uphouse.monew.global.config.ApiHeader;
import com.uphouse.monew.global.config.ApiPath;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping(ApiPath.Comment.COMMENT_LIKES)
    public ResponseEntity<?> createLike(
            @PathVariable UUID commentId,
            @RequestHeader(ApiHeader.MONEW_REQUEST_ID) UUID userId
    ) {
        commentLikeService.create(commentId, userId);
        return ResponseEntity.status(200).body(null);
    }
}
