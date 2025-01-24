INSERT INTO users (login, password, first_name, last_name, contacts) VALUES
('alice', '$2a$10$JJ.ig/ZjSs.CY9QATRQjWurYAYGyuyncSjt5vIKBdWJOTqV3mhfRi', 'Alice', 'Smith', 'alice.smith@example.com'),
('bob', '$2a$10$kj4oYdNpp0fVDdjNjsyilOh2q00am5xoRrsmJdLUvP4x7xhPTr/2G', 'Bob', 'Johnson', 'bob.johnson@example.com'),
('charlie', '$2a$10$fxl0b4VN4CDEFoKilg4qruk9Axw6v5ZqbB0KYVuYzHMYg7XCS1jx.', 'Charlie', 'Brown', 'charlie.brown@example.com'),
('diana', '$2a$10$H6h2AY8xtjcRgo7jL0PGVuCbrD6bXYec/hATMcVdqZVoDT.O0eLli', 'Diana', 'Miller', 'diana.miller@example.com'),
('eve', '$2a$10$5gU62PUUx0TJnNhOwGLyouInbQmfc.iS7Hn/YL7TvHvvyn74JKroG', 'Eve', 'Davis', 'eve.davis@example.com'),
('frank', '$2a$10$GI9aE5WCd4p78jPTdZGM8OK0dyTil7RRFAPQsiy7lHZH0CTSgZKnW', 'Frank', 'Taylor', 'frank.taylor@example.com');

INSERT INTO sitter (user_id, location, is_vet, title, description, attendance_in, attendance_out, with_dog, with_cat, with_other) VALUES
(1, 'New York', false, 'I love taking care of dogs and cats.', 'I love taking care of dogs and cats.', true, true, true, true, false),
(2, 'Los Angeles', true, 'Veterinarian with experience in caring for all kinds of animals.', 'Veterinarian with experience in caring for all kinds of animals.', true, false, true, true, true),
(4, 'New York', true, 'Veterinary student, can care for any pets.', 'Veterinary student, can care for any pets.', true, true, true, true, true);

INSERT INTO customer (user_id, location, begin_date, end_date, title, description, attendance_in, attendance_out, with_dog, with_cat, with_other) VALUES
(3, 'New York', '2024-12-01', '2024-12-05', 'Looking for someone to care for my dog.', 'Looking for someone to care for my dog.', true, false, true, false, false),
(6, 'Los Angeles', '2024-12-10', '2024-12-12', 'Need someone to feed and play with my cat.', 'Need someone to feed and play with my cat.', true, false, false, true, false),
(5, 'New York', '2024-12-15', '2024-12-20', 'Rabbit care needed.', 'Rabbit care needed.', false, true, false, false, true);

--INSERT INTO orders (sitter_id, customer_id, status) VALUES (1, 1, 'PENDING');
