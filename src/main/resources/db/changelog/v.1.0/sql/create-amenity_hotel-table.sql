CREATE TABLE amenity_hotel
(
    hotel_id   BIGINT,
    amenity_id BIGINT,
    PRIMARY KEY (hotel_id, amenity_id),
    FOREIGN KEY (hotel_id) REFERENCES hotel (id),
    FOREIGN KEY (amenity_id) REFERENCES amenity (id)
);