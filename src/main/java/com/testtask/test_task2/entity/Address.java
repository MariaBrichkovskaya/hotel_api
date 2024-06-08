package com.testtask.test_task2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "address")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(name = "house_number")
    int houseNumber;

    @Column(name = "street")
    String street;

    @Column(name = "city")
    String city;

    @Column(name = "county")
    String county;

    @Column(name = "post_code")
    String postCode;

    @Override
    public String toString() {
        return houseNumber + " " + street + ", " + city + ", " + postCode + ", " + county;
    }
}
