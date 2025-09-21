package com.uphouse.monew.common.config;

public final class ApiPath {

    private ApiPath() { }

    public static final String API_BASE = "/api";

    public static final class User {
        public static final String BASE = API_BASE + "/users";
        public static final String LOGIN = BASE + "/login";
        public static final String UPDATE = BASE + "/{id}";
        public static final String HARD_DELETE = BASE + "/{id}" + "/hard";
    }
}