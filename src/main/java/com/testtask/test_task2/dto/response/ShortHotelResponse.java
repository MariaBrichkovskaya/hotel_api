package com.testtask.test_task2.dto.response;

import lombok.Builder;

@Builder
public record ShortHotelResponse(long id,
                                 String name,
                                 String description,
                                 String address,
                                 String phone) {
}
