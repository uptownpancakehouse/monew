package com.uphouse.monew.domain.interest.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record InterestDto(
        Long id,
        String name,
        List<String> keywords,
        int subscriberCount,
        Boolean subscribedByMe
) {}
