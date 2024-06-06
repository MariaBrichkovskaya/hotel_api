package com.testtask.test_task2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class ArrivalTimeDto {
    @NotBlank(message = CHECK_IN_NOT_EMPTY_MESSAGE)
    @Pattern(regexp = "([01][0-9]|2[0-3]):([0-5][0-9])", message = TIME_FORMAT_INVALID_MESSAGE)
    @Schema(name = "Check-in time", example = "14:00")
    String checkIn;
    @Schema(name = "Check-out time", example = "12:00")
    @Pattern(regexp = "([01][0-9]|2[0-3]):([0-5][0-9])", message = TIME_FORMAT_INVALID_MESSAGE)
    String checkOut;
}
