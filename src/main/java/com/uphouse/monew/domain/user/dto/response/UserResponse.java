package com.uphouse.monew.domain.user.dto.response;

import com.uphouse.monew.domain.user.domain.User;

import java.time.LocalDateTime;

public record UserResponse(
    Long id,
    String email,
    String nickname,
    LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getNickname(),
            user.getCreatedAt()
        );
    }
}
