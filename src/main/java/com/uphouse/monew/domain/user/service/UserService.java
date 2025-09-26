package com.uphouse.monew.domain.user.service;

import com.uphouse.monew.domain.user.domain.User;
import com.uphouse.monew.domain.user.dto.request.UserLogInRequest;
import com.uphouse.monew.domain.user.dto.request.UserSignUpRequest;
import com.uphouse.monew.domain.user.dto.request.UserUpdateRequest;
import com.uphouse.monew.domain.user.dto.response.UserResponse;
import com.uphouse.monew.domain.user.repository.UserRepository;
import com.uphouse.monew.global.exception.ExceptionCode;
import com.uphouse.monew.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse signUp(UserSignUpRequest request) {
        validateEmail(request.email());
        User user = request.toEntity();
        userRepository.save(user);
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse login(UserLogInRequest request) {
        User user = userRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new GlobalException(ExceptionCode.USER_NOT_FOUND));
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse update(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ExceptionCode.USER_NOT_FOUND));
        user.changeNickname(request.nickname());
        return UserResponse.from(user);
    }

    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ExceptionCode.USER_NOT_FOUND));
        user.delete();
        userRepository.save(user);
    }

    @Transactional
    public void deleteHard(Long userId) {
        // TODO(robolovo) - 논리 삭제 후 1일 경과 뒤 유저 정보가 완전히 삭제되도록 하세요.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ExceptionCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    private void validateEmail(String email) {
        boolean exist = userRepository.existsByEmail(email);
        if (exist) {
            throw new GlobalException(ExceptionCode.USER_EMAIL_ALREADY_USED);
        }
    }
}
