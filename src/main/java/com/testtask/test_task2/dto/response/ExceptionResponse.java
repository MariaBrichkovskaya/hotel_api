package com.testtask.test_task2.dto.response;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(String message, HttpStatus httpStatus) {
}
