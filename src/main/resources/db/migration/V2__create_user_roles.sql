CREATE TABLE user_roles (
    user_id BIGINT NOT NUll,
    role VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(id)
);