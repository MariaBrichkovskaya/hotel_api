package com.testtask.test_task2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import static com.testtask.test_task2.util.MessageUtil.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@NoArgsConstructor
public class ContactDto {
    @Pattern(regexp = "\\+375\\s(17|25|29|33|44)\\s\\d{3}-\\d{2}-\\d{2}", message = PHONE_FORMAT_INVALID_MESSAGE)
    @NotBlank(message = PHONE_NOT_EMPTY_MESSAGE)
    @Schema(name = "Phone number", example = "+375 17 309-80-00")
    String phone;
    @NotBlank(message = EMAIL_NOT_EMPTY_MESSAGE)
    @Schema(name = "Email address", example = "doubletreeminsk.info@hilton.com")
    @Email(message = EMAIL_INVALID_MESSAGE)
    String email;
}
