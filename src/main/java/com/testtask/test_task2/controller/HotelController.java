package com.testtask.test_task2.controller;

import com.testtask.test_task2.dto.request.HotelRequest;
import com.testtask.test_task2.dto.response.HotelResponse;
import com.testtask.test_task2.dto.response.ListHotelsResponse;
import com.testtask.test_task2.dto.response.ShortHotelResponse;
import com.testtask.test_task2.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/hotels")
    public ListHotelsResponse getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    public HotelResponse getHotelById(@PathVariable long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/search")
    public List<ShortHotelResponse> searchHotels(@RequestParam(required = false) String city,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) String country,
                                                 @RequestParam(required = false) List<String> amenities) {
        return hotelService.searchHotels(city, name, brand, country, amenities);
    }

    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortHotelResponse createHotel(@RequestBody @Valid HotelRequest hotelRequest) {
        return hotelService.createHotel(hotelRequest);
    }

    @PostMapping("/hotels/{id}/amenities")
    public void addAmenitiesToHotel(@PathVariable long id, @RequestBody List<String> amenitiesNames) {
        hotelService.addAmenitiesToHotel(id, amenitiesNames);
    }

    @GetMapping("/histogram/{param}")
    public Map<String, Long> histogram(@PathVariable String param) {
        return hotelService.histogram(param);
    }
}
