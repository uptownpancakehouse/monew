package com.uphouse.monew.global.exception.dto;

public record ExceptionResponse(String httpMethod, String path, String code, String message) {
}
