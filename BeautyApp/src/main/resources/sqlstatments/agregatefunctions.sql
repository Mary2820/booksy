USE booksy;

SELECT role_id, COUNT(*) AS user_count FROM Users GROUP BY role_id;
SELECT business_id, AVG(rating) AS avg_rating FROM Employees GROUP BY business_id;
SELECT client_id, COUNT(*) AS appointment_count FROM Appointments GROUP BY client_id;
SELECT category_id, COUNT(*) AS procedure_count FROM Procedures GROUP BY category_id;
SELECT appointment_id, AVG(rating) AS avg_review FROM Reviews GROUP BY appointment_id;
SELECT appointment_id, SUM(amount) AS total_revenue FROM Payments GROUP BY appointment_id;
SELECT business_id, COUNT(*) AS total_employees FROM Employees GROUP BY business_id;

SELECT role_id, COUNT(*) AS user_count FROM Users GROUP BY role_id HAVING COUNT(*) > 5;
SELECT business_id, AVG(rating) AS avg_rating FROM Employees GROUP BY business_id HAVING AVG(rating) > 4.0;
SELECT appointment_id, SUM(amount) AS total_revenue FROM Payments GROUP BY appointment_id HAVING SUM(amount) > 100;
SELECT client_id, COUNT(*) AS appointment_count FROM Appointments GROUP BY client_id HAVING COUNT(*) > 3;
SELECT category_id, COUNT(*) AS procedure_count FROM Procedures GROUP BY category_id HAVING COUNT(*) > 2;
SELECT business_id, COUNT(*) AS total_employees FROM Employees GROUP BY business_id HAVING COUNT(*) > 1;
SELECT appointment_id, AVG(rating) AS avg_review FROM Reviews GROUP BY appointment_id HAVING AVG(rating) >= 4.5;