DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       user_id VARCHAR(255) PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255),
                       comment TEXT,
                       phone VARCHAR(255),
                       street_address VARCHAR(255),
                       city VARCHAR(255),
                       state VARCHAR(255),
                       zip VARCHAR(255)
);