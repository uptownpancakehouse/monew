package com.uphouse.monew.domain.interest.dto;

import java.util.List;

public record InterestCreateRequest(
        String name,
        List<String> keywords
) {}
