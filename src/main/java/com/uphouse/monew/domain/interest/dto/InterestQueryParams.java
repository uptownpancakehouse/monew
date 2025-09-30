package com.uphouse.monew.domain.interest.dto;

import jakarta.validation.constraints.NotBlank;

public record InterestQueryParams(
        String keyword,

        @NotBlank(message = "정렬 속성은 필수 값입니다.")
        String orderBy,

        @NotBlank(message = "정렬 방향은 필수 값입니다.")
        String direction,

        String cursor,

        String after,

        Integer limit
) {
    public InterestQueryParams {
        if(limit == null || limit < 1) limit = 50;
    }
}
