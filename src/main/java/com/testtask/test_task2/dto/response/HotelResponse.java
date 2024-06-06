package com.testtask.test_task2.dto.response;

import com.testtask.test_task2.dto.AddressDto;
import com.testtask.test_task2.dto.ArrivalTimeDto;
import com.testtask.test_task2.dto.ContactDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record HotelResponse(@Schema(example = "1") long id,
                            @Schema(example = "DoubleTree by Hilton Minsk") String name,
                            @Schema(example = "Hilton") String brand,
                            AddressDto address,
                            ContactDto contacts,
                            ArrivalTimeDto arrivalTime,
                            List<String> amenities
) {
}

