--liquibase formatted sql

--changeset adm:1
CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  filename VARCHAR(255),
  filepath VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--changeset adm:2
CREATE INDEX idx_users_email ON users(email);
