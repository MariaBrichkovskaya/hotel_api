package com.testtask.test_task2.repository;

import com.testtask.test_task2.dto.response.HistogramResponse;
import com.testtask.test_task2.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("SELECT NEW com.testtask.test_task2.dto.response.HistogramResponse(h.address.city, COUNT(h)) FROM Hotel h GROUP BY h.address.city")
    List<HistogramResponse> getCityHistogram();

    @Query("SELECT NEW com.testtask.test_task2.dto.response.HistogramResponse(h.brand, COUNT(h)) FROM Hotel h GROUP BY h.brand")
    List<HistogramResponse> getBrandyHistogram();

    @Query("SELECT NEW com.testtask.test_task2.dto.response.HistogramResponse(h.address.country, COUNT(h)) FROM Hotel h GROUP BY h.address.country")
    List<HistogramResponse> getCountryHistogram();

    @Query("SELECT NEW com.testtask.test_task2.dto.response.HistogramResponse(a.name, COUNT(h)) FROM Amenity a JOIN a.hotels h GROUP BY a.name")
    List<HistogramResponse> getAmenityHistogram();

    boolean existsHotelByContact_Email(String email);

    boolean existsHotelByContact_Phone(String phone);
}

