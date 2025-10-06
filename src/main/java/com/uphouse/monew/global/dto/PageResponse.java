package com.uphouse.monew.global.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse(
        List<?> content,
        String nextCursor,
        String nextAfter,
        int size,
        Long totalElements,
        Boolean hasNext
) {}
