package com.testtask.test_task2.dto.response;

import com.testtask.test_task2.dto.AddressDto;
import com.testtask.test_task2.dto.ArrivalTimeDto;
import com.testtask.test_task2.dto.ContactDto;
import lombok.Builder;

import java.util.List;

@Builder
public record HotelResponse(long id,
                            String name,
                            String brand,
                            AddressDto address,
                            ContactDto contacts,
                            ArrivalTimeDto arrivalTime,
                            List<String> amenities
) {
}

