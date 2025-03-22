-- Insert sample user
INSERT INTO users (user_id, username, first_name, last_name, email)
VALUES
    ('u1', 'john.doe', 'John', 'Doe', 'john.doe@example.com');
-- Insert sample courses
INSERT INTO courses (course_id, title, description, instructor, updated_at, max_enrollments, start_date, end_date)
VALUES ('c1', 'Introduction to Java', 'A basic Java course', 'Dr. Smith', NOW(), 50, '2025-03-01', '2025-06-01');

INSERT INTO courses (course_id, title, description, instructor, updated_at, max_enrollments, start_date, end_date)
VALUES ('c2', 'Spring Boot Basics', 'Learn Spring Boot from scratch', 'Dr. Johnson', NOW(), 40, '2025-04-01', '2025-07-01');

-- Insert sample enrollments
INSERT INTO enrollments (user_id, course_id, enrollment_date, status)
VALUES ('u1', 'c1', NOW(), 'active');

INSERT INTO enrollments (user_id, course_id, enrollment_date, status)
VALUES ('u1', 'c2', NOW(), 'active');