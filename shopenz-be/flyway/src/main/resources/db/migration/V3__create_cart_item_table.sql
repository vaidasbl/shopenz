CREATE TABLE cart_item (
    item_id  BIGSERIAL PRIMARY KEY,
    quantity BIGSERIAL,
    cart_id  BIGSERIAL NOT NULL
);