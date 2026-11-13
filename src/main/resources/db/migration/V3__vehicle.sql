-- V3: araç ve araç özellikleri
-- version: optimistic locking için (7. adımda kullanılacak)
-- Bilerek eksik bırakılanlar: vehicle.car_model_id üzerinde index YOK (5. adımda EXPLAIN ile göreceğiz).

CREATE TABLE vehicle (
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    car_model_id        BIGINT         NOT NULL REFERENCES car_model (id),
    vin                 VARCHAR(17)    NOT NULL UNIQUE,
    plate               VARCHAR(12),
    production_year     INTEGER        NOT NULL CHECK (production_year BETWEEN 1990 AND 2100),
    mileage_km          INTEGER        NOT NULL CHECK (mileage_km >= 0),
    fuel_type           VARCHAR(20)    NOT NULL CHECK (fuel_type IN ('PETROL', 'DIESEL', 'HYBRID', 'ELECTRIC', 'LPG')),
    transmission        VARCHAR(20)    NOT NULL CHECK (transmission IN ('MANUAL', 'AUTOMATIC')),
    color               VARCHAR(30),
    list_price_amount   NUMERIC(12, 2) NOT NULL CHECK (list_price_amount > 0),
    list_price_currency VARCHAR(3)     NOT NULL DEFAULT 'TRY',
    status              VARCHAR(20)    NOT NULL DEFAULT 'IN_STOCK' CHECK (status IN ('IN_STOCK', 'RESERVED', 'SOLD')),
    version             BIGINT         NOT NULL DEFAULT 0,
    created_at          TIMESTAMPTZ    NOT NULL DEFAULT now()
);

CREATE TABLE vehicle_feature (
    vehicle_id BIGINT      NOT NULL REFERENCES vehicle (id) ON DELETE CASCADE,
    feature    VARCHAR(60) NOT NULL,
    PRIMARY KEY (vehicle_id, feature)
);
