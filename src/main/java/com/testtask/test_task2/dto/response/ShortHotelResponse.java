package com.testtask.test_task2.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ShortHotelResponse(@Schema(example = "1") long id,
                                 @Schema(example = "DoubleTree by Hilton Minsk") String name,
                                 @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms " +
                                         "in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...")
                                 String description,
                                 @Schema(example = "9 Pobediteley Avenue, Minsk, 220004, Belarus") String address,
                                 @Schema(example = "+375 17 309-80-00") String phone) {
}
