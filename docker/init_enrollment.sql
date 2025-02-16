-- Drop tables if they exist (order matters because of foreign keys)
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS courses;

-- Create the "users" table
CREATE TABLE users (
                       id BIGINT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       updated_at TIMESTAMP
);

-- Create the "courses" table
CREATE TABLE courses (
                         id BIGINT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         description VARCHAR(2000),
                         instructor VARCHAR(255) NOT NULL,
                         updated_at TIMESTAMP,
                         max_enrollments INTEGER NOT NULL,
                         start_date DATE,
                         end_date DATE
);

-- Create the "enrollments" table
CREATE TABLE enrollments (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             course_id BIGINT NOT NULL,
                             enrollment_date TIMESTAMP,
                             status VARCHAR(50),
                             CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id),
                             CONSTRAINT fk_course FOREIGN KEY(course_id) REFERENCES courses(id)
);