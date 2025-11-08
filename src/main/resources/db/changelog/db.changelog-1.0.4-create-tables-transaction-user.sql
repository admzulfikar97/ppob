--liquibase formatted sql

--changeset adm:1
CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    invoice_number VARCHAR(50) NOT NULL,
    service_code VARCHAR(30) NOT NULL,
    service_name VARCHAR(255) NOT NULL,
    transaction_type VARCHAR(50) NOT NULL DEFAULT 'PAYMENT',
    total_amount BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (service_code) REFERENCES service (service_code)
);