CREATE TABLE item (
    id    BIGSERIAL PRIMARY KEY,
    stock BIGSERIAL,
    name  VARCHAR(255) NOT NULL
);