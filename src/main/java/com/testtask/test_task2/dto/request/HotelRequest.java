package com.testtask.test_task2.dto.request;

import com.testtask.test_task2.dto.AddressDto;
import com.testtask.test_task2.dto.ArrivalTimeDto;
import com.testtask.test_task2.dto.ContactDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static com.testtask.test_task2.util.MessageUtil.*;

@Builder
public record HotelRequest(@NotBlank(message = NAME_NOT_EMPTY_MESSAGE) String name,
                           String description,
                           @NotBlank(message = BRAND_NOT_EMPTY_MESSAGE) String brand,
                           @Valid @NotNull(message = ADDRESS_NOT_EMPTY_MESSAGE) AddressDto address,
                           @Valid @NotNull(message = CONTACTS_NOT_EMPTY_MESSAGE) ContactDto contacts,
                           @Valid @NotNull(message = TIME_NOT_EMPTY_MESSAGE) ArrivalTimeDto arrivalTime) {
}
