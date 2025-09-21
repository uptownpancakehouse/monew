package com.uphouse.monew.domain.user.service;

import com.uphouse.monew.domain.user.domain.User;
import com.uphouse.monew.domain.user.dto.request.UserSignUpRequest;
import com.uphouse.monew.domain.user.dto.response.UserSignUpResponse;
import com.uphouse.monew.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        User user = userRepository.save(request.toEntity());
        return UserSignUpResponse.from(user);
    }
}
