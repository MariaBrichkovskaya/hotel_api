package com.testtask.test_task2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@EqualsAndHashCode
@Builder
@NoArgsConstructor
public class AddressDto {
    @NotNull
    @Min(value = 1, message = MIN_VALUE_MESSAGE)
    int houseNumber;
    @NotBlank(message = STREET_NOT_EMPTY_MESSAGE)
    String street;
    @NotBlank(message = CITY_NOT_EMPTY_MESSAGE)
    String city;
    @NotBlank(message = COUNTRY_NOT_EMPTY_MESSAGE)
    String country;
    @NotBlank(message = POST_CODE_NOT_EMPTY_MESSAGE)
    String postCode;
}