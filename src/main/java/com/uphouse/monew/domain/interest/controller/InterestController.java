package com.uphouse.monew.domain.interest.controller;

import com.uphouse.monew.domain.interest.dto.InterestCreateRequest;
import com.uphouse.monew.domain.interest.dto.InterestDto;
import com.uphouse.monew.domain.interest.dto.InterestQueryParams;
import com.uphouse.monew.domain.interest.dto.InterestSubscribeResponse;
import com.uphouse.monew.domain.interest.service.InterestService;
import com.uphouse.monew.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interests")
public class InterestController {

    private final InterestService interestService;

    @PostMapping
    public ResponseEntity<InterestDto> create(@RequestBody InterestCreateRequest request) {
        InterestDto response = interestService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse> getInterests(@RequestHeader("Monew-Request-User-ID") UUID userId, InterestQueryParams  params) {
         PageResponse response = interestService.getInterests(userId, params);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{interestId}")
    public ResponseEntity<InterestDto> update(@PathVariable Long interestId, @RequestBody Map<String, List<String>> body) {
        Set<String> keywords = new HashSet<>(body.get("keywords")); // 중복된 키워드 제거
        InterestDto response = interestService.update(interestId, keywords);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{interestId}/subscriptions")
    public ResponseEntity<InterestSubscribeResponse> subscribe(@PathVariable Long interestId, @RequestHeader("Monew-Request-User-ID") UUID userId) {
        InterestSubscribeResponse response = interestService.subscribe(interestId, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{interestId}/subscriptions")
    public ResponseEntity<String> unsubscribe(@PathVariable Long interestId, @RequestHeader("Monew-Request-User-ID") UUID userId) {
        interestService.unsubscribe(interestId, userId);
        return ResponseEntity.ok("success");
    }
}
