package com.testtask.test_task2.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Builder
public record ValidationExceptionResponse(HttpStatus status,
                                          String message,
                                          Map<String, String> errors) {
}