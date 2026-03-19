CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uid VARCHAR(225) NOT NUll UNIQUE,
    name VARCHAR(255),
    password VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(255),

    CONSTRAINT uk_user_email UNIQUE (email)
);