package com.uphouse.monew.domain.user.dto.response;

import com.uphouse.monew.domain.user.domain.User;

import java.time.LocalDateTime;

public record UserSignUpResponse (
    Long id,
    String email,
    String nickname,
    LocalDateTime createdAt
) {
    public static UserSignUpResponse from(User user) {
        return new UserSignUpResponse(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            LocalDateTime.now()
        );
    }
}
