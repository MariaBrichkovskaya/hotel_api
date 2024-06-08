package com.testtask.test_task2.controller;

import com.testtask.test_task2.dto.request.HotelRequest;
import com.testtask.test_task2.dto.response.HotelResponse;
import com.testtask.test_task2.dto.response.ShortHotelResponse;
import com.testtask.test_task2.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get short information about all hotels")
    public List<ShortHotelResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "Get full hotel info by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retrieving hotel by id was successful"),
            @ApiResponse(responseCode = "404", description = "Hotel is not found")})
    public HotelResponse getHotelById(@PathVariable long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Search hotels by city, name, brand, county and amenities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Searching hotels by params was successful")})
    public List<ShortHotelResponse> searchHotels(@RequestParam(required = false) String city,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) String county,
                                                 @RequestParam(required = false) List<String> amenities) {
        return hotelService.searchHotels(city, name, brand, county, amenities);
    }

    @PostMapping("/hotels")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "201", description = "Hotel was created successfully"),
            @ApiResponse(responseCode = "409", description = "Hotel with such phone or email already exists")
    })
    public ShortHotelResponse createHotel(@RequestBody @Valid HotelRequest hotelRequest) {
        return hotelService.createHotel(hotelRequest);
    }

    @PostMapping("/hotels/{id}/amenities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Amenities was added to hotel successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel is not found")})
    @Operation(summary = "Add list of amenities to hotel by id")
    public HotelResponse addAmenitiesToHotel(@PathVariable long id, @RequestBody List<String> amenitiesNames) {
        return hotelService.addAmenitiesToHotel(id, amenitiesNames);
    }

    @GetMapping("/histogram/{param}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid param"),
            @ApiResponse(responseCode = "200", description = "Histogram by params was successful")})
    @Operation(summary = "Get the number of hotels grouped by each value of the specified parameter")
    public Map<String, Long> histogram(@PathVariable String param) {
        return hotelService.histogram(param);
    }
}
