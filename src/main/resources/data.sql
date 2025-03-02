-- Limpiar tablas existentes (en caso de que existan datos previos)
TRUNCATE TABLE guest, guest_group RESTART IDENTITY CASCADE;

-- Insertar grupos de invitados
INSERT INTO guest_group (id, name) VALUES
(1, 'Familia García'),
(2, 'Familia López'),
(3, 'Amigos del Novio'),
(4, 'Amigos de la Novia'),
(5, 'Familia Martínez'),
(6, 'Compañeros de Trabajo');

-- Resetear la secuencia para los IDs de guest_group
SELECT setval('guest_group_id_seq', (SELECT MAX(id) FROM guest_group));

-- Insertar invitados para Familia García
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, group_id) VALUES
('Juan', 'García', NULL, false, 'Sin gluten', 'Me gustaría escuchar música latina', 1),
('María', 'García', NULL, false, NULL, NULL, 1),
('Pedro', 'García', NULL, true, 'Alergia a frutos secos', NULL, 1),
('Lucía', 'García', NULL, true, NULL, NULL, 1);

-- Insertar invitados para Familia López
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, group_id) VALUES
('Carlos', 'López', true, false, NULL, 'Me encanta el rock clásico', 2),
('Ana', 'López', true, false, 'Vegetariana', NULL, 2),
('Miguel', 'López', true, true, NULL, NULL, 2);

-- Insertar invitados para Amigos del Novio
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, group_id) VALUES
('Alberto', 'Fernández', false, false, NULL, 'Música actual por favor', 3),
('Sofía', 'Rodríguez', NULL, false, 'Intolerancia a la lactosa', NULL, 3),
('Javier', 'Gómez', true, false, NULL, 'Me gustaría bailar salsa', 3),
('Elena', 'Pérez', true, false, 'Vegana', NULL, 3);

-- Insertar invitados para Amigos de la Novia
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, group_id) VALUES
('Laura', 'Martín', true, false, NULL, NULL, 4),
('Diego', 'Sánchez', NULL, false, NULL, 'Algo de reggaeton', 4),
('Carmen', 'Jiménez', false, false, 'Celiaca', NULL, 4);

-- Insertar invitados para Familia Martínez
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, group_id) VALUES
('Roberto', 'Martínez', true, false, NULL, NULL, 5),
('Isabel', 'Martínez', true, false, 'Sin mariscos', NULL, 5),
('Pablo', 'Martínez', true, true, NULL, NULL, 5),
('Daniela', 'Martínez', true, true, 'Sin lactosa', NULL, 5);

-- Insertar invitados para Compañeros de Trabajo
INSERT INTO guest (name, surname, confirmed_attendance, kid, dietary_restrictions, suggests, group_id) VALUES
('Marcos', 'Torres', NULL, false, NULL, 'Rock de los 80', 6),
('Patricia', 'Ruiz', NULL, false, 'Vegetariana', NULL, 6),
('Andrés', 'Moreno', NULL, false, NULL, NULL, 6),
('Natalia', 'Serrano', NULL, false, 'Sin gluten', 'Música disco', 6);

-- Resetear la secuencia para los IDs de guest
SELECT setval('guest_id_seq', (SELECT MAX(id) FROM guest));