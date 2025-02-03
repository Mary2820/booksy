USE booksy;

INSERT INTO Users (id, first_name, last_name, email, phone, password, role_id) VALUES
(20, 'John', 'Smith', 'jSmith@gmail.com', '+48123456780', 'xYzA12BcD', 1),
(21, 'Jane', 'Johnson', 'jJohnson@gmail.com', '+48123456781', 'aBcD34EfG', 1),
(22, 'Emily', 'Williams', 'eWilliams@gmail.com', '+48123456782', 'qRsT56UvW', 1),
(23, 'Michael', 'Brown', 'mBrown@gmail.com', '+48123456783', 'mNop78QrS', 1),
(24, 'Sarah', 'Davis', 'sDavis@gmail.com', '+48123456784', 'lKjH90MnB', 1),
(25, 'Chris', 'Miller', 'cMiller@gmail.com', '+48123456785', 'hGfE12XyZ', 2),
(26, 'Anna', 'Wilson', 'aWilson@gmail.com', '+48123456786', 'zXyV34TgH', 2),
(27, 'David', 'Moore', 'dMoore@gmail.com', '+48123456787', 'bNqW56LmO', 2),
(28, 'Laura', 'Taylor', 'lTaylor@gmail.com', '+48123456788', 'dEfR78GhY', 2),
(29, 'Tom', 'Anderson', 'tAnderson@gmail.com', '+48123456789', 'kLmJ90NwQ', 2);

INSERT INTO Businesses (id, name, address, city, postcode) VALUES
(8, 'Elite Salon', 'ul. Marszałkowska 123', 'Warszawa', '00-001'),
(9, 'Barber Bros', 'ul. Długa 456', 'Kraków', '30-002'),
(10, 'Spa Retreat', 'ul. Krótka 789', 'Gdańsk', '80-003'),
(11, 'Nail Haven', 'ul. Nowa 321', 'Wrocław', '50-004'),
(12, 'Wellness Center', 'ul. Zielona 654', 'Poznań', '60-005');

INSERT INTO Employees (id, user_id, description, rating, business_id) VALUES
(12, 25, 'Senior Hair Stylist', 4.5, 8),
(13, 26, 'Junior Barber', 4.0, 9),
(14, 27, 'Massage Therapist', 4.8, 10),
(15, 28, 'Nail Technician', 4.6, 11),
(16, 29, 'Receptionist', 4.3, 12);

INSERT INTO Procedures (id, category_id, name, description, duration) VALUES
(27, 11, 'Massage Therapy', 'Massage provided by certified therapists', 60),
(28, 12, 'Spa Treatment', 'Relaxing spa services', 120),
(29, 13, 'Weight Management', 'Personalized weight management sessions', 90),
(30, 14, 'Fitness Training', 'Training with certified fitness instructors', 45),
(31, 15, 'Physiotherapy', 'Sessions with licensed physiotherapists', 60);

INSERT INTO Offerings (employee_id, procedure_id, price) VALUES
(12, 27, 30.00),
(13, 28, 20.00),
(14, 29, 120.00),
(15, 30, 60.00),
(16, 31, 45.00);

INSERT INTO Appointments (id, client_id, procedure_id, employee_id, status, created_at, date, day_of_week, start_time, end_time) VALUES
(6, 20, 27, 12, 'confirmed', '2025-02-01 10:00:00', '2025-02-05', 'Monday', '10:00:00', '11:00:00'),
(7, 21, 28, 13, 'pending', '2025-02-01 11:00:00', '2025-02-06', 'Tuesday', '11:00:00', '13:00:00'),
(8, 22, 29, 14, 'confirmed', '2025-02-01 12:00:00', '2025-02-07', 'Wednesday', '12:00:00', '13:30:00'),
(9, 23, 30, 15, 'cancelled', '2025-02-01 13:00:00', '2025-02-08', 'Thursday', '13:00:00', '13:45:00'),
(10, 24, 31, 16, 'confirmed', '2025-02-01 14:00:00', '2025-02-09', 'Friday', '14:00:00', '15:00:00');

INSERT INTO Reviews (id, appointment_id, rating, comment, created_at) VALUES
(1, 6, 5.0, 'Excellent service!', '2025-02-01 15:00:00'),
(2, 7, 4.5, 'Very good!', '2025-02-01 16:00:00'),
(3, 8, 5.0, 'Outstanding experience!', '2025-02-01 17:00:00'),
(4, 9, 3.0, 'Average, not great.', '2025-02-01 18:00:00'),
(5, 10, 4.8, 'Fantastic job!', '2025-02-01 19:00:00');

INSERT INTO Payments (id, appointment_id, amount, payment_date, status) VALUES
(3, 6, 30.00, '2025-02-05', 'completed'),
(4, 7, 20.00, '2025-02-06', 'pending'),
(5, 8, 120.00, '2025-02-07', 'completed'),
(6, 9, 60.00, '2025-02-08', 'completed'),
(7, 10, 45.00, '2025-02-09', 'completed');

INSERT INTO Notifications (id, appointment_id, message, status, created_at) VALUES
(1, 6, 'Your appointment is confirmed.', 'sent', '2025-02-01 08:00:00'),
(6, 7, 'Your appointment is pending.', 'sent', '2025-02-01 09:00:00'),
(3, 8, 'Your appointment is confirmed.', 'sent', '2025-02-01 10:00:00'),
(4, 9, 'Your appointment was cancelled.', 'sent', '2025-02-01 11:00:00'),
(5, 10, 'Your appointment is confirmed.', 'sent', '2025-02-01 12:00:00');