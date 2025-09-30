package com.uphouse.monew.domain.interest.controller;

import com.uphouse.monew.domain.interest.dto.InterestCreateRequest;
import com.uphouse.monew.domain.interest.dto.InterestCreateResponse;
import com.uphouse.monew.domain.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PatchMapping("/{interestId}")
    public ResponseEntity<InterestCreateResponse> update(@PathVariable Long interestId, @RequestBody Map<String, List<String>> body) {
        Set<String> keywords = new HashSet<>(body.get("keywords")); // 중복된 키워드 제거
        InterestCreateResponse response = interestService.update(interestId, keywords);
        return ResponseEntity.ok(response);
    }
}
