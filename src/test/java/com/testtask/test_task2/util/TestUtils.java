package com.testtask.test_task2.util;


import com.testtask.test_task2.dto.AddressDto;
import com.testtask.test_task2.dto.ArrivalTimeDto;
import com.testtask.test_task2.dto.ContactDto;
import com.testtask.test_task2.dto.request.HotelRequest;
import com.testtask.test_task2.dto.response.HotelResponse;
import com.testtask.test_task2.dto.response.ShortHotelResponse;
import com.testtask.test_task2.entity.Address;
import com.testtask.test_task2.entity.ArrivalTime;
import com.testtask.test_task2.entity.Contact;
import com.testtask.test_task2.entity.Hotel;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

import java.util.List;


@UtilityClass
public class TestUtils {
    public final long DEFAULT_ID = 1L;
    public final String DEFAULT_NAME = "DoubleTree by Hilton Minsk";
    public final String DEFAULT_PHONE = "+375 17 309-80-00";
    public final String DEFAULT_BRAND = "Hilton";
    public final String DEFAULT_CITY = "Minsk";
    public final String NEW_CITY = "Fanipol";
    public final String DEFAULT_COUNTY = "Belarus";
    public final String DEFAULT_EMAIL = "maria@example.com";
    private final ModelMapper modelMapper = new ModelMapper();

    public List<String> getAmenitiesList() {
        return List.of("Meeting rooms");
    }

    public AddressDto getDefaultAddressDto() {
        return AddressDto.builder()
                .city(DEFAULT_CITY)
                .houseNumber(9)
                .postCode("220004")
                .street("Pobediteley Avenue")
                .county(DEFAULT_COUNTY)
                .build();

    }

    public Address getDefaultAddress() {
        return modelMapper.map(getDefaultAddressDto(), Address.class);
    }

    public ContactDto getDefaultContactDto() {
        return ContactDto.builder()
                .email(DEFAULT_EMAIL)
                .phone(DEFAULT_PHONE)
                .build();
    }

    public ArrivalTimeDto getDefaultArrivalTimeDto() {
        return ArrivalTimeDto.builder()
                .checkIn("14:00")
                .checkOut("12:00")
                .build();
    }

    public ShortHotelResponse getDefaultShortResponse() {
        return ShortHotelResponse.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .phone(DEFAULT_PHONE)
                .address("9 Pobediteley Avenue, Minsk, 220004, Belarus")
                .build();
    }

    public List<ShortHotelResponse> getHotelsResponseList() {
        return List.of(getDefaultShortResponse());
    }

    public ArrivalTime getDefaultArrivalTime() {
        return modelMapper.map(getDefaultArrivalTimeDto(), ArrivalTime.class);
    }

    public Contact getDefaultContact() {
        return modelMapper.map(getDefaultContactDto(), Contact.class);
    }

    public Hotel getDefaultHotel() {
        return Hotel.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .amenities(List.of())
                .brand(DEFAULT_BRAND)
                .arrivalTime(getDefaultArrivalTime())
                .address(getDefaultAddress())
                .contact(getDefaultContact())
                .build();
    }

    public List<Hotel> getHotels() {
        return List.of(getDefaultHotel());
    }

    public HotelResponse getDefaultHotelResponse() {
        return HotelResponse.builder()
                .id(DEFAULT_ID)
                .name(DEFAULT_NAME)
                .amenities(List.of())
                .contacts(getDefaultContactDto())
                .brand(DEFAULT_BRAND)
                .address(getDefaultAddressDto())
                .arrivalTime(getDefaultArrivalTimeDto())
                .build();
    }

    public HotelRequest getDefaultHotelRequest() {
        return HotelRequest.builder()
                .brand(DEFAULT_BRAND)
                .name(DEFAULT_NAME)
                .address(getDefaultAddressDto())
                .arrivalTime(getDefaultArrivalTimeDto())
                .contacts(getDefaultContactDto())
                .build();
    }

    public HotelRequest getInvalidHotelRequest() {
        return HotelRequest.builder()
                .brand(null)
                .name(null)
                .address(getDefaultAddressDto())
                .arrivalTime(getDefaultArrivalTimeDto())
                .contacts(getDefaultContactDto())
                .build();
    }
}
