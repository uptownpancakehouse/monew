package com.uphouse.monew.domain.user.controller;

import com.uphouse.monew.common.config.ApiPath;
import com.uphouse.monew.domain.user.dto.request.UserSignUpRequest;
import com.uphouse.monew.domain.user.dto.response.UserSignUpResponse;
import com.uphouse.monew.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping(ApiPath.User.BASE)
    public ResponseEntity<UserSignUpResponse> signUp(
        @RequestBody UserSignUpRequest request
    ) {
        UserSignUpResponse response = userService.signUp(request);
        return ResponseEntity.status(201).body(response);
    }

}
