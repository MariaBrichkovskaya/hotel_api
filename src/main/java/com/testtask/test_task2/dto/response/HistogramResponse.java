package com.testtask.test_task2.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record HistogramResponse(@Schema(name = "Name of histogram parameter", example = "Minsk") String param,
                                @Schema(name = "Amount of those parameters after grouping", example = "4") long amount) {
}
