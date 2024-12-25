CREATE TABLE cart_item (
    id       BIGSERIAL PRIMARY KEY,
    item_id  BIGSERIAL,
    name     VARCHAR(255) NOT NULL,
    quantity BIGSERIAL,
    user_id  BIGSERIAL    NOT NULL
);