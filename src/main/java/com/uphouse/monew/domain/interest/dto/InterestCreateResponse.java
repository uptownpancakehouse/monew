package com.uphouse.monew.domain.interest.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record InterestCreateResponse(
        Long id,
        String name,
        List<String> keywords,
        Long subscriberCount,
        boolean subscribedByMe
) {}
