package com.testtask.test_task2.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ListHotelsResponse(List<ShortHotelResponse> hotels) {
}
