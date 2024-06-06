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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.testtask.test_task2.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private ArrivalTimeRepository arrivalTimeRepository;
    @Mock
    private AmenityRepository amenityRepository;
    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void getAllHotels() {
        doReturn(getHotels())
                .when(hotelRepository)
                .findAll();
        List<ShortHotelResponse> expected = getHotelsResponseList();

        List<ShortHotelResponse> actual = hotelService.getAllHotels();

        assertEquals(expected, actual);
        verify(hotelRepository).findAll();
    }

    @Test
    void getHotelById_whenHotelNotFound_shouldThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(hotelRepository)
                .findById(DEFAULT_ID);
        assertThrows(
                NotFoundException.class,
                () -> hotelService.getHotelById(DEFAULT_ID)
        );
        verify(hotelRepository).findById(DEFAULT_ID);
    }

    @Test
    void getHotelById_whenHotelExists_shouldReturnHotelResponse() {
        doReturn(Optional.of(getDefaultHotel()))
                .when(hotelRepository)
                .findById(DEFAULT_ID);
        HotelResponse expected = getDefaultHotelResponse();

        HotelResponse actual = hotelService.getHotelById(DEFAULT_ID);

        assertEquals(expected, actual);
        verify(hotelRepository).findById(DEFAULT_ID);
        verify(modelMapper).map(any(Address.class), eq(AddressDto.class));
        verify(modelMapper).map(any(ArrivalTime.class), eq(ArrivalTimeDto.class));
        verify(modelMapper).map(any(Contact.class), eq(ContactDto.class));
    }

    @Test
    void searchHotels_shouldReturnEmptyList() {
        doReturn(getHotels())
                .when(hotelRepository)
                .findAll();
        List<ShortHotelResponse> expected = List.of();

        List<ShortHotelResponse> actual = hotelService.searchHotels(
                NEW_CITY,
                DEFAULT_NAME,
                DEFAULT_BRAND,
                DEFAULT_COUNTRY,
                null
        );

        assertEquals(expected, actual);
        verify(hotelRepository).findAll();
    }

    @Test
    void searchHotels_withExistingParameters_shouldReturnHotels() {
        doReturn(getHotels())
                .when(hotelRepository)
                .findAll();
        List<ShortHotelResponse> expected = getHotelsResponseList();

        List<ShortHotelResponse> actual = hotelService.searchHotels(null,
                null,
                DEFAULT_BRAND,
                DEFAULT_COUNTRY,
                null
        );

        assertEquals(expected, actual);
        verify(hotelRepository).findAll();
    }

    @Test
    void createHotel_whenDataIsValid_shouldReturnHotelResponse() {
        doReturn(false)
                .when(hotelRepository)
                .existsHotelByContact_Phone(anyString());
        doReturn(false)
                .when(hotelRepository)
                .existsHotelByContact_Email(anyString());
        doReturn(getDefaultAddress())
                .when(addressRepository)
                .save(any(Address.class));
        doReturn(getDefaultContact())
                .when(contactRepository)
                .save(any(Contact.class));
        doReturn(getDefaultArrivalTime())
                .when(arrivalTimeRepository)
                .save(any(ArrivalTime.class));
        doReturn(getDefaultHotel())
                .when(hotelRepository)
                .save(any(Hotel.class));
        ShortHotelResponse expected = getDefaultShortResponse();
        HotelRequest hotelRequest = getDefaultHotelRequest();
        ShortHotelResponse actual = hotelService.createHotel(hotelRequest);

        assertEquals(expected, actual);
        verify(hotelRepository).existsHotelByContact_Phone(anyString());
        verify(hotelRepository).existsHotelByContact_Email(anyString());
        verify(modelMapper).map(hotelRequest.address(), Address.class);
        verify(modelMapper).map(hotelRequest.contacts(), Contact.class);
        verify(modelMapper).map(hotelRequest.arrivalTime(), ArrivalTime.class);
        verify(addressRepository).save(any(Address.class));
        verify(contactRepository).save(any(Contact.class));
        verify(arrivalTimeRepository).save(any(ArrivalTime.class));
        verify(hotelRepository).save(any(Hotel.class));
    }

    @Test
    void createHotel_whenDataIsNotUnique_shouldReturnValidationExceptionResponse() {
        doReturn(true)
                .when(hotelRepository)
                .existsHotelByContact_Phone(anyString());
        doReturn(true)
                .when(hotelRepository)
                .existsHotelByContact_Email(anyString());

        assertThrows(
                AlreadyExistsException.class,
                () -> hotelService.createHotel(getDefaultHotelRequest())
        );

        verify(hotelRepository).existsHotelByContact_Phone(anyString());
        verify(hotelRepository).existsHotelByContact_Email(anyString());


    }

    @Test
    void addAmenitiesToHotel_whenHotelExistsAndAmenitiesNotExist() {
        doReturn(Optional.of(getDefaultHotel()))
                .when(hotelRepository)
                .findById(DEFAULT_ID);
        doReturn(Optional.empty())
                .when(amenityRepository)
                .findByName(anyString());
        Amenity amenity = Amenity.builder()
                .id(DEFAULT_ID)
                .name("Meeting rooms")
                .build();
        doReturn(amenity)
                .when(amenityRepository)
                .save(any(Amenity.class));
        Hotel hotel = getDefaultHotel();
        hotel.setAmenities(List.of(amenity));
        doReturn(hotel)
                .when(hotelRepository)
                .save(any(Hotel.class));

        HotelResponse response = hotelService.addAmenitiesToHotel(DEFAULT_ID, getAmenitiesList());

        assertEquals(getAmenitiesList(), response.amenities());
        verify(hotelRepository).findById(DEFAULT_ID);
        verify(amenityRepository).findByName(anyString());
        verify(amenityRepository).save(any(Amenity.class));
        verify(hotelRepository).save(any(Hotel.class));
    }

    @Test
    void addAmenitiesToHotel_whenHotelNotFound_shouldThrowNotFoundException() {
        doReturn(Optional.empty())
                .when(hotelRepository)
                .findById(DEFAULT_ID);
        assertThrows(
                NotFoundException.class,
                () -> hotelService.addAmenitiesToHotel(DEFAULT_ID, getAmenitiesList())
        );
        verify(hotelRepository).findById(DEFAULT_ID);
    }

    @Test
    void histogram_whenParamIsValid_shouldReturnHistogramResponse() {
        HistogramResponse histogramResponse = HistogramResponse.builder()
                .param(DEFAULT_CITY)
                .amount(1)
                .build();
        doReturn(List.of(histogramResponse))
                .when(hotelRepository)
                .getCityHistogram();
        Map<String, Long> expected = Map.of(DEFAULT_CITY, 1L);

        Map<String, Long> actual = hotelService.histogram("city");

        assertEquals(expected, actual);
        verify(hotelRepository).getCityHistogram();
    }

    @Test
    void histogram_whenParamIsNotValid_shouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> {
            hotelService.histogram("invalid_param");
        });

    }
}