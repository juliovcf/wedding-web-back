-- Create guest_group table
CREATE TABLE guest_group (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create guest table
CREATE TABLE guest (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    confirmed_attendance BOOLEAN,
    kid BOOLEAN NOT NULL DEFAULT FALSE,
    dietary_restrictions VARCHAR(500),
    suggests VARCHAR(1000),
    bus VARCHAR(255),
    group_id BIGINT NOT NULL,
    CONSTRAINT fk_guest_group FOREIGN KEY (group_id) REFERENCES guest_group(id)
);