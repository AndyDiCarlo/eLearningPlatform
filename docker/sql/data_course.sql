INSERT INTO course (course_id, title, description, instructor, course_code, status, max_enrollments, created_at, updated_at)
VALUES
    ('c1', 'Introduction to Java', 'Learn the basics of Java programming.', 'Dr. Smith', 'JAVA101', 'DRAFT', 100, NOW(), NOW()),
    ('c2', 'Advanced Spring Boot', 'Deep dive into Spring Boot framework.', 'Dr. Johnson', 'SPRING202', 'DRAFT', 50, NOW(), NOW()),
    ('c3', 'Object Oriented Development', 'Advanced OOP Modeling Topics', 'Dr. Prince', 'OOP403', 'DRAFT', 20, NOW(), NOW());