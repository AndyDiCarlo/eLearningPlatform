INSERT INTO course (course_id, title, description, instructor_id, course_code, status, max_users, created_at, updated_at)
VALUES
    ('c1', 'Introduction to Java', 'Learn the basics of Java programming.', 'instr1', 'JAVA101', 'DRAFT', 100, NOW(), NOW()),
    ('c2', 'Advanced Spring Boot', 'Deep dive into Spring Boot framework.', 'instr2', 'SPRING202', 'DRAFT', 50, NOW(), NOW());