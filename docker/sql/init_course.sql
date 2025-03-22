DROP TABLE IF EXISTS course;

CREATE TABLE course (
                        course_id VARCHAR(255) PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description VARCHAR(1000),
                        instructor VARCHAR(255) NOT NULL,
                        course_code VARCHAR(255) UNIQUE,
                        status VARCHAR(50) NOT NULL,
                        max_enrollments INT,
                        created_at TIMESTAMP,
                        updated_at TIMESTAMP
);