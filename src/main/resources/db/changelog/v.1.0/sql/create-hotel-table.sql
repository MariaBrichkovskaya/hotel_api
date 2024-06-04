CREATE TABLE hotel
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255),
    brand       VARCHAR(255),
    description varchar(255),
    address_id  INT unique,
    contact_id INT unique,
    arrival_time_id  INT,
    FOREIGN KEY (arrival_time_id) REFERENCES arrival_time (id),
    FOREIGN KEY (address_id) REFERENCES address (id),
    FOREIGN KEY (contact_id) REFERENCES contact (id)
);