-- Insert guest groups
INSERT INTO guest_group (id, name) VALUES
(1, 'Familia García'),
(2, 'Familia López'),
(3, 'Amigos del Novio'),
(4, 'Amigos de la Novia'),
(5, 'Familia Martínez'),
(6, 'Compañeros de Trabajo');

-- Reset sequence for guest_group
SELECT setval('guest_group_id_seq', (SELECT MAX(id) FROM guest_group));

-- Insert guests for Familia García
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, bus, group_id) VALUES
('Juan', 'García', NULL, false, 'Sin gluten', 'Me gustaría escuchar música latina', NULL, 1),
('María', 'García', NULL, false, NULL, NULL, NULL, 1),
('Pedro', 'García', NULL, true, 'Alergia a frutos secos', NULL, NULL, 1),
('Lucía', 'García', NULL, true, NULL, NULL, NULL, 1);

-- Insert guests for Familia López
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, bus, group_id) VALUES
('Carlos', 'López', true, false, NULL, 'Me encanta el rock clásico', '20:00h', 2),
('Ana', 'López', true, false, 'Vegetariana', NULL, '20:00h', 2),
('Miguel', 'López', true, true, NULL, NULL, '20:00h', 2);

-- Insert guests for Amigos del Novio
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, bus, group_id) VALUES
('Alberto', 'Fernández', false, false, NULL, 'Música actual por favor', NULL, 3),
('Sofía', 'Rodríguez', NULL, false, 'Intolerancia a la lactosa', NULL, NULL, 3),
('Javier', 'Gómez', true, false, NULL, 'Me gustaría bailar salsa', '00:00h', 3),
('Elena', 'Pérez', true, false, 'Vegana', NULL, '00:00h', 3);

-- Insert guests for Amigos de la Novia
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, bus, group_id) VALUES
('Laura', 'Martín', true, false, NULL, NULL, 'No', 4),
('Diego', 'Sánchez', NULL, false, NULL, 'Algo de reggaeton', NULL, 4),
('Carmen', 'Jiménez', false, false, 'Celiaca', NULL, NULL, 4);

-- Insert guests for Familia Martínez
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, bus, group_id) VALUES
('Roberto', 'Martínez', true, false, NULL, NULL, 'No lo sé', 5),
('Isabel', 'Martínez', true, false, 'Sin mariscos', NULL, 'No lo sé', 5),
('Pablo', 'Martínez', true, true, NULL, NULL, 'No lo sé', 5),
('Daniela', 'Martínez', true, true, 'Sin lactosa', NULL, 'No lo sé', 5);

-- Insert guests for Compañeros de Trabajo
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, bus, group_id) VALUES
('Marcos', 'Torres', NULL, false, NULL, 'Rock de los 80', NULL, 6),
('Patricia', 'Ruiz', NULL, false, 'Vegetariana', NULL, NULL, 6),
('Andrés', 'Moreno', NULL, false, NULL, NULL, NULL, 6),
('Natalia', 'Serrano', NULL, false, 'Sin gluten', 'Música disco', NULL, 6);

-- Reset sequence for guest
SELECT setval('guest_id_seq', (SELECT MAX(id) FROM guest));