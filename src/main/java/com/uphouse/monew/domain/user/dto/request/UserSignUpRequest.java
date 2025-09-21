package com.uphouse.monew.domain.user.dto.request;

import com.uphouse.monew.domain.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSignUpRequest(
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수 값입니다.")
    String email,

    @NotBlank(message = "닉네임은 필수 값입니다.")
    String nickname,

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    String password
) {
    public User toEntity() {
        return new User(email, nickname, password);
    }
}
