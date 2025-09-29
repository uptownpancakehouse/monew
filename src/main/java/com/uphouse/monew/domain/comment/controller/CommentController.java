package com.uphouse.monew.domain.comment.controller;

import com.uphouse.monew.domain.comment.dto.request.*;
import com.uphouse.monew.domain.comment.dto.response.CommentCreateResponse;
import com.uphouse.monew.domain.comment.dto.response.CommentCursorResponse;
import com.uphouse.monew.domain.comment.service.CommentService;
import com.uphouse.monew.global.config.ApiHeader;
import com.uphouse.monew.global.config.ApiPath;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(ApiPath.Comment.BASE)
    public ResponseEntity<CommentCursorResponse> list(
        @RequestHeader(ApiHeader.MONEW_REQUEST_ID) UUID userId,
        @RequestParam CommentOrderBy orderBy,
        @RequestParam CommentSortDirection direction,
        @RequestParam(required = false) UUID articleId,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime cursor,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after,
        @RequestParam Integer limit
    ) {
        CommentCursorRequest request = new CommentCursorRequest(articleId, orderBy, direction, cursor, after, limit);
        CommentCursorResponse response = commentService.getComments(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping(ApiPath.Comment.BASE)
    public ResponseEntity<CommentCreateResponse> create(@RequestBody CommentCreateRequest request) {
        CommentCreateResponse response = commentService.create(request);
        return ResponseEntity.status(200).body(response);
    }
}
