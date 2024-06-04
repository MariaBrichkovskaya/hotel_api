package com.testtask.test_task2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.testtask.test_task2.util.MessageUtil.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    //паттерн
    @NotBlank(message = PHONE_NOT_EMPTY_MESSAGE)
    String phone;
    @NotBlank(message = EMAIL_NOT_EMPTY_MESSAGE)
    @Email(message = EMAIL_INVALID_MESSAGE)
    String email;
}
