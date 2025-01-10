CREATE DATABASE HOPE;
USE HOPE;

-- Création des tables
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL
);

CREATE TABLE tool (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    domain VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    added_by_id BIGINT,
    FOREIGN KEY (added_by_id) REFERENCES user(id) ON DELETE SET NULL
);

CREATE TABLE feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comment TEXT NOT NULL,
    tool_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (tool_id) REFERENCES tool(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

INSERT INTO user (first_name, last_name, email, password, role) VALUES
('Admin', 'User', 'admin@hope.com', 'password', 'ADMIN'),
('John', 'Doe', 'john.doe@example.com', 'password', 'TEACHER'),
('Jane', 'Smith', 'jane.smith@example.com', 'password', 'STUDENT');

INSERT INTO tool (name, domain, description, added_by_id) VALUES
('Spring Boot', 'Backend', 'Framework Java pour construire des applications', 1),
('React', 'Frontend', 'Librairie JS pour créer des interfaces dynamiques', 1),
('MariaDB', 'Database', 'Système de gestion de base de données open source', 2);

INSERT INTO feedback (comment, tool_id, user_id) VALUES
('Super outil pour le backend', 1, 2),
('Très utile pour construire des interfaces modernes', 2, 3),
('Facile à utiliser pour gérer les données', 3, 2);
