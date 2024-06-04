package com.testtask.test_task2.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageUtil {
    public final String VALIDATION_FAILED_MESSAGE = "Invalid request";
    public final String NOT_FOUND_WITH_ID_MESSAGE = "Hotel with id %s was not found";
    public final String ILLEGAL_STATE_MESSAGE = "Unexpected value: %s";
    public final String NAME_NOT_EMPTY_MESSAGE = "Name is mandatory";
    public final String BRAND_NOT_EMPTY_MESSAGE = "Brand is mandatory";
    public final String ADDRESS_NOT_EMPTY_MESSAGE = "Address is mandatory";
    public final String CONTACTS_NOT_EMPTY_MESSAGE = "Contacts are mandatory";
    public final String TIME_NOT_EMPTY_MESSAGE = "Arrival time is mandatory";
    public final String CHECK_IN_NOT_EMPTY_MESSAGE = "Check-in time is mandatory";
    public final String TIME_FORMAT_INVALID_MESSAGE = "Invalid time format. Please use format hh:mm";
    public final String PHONE_NOT_EMPTY_MESSAGE = "Phone is mandatory";
    public final String PHONE_FORMAT_INVALID_MESSAGE = "Invalid phone format. Please use format +375 xx xxx-xx-xx";
    public final String EMAIL_NOT_EMPTY_MESSAGE = "Email is mandatory";
    public final String EMAIL_INVALID_MESSAGE = "Invalid email address";
    public final String STREET_NOT_EMPTY_MESSAGE = "Street is mandatory";
    public final String CITY_NOT_EMPTY_MESSAGE = "City is mandatory";
    public final String COUNTRY_NOT_EMPTY_MESSAGE = "Country is mandatory";
    public final String POST_CODE_NOT_EMPTY_MESSAGE = "Post code is mandatory";
    public final String MIN_VALUE_MESSAGE = "Min value is 1";


}
