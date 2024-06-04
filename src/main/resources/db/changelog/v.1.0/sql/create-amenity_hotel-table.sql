CREATE TABLE amenity_hotel
(
    hotel_id   INT,
    amenity_id INT,
    PRIMARY KEY (hotel_id, amenity_id),
    FOREIGN KEY (hotel_id) REFERENCES hotel (id),
    FOREIGN KEY (amenity_id) REFERENCES amenity (id)
);