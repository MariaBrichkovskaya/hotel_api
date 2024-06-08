package com.testtask.test_task2.service;

import com.testtask.test_task2.dto.AddressDto;
import com.testtask.test_task2.dto.ArrivalTimeDto;
import com.testtask.test_task2.dto.ContactDto;
import com.testtask.test_task2.dto.request.HotelRequest;
import com.testtask.test_task2.dto.response.HistogramResponse;
import com.testtask.test_task2.dto.response.HotelResponse;
import com.testtask.test_task2.dto.response.ShortHotelResponse;
import com.testtask.test_task2.entity.Address;
import com.testtask.test_task2.entity.Amenity;
import com.testtask.test_task2.entity.ArrivalTime;
import com.testtask.test_task2.entity.Contact;
import com.testtask.test_task2.entity.Hotel;
import com.testtask.test_task2.exception.AlreadyExistsException;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public List<ShortHotelResponse> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(this::fromHotelToShortResponse)
                .toList();
    }

    public HotelResponse getHotelById(long id) {
        Hotel hotel = checkHotelExists(id);
        return fromHotelToResponse(hotel);
    }


    public List<ShortHotelResponse> searchHotels(String city, String name, String brand, String country, List<String> amenities) {

        List<Hotel> hotels = hotelRepository.findAll();
        if (city != null) {
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getAddress().getCity().equalsIgnoreCase(city))
                    .toList();
        }
        if (name != null) {
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getName().equalsIgnoreCase(name))
                    .toList();
        }
        if (brand != null) {
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getBrand().equalsIgnoreCase(brand))
                    .toList();
        }
        if (country != null) {
            hotels = hotels.stream()
                    .filter(hotel -> hotel.getAddress().getCounty().equalsIgnoreCase(country))
                    .toList();
        }
        if (amenities != null && !amenities.isEmpty()) {
            hotels = hotels.stream()
                    .filter(hotel -> amenities.stream()
                            .allMatch(amenity -> hotel.getAmenities().stream()
                                    .anyMatch(hotelAmenity -> hotelAmenity.getName().equalsIgnoreCase(amenity))))
                    .toList();
        }

        return hotels.stream()
                .map(this::fromHotelToShortResponse)
                .toList();
    }

    public ShortHotelResponse createHotel(HotelRequest hotelRequest) {
        checkCreateDataIsUnique(hotelRequest);
        Address address = addressRepository.save(modelMapper.map(hotelRequest.address(), Address.class));
        Contact contact = contactRepository.save(modelMapper.map(hotelRequest.contacts(), Contact.class));
        ArrivalTime arrivalTime = arrivalTimeRepository.save(modelMapper.map(hotelRequest.arrivalTime(), ArrivalTime.class));
        Hotel hotel = hotelRepository.save(
                Hotel.builder()
                        .description(hotelRequest.description())
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

    public HotelResponse addAmenitiesToHotel(long id, List<String> amenityNames) {
        Hotel hotel = checkHotelExists(id);
        List<Amenity> currentAmenities = new ArrayList<>(hotel.getAmenities());
        List<Amenity> newAmenities = findOrCreateAmenities(amenityNames, currentAmenities);
        currentAmenities.addAll(newAmenities);
        hotel.setAmenities(currentAmenities);
        hotelRepository.save(hotel);
        return fromHotelToResponse(hotel);
    }

    public Map<String, Long> histogram(String param) {
        Map<String, Long> map = new HashMap<>();
        List<HistogramResponse> histogram = switch (param) {
            case "city" -> hotelRepository.getCityHistogram();
            case "brand" -> hotelRepository.getBrandyHistogram();
            case "county" -> hotelRepository.getCountyHistogram();
            case "amenities" -> hotelRepository.getAmenityHistogram();
            default -> throw new IllegalStateException(ILLEGAL_STATE_MESSAGE.formatted(param));
        };
        histogram
                .forEach(histogramResponse ->
                        map.put(histogramResponse.param(), histogramResponse.amount())
                );
        return map;
    }

    private Hotel checkHotelExists(long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_WITH_ID_MESSAGE.formatted(id)));
    }

    private List<Amenity> findOrCreateAmenities(List<String> amenityNames, List<Amenity> hotelAmenities) {
        List<Amenity> amenities = new ArrayList<>();
        for (String amenityName : amenityNames) {
            Optional<Amenity> existingAmenity = amenityRepository.findByName(amenityName);
            if (existingAmenity.isPresent()) {
                if (!hotelAmenities.contains(existingAmenity.get())) {
                    amenities.add(existingAmenity.get());
                }
            } else {
                Amenity newAmenity = amenityRepository.save(
                        Amenity.builder()
                                .name(amenityName)
                                .build()
                );
                amenities.add(newAmenity);
            }
        }
        return amenities;
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

    private void checkEmailIsUnique(String email, Map<String, String> errors) {
        if (hotelRepository.existsHotelByContact_Email(email)) {
            errors.put(
                    "email",
                    String.format(HOTEL_WITH_EMAIL_EXISTS_MESSAGE, email)
            );
        }
    }

    private void checkPhoneIsUnique(String phone, Map<String, String> errors) {
        if (hotelRepository.existsHotelByContact_Phone(phone)) {
            errors.put(
                    "phone",
                    String.format(HOTEL_WITH_PHONE_EXISTS_MESSAGE, phone)
            );
        }
    }

    private void checkCreateDataIsUnique(HotelRequest request) {
        var errors = new HashMap<String, String>();

        checkEmailIsUnique(request.contacts().getEmail(), errors);
        checkPhoneIsUnique(request.contacts().getPhone(), errors);

        if (!errors.isEmpty()) {
            throw new AlreadyExistsException(errors);
        }
    }

}
