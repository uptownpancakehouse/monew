package com.uphouse.monew.domain.interest.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record InterestSubscribeResponse(
        UUID id,                        // 사용자 아이디
        Long interestId,
        String interestName,
        List<String> interestKeywords,  // 관심사 목록
        int interestSubscriberCount,
        LocalDateTime createdAt
) {}
