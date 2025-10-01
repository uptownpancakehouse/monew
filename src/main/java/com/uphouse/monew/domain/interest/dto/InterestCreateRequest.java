package com.uphouse.monew.domain.interest.dto;

import java.util.Set;

public record InterestCreateRequest(
        String name,
        Set<String> keywords // 중복된 키워드 제거
) {}
