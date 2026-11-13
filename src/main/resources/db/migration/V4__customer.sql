-- V4: müşteri
-- owner_id: müşteriden sorumlu satış danışmanının kimliği (8. adımda Keycloak'taki "sub" değeri olacak)

CREATE TABLE customer (
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    full_name  VARCHAR(150) NOT NULL,
    phone      VARCHAR(30),
    email      VARCHAR(150),
    owner_id   VARCHAR(64)  NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);
