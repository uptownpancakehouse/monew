package com.uphouse.monew.domain.interest.controller;

import com.uphouse.monew.domain.interest.dto.InterestCreateRequest;
import com.uphouse.monew.domain.interest.dto.InterestCreateResponse;
import com.uphouse.monew.domain.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interests")
public class InterestController {

    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<InterestCreateResponse> create(@RequestBody InterestCreateRequest request) {
        InterestCreateResponse response = interestService.create(request);
        return ResponseEntity.ok(response);
    }
}
