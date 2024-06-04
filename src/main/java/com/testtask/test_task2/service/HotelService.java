package com.testtask.test_task2.service;

import com.testtask.test_task2.dto.AddressDto;
import com.testtask.test_task2.dto.ArrivalTimeDto;
import com.testtask.test_task2.dto.ContactDto;
import com.testtask.test_task2.dto.request.HotelRequest;
import com.testtask.test_task2.dto.response.HistogramResponse;
import com.testtask.test_task2.dto.response.HotelResponse;
import com.testtask.test_task2.dto.response.ListHotelsResponse;
import com.testtask.test_task2.dto.response.ShortHotelResponse;
import com.testtask.test_task2.entity.Address;
import com.testtask.test_task2.entity.Amenity;
import com.testtask.test_task2.entity.ArrivalTime;
import com.testtask.test_task2.entity.Contact;
import com.testtask.test_task2.entity.Hotel;
import com.testtask.test_task2.exception.NotFoundException;
import com.testtask.test_task2.repository.AddressRepository;
import com.testtask.test_task2.repository.AmenityRepository;
import com.testtask.test_task2.repository.ArrivalTimeRepository;
import com.testtask.test_task2.repository.ContactRepository;
import com.testtask.test_task2.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.testtask.test_task2.util.MessageUtil.*;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final ArrivalTimeRepository arrivalTimeRepository;
    private final AmenityRepository amenityRepository;

    public ListHotelsResponse getAllHotels() {
        List<ShortHotelResponse> hotels = hotelRepository.findAll().stream()
                .map(this::fromHotelToShortResponse)
                .toList();

        return ListHotelsResponse.builder()
                .hotels(hotels)
                .build();
    }

    public HotelResponse getHotelById(long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_WITH_ID_MESSAGE.formatted(id)));
        return fromHotelToResponse(hotel);
    }

    private HotelResponse fromHotelToResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(modelMapper.map(hotel.getAddress(), AddressDto.class))
                .brand(hotel.getBrand())
                .arrivalTime(modelMapper.map(hotel.getArrivalTime(), ArrivalTimeDto.class))
                .contacts(modelMapper.map(hotel.getContact(), ContactDto.class))
                .amenities(hotel.getAmenities().stream()
                        .map(Amenity::getName)
                        .toList())
                .build();
    }

    private ShortHotelResponse fromHotelToShortResponse(Hotel hotel) {
        return ShortHotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .phone(hotel.getContact().getPhone())
                .address(hotel.getAddress().toString())
                .build();
    }

    //возможно дто с листом, надо подумать, но если так, что гетол надо поменять
    public List<ShortHotelResponse> searchHotels(String city, String name, String brand, String country, List<String> amenities) {
        List<Hotel> hotels = new ArrayList<>();

        if (city != null) {
            hotels.addAll(hotelRepository.findByCityIgnoreCase(city));
        }
        if (name != null) {
            hotels.addAll(hotelRepository.findByNameIgnoreCase(name));
        }
        if (brand != null) {
            hotels.addAll(hotelRepository.findByBrandIgnoreCase(brand));
        }
        if (country != null) {
            hotels.addAll(hotelRepository.findByCountryIgnoreCase(country));
        }
        if (amenities != null && !amenities.isEmpty()) {
            hotels.addAll(hotelRepository.findByAmenitiesIgnoreCase(amenities));
        }

        Set<Hotel> uniqueHotels = new HashSet<>(hotels);

        return uniqueHotels.stream()
                .map(this::fromHotelToShortResponse)
                .toList();
    }

    public ShortHotelResponse createHotel(HotelRequest hotelRequest) {

        Address address = addressRepository.save(modelMapper.map(hotelRequest.address(), Address.class));
        Contact contact = contactRepository.save(modelMapper.map(hotelRequest.contacts(), Contact.class));
        ArrivalTime arrivalTime = arrivalTimeRepository.save(modelMapper.map(hotelRequest.arrivalTime(), ArrivalTime.class));
        Hotel hotel = hotelRepository.save(
                Hotel.builder()
                        .name(hotelRequest.name())
                        .address(address)
                        .brand(hotelRequest.brand())
                        .arrivalTime(arrivalTime)
                        .contact(contact)
                        .build()
        );
        return ShortHotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .phone(hotel.getContact().getPhone())
                .address(hotel.getAddress().toString())
                .build();
    }

    public void addAmenitiesToHotel(long id, List<String> amenityNames) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_WITH_ID_MESSAGE.formatted(id)));

        List<Amenity> amenities = new ArrayList<>();
        for (String amenityName : amenityNames) {
            Optional<Amenity> existingAmenity = amenityRepository.findByName(amenityName);
            if (existingAmenity.isPresent()) {
                amenities.add(existingAmenity.get());
            } else {
                Amenity newAmenity = new Amenity();
                newAmenity.setName(amenityName);
                amenityRepository.save(newAmenity);
                amenities.add(newAmenity);
            }
        }
        hotel.getAmenities().addAll(amenities);
        hotelRepository.save(hotel);
    }

    public Map<String, Long> histogram(String param) {
        Map<String, Long> map = new HashMap<>();
        List<HistogramResponse> histogram = switch (param) {
            case "city" -> hotelRepository.getCityHistogram();
            case "brand" -> hotelRepository.getBrandyHistogram();
            case "country" -> hotelRepository.getCountryHistogram();
            case "amenities" -> hotelRepository.getAmenityHistogram();
            default -> throw new IllegalStateException(ILLEGAL_STATE_MESSAGE.formatted(param));
        };
        histogram
                .forEach(histogramResponse ->
                        map.put(histogramResponse.param(), histogramResponse.amount())
                );
        return map;
    }
}
