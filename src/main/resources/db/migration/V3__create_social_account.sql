CREATE TABLE social_account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    provider VARCHAR(50),
    provider_user_id VARCHAR(225) NOT NUll,
    user_id BIGINT,

    CONSTRAINT fk_social_account_user
        FOREIGN KEY (user_id)
        REFERENCES user(id)
        ON DELETE CASCADE,
    
    CONSTRAINT uk_user_provider
        UNIQUE (user_id, provider),
    
    CONSTRAINT uk_provider_provider_user_id
        UNIQUE (provider, provider_user_id)
);