package com.testtask.test_task2.dto.request;

import com.testtask.test_task2.dto.AddressDto;
import com.testtask.test_task2.dto.ArrivalTimeDto;
import com.testtask.test_task2.dto.ContactDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static com.testtask.test_task2.util.MessageUtil.*;

@Builder
@Schema(description = "Request for hotel creation")
public record HotelRequest(
        @NotBlank(message = NAME_NOT_EMPTY_MESSAGE) @Schema(example = "DoubleTree by Hilton Minsk") String name,
        @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...") String description,
        @NotBlank(message = BRAND_NOT_EMPTY_MESSAGE) @Schema(example = "DoubleTree by Hilton Minsk") String brand,
        @Valid @NotNull(message = ADDRESS_NOT_EMPTY_MESSAGE) AddressDto address,
        @Valid @NotNull(message = CONTACTS_NOT_EMPTY_MESSAGE) ContactDto contacts,
        @Valid @NotNull(message = TIME_NOT_EMPTY_MESSAGE) ArrivalTimeDto arrivalTime) {
}
