package com.uphouse.monew.global.config;

public final class ApiPath {

    private ApiPath() { }

    public static final String API_BASE = "/api";

    public static final class User {
        public static final String BASE = API_BASE + "/users";
        public static final String LOGIN = BASE + "/login";
        public static final String UPDATE = BASE + "/{id}";
        public static final String HARD_DELETE = BASE + "/{id}" + "/hard";
    }

    public static final class Article {
        public static final String BASE = API_BASE + "/articles";
        public static final String CREATE = BASE + "/{id}/article-views";
    }

    public static final class Comment {
        public static final String BASE = API_BASE + "/comments";
        public static final String COMMENT_ID = BASE + "/{commentId}";
        public static final String COMMENT_LIKES = COMMENT_ID + "/comment-likes";
        public static final String COMMENT_HARD_DELETE = COMMENT_ID + "/hard";
    }
}