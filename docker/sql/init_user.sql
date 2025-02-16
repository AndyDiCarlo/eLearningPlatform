DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id VARCHAR(255) PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255),
                       comment TEXT
);