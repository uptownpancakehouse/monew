package com.uphouse.monew.domain.comment.dto.request;

import lombok.Getter;

@Getter
public enum CommentOrderBy {
    createdAt("created_at"),
    likeCount("like_count");

    private final String column;

    CommentOrderBy(String column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return column;
    }
}