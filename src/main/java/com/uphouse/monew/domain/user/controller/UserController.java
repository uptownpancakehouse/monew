package com.uphouse.monew.domain.user.controller;

import com.uphouse.monew.domain.user.dto.request.UserUpdateRequest;
import com.uphouse.monew.global.config.ApiHeader;
import com.uphouse.monew.global.config.ApiPath;
import com.uphouse.monew.domain.user.dto.request.UserLogInRequest;
import com.uphouse.monew.domain.user.dto.request.UserSignUpRequest;
import com.uphouse.monew.domain.user.dto.response.UserResponse;
import com.uphouse.monew.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping(ApiPath.User.BASE)
    public ResponseEntity<UserResponse> signUp(@RequestBody UserSignUpRequest request) {
        UserResponse response = userService.signUp(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping(ApiPath.User.LOGIN)
    public ResponseEntity<UserResponse> login(@RequestBody UserLogInRequest request) {
        UserResponse response = userService.login(request);
        return ResponseEntity.status(200)
                .header(ApiHeader.MONEW_REQUEST_ID, response.id().toString())
                .body(response);
    }

    @PatchMapping(ApiPath.User.UPDATE)
    public ResponseEntity<UserResponse> update(
        @RequestParam Long id,
        @RequestBody UserUpdateRequest request
    ) {
        UserResponse response = userService.update(id, request);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{userId}/hard")
    public ResponseEntity<Void> hardDelete(@PathVariable Long userId) {
        userService.deleteHard(userId);
        return ResponseEntity.status(204).build();
    }

}
