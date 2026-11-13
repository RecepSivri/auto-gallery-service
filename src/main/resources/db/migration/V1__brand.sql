-- V1: marka tablosu
-- Not: Bu dosya bir kez çalıştıktan sonra ASLA değiştirilmez. Değişiklik için V2__... yaz.

CREATE TABLE brand (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
