CREATE TABLE address
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    house_number INT,
    street       VARCHAR(255),
    city         VARCHAR(255),
    country      VARCHAR(255),
    post_code    VARCHAR(10)
);