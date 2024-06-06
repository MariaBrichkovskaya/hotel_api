package com.testtask.test_task2.exception;

import lombok.Getter;

import java.util.Map;

import static com.testtask.test_task2.util.MessageUtil.*;


@Getter
public class AlreadyExistsException extends RuntimeException {
    private final Map<String, String> errors;

    public AlreadyExistsException(Map<String, String> errors) {
        super(HOTEL_ALREADY_EXISTS_MESSAGE);
        this.errors = errors;
    }
}
