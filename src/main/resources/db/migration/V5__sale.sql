-- V5: satış
-- vehicle_id UNIQUE: bir araç yalnızca bir kez satılabilir (veritabanı seviyesinde güvence).

CREATE TABLE sale (
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vehicle_id          BIGINT         NOT NULL UNIQUE REFERENCES vehicle (id),
    customer_id         BIGINT         NOT NULL REFERENCES customer (id),
    sale_price_amount   NUMERIC(12, 2) NOT NULL CHECK (sale_price_amount > 0),
    sale_price_currency VARCHAR(3)     NOT NULL,
    payment_type        VARCHAR(20)    NOT NULL CHECK (payment_type IN ('CASH', 'LOAN', 'TRADE_IN')),
    sold_by             VARCHAR(64)    NOT NULL,
    sold_at             TIMESTAMPTZ    NOT NULL DEFAULT now()
);
