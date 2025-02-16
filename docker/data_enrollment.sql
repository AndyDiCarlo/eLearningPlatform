-- Insert sample users
INSERT INTO users (id, username, email, first_name, last_name, updated_at)
VALUES (1, 'john_doe', 'john@example.com', 'John', 'Doe', NOW());

INSERT INTO users (id, username, email, first_name, last_name, updated_at)
VALUES (2, 'jane_doe', 'jane@example.com', 'Jane', 'Doe', NOW());

-- Insert sample courses
INSERT INTO courses (id, title, description, instructor, updated_at, max_enrollments, start_date, end_date)
VALUES (101, 'Introduction to Java', 'A basic Java course', 'Dr. Smith', NOW(), 50, '2025-03-01', '2025-06-01');

INSERT INTO courses (id, title, description, instructor, updated_at, max_enrollments, start_date, end_date)
VALUES (102, 'Spring Boot Basics', 'Learn Spring Boot from scratch', 'Dr. Johnson', NOW(), 40, '2025-04-01', '2025-07-01');

-- Insert sample enrollments
INSERT INTO enrollments (user_id, course_id, enrollment_date, status)
VALUES (1, 101, NOW(), 'active');

INSERT INTO enrollments (user_id, course_id, enrollment_date, status)
VALUES (2, 102, NOW(), 'active');