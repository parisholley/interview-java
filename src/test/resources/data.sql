CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    created_at TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);


INSERT INTO users (username, email, full_name, created_at, is_active) VALUES
('foobar', 'foobar@example.com', 'Foobar User', NOW(), true);
