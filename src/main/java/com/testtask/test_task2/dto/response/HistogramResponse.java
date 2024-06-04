package com.testtask.test_task2.dto.response;

import lombok.Builder;

@Builder
public record HistogramResponse(String param,
                                long amount) {
}
