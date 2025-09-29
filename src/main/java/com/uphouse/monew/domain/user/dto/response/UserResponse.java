package com.uphouse.monew.domain.user.dto.response;

import com.uphouse.monew.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
    UUID id,
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
