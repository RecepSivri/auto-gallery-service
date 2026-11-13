-- V2: marka altındaki modeller (örn. Toyota -> Corolla)
-- (brand_id, name) birlikte benzersiz: aynı markada iki "Corolla" olamaz.

CREATE TABLE car_model (
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    brand_id BIGINT       NOT NULL REFERENCES brand (id),
    name     VARCHAR(100) NOT NULL,
    CONSTRAINT uq_car_model_brand_name UNIQUE (brand_id, name)
);
