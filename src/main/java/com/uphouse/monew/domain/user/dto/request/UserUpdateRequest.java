package com.uphouse.monew.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
    @NotBlank(message = "닉네임은 필수 값입니다.")
    String nickname
) {}
