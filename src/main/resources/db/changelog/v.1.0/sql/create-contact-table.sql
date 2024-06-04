CREATE TABLE contact
(
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    phone VARCHAR(17) unique,
    email VARCHAR(40) unique
);