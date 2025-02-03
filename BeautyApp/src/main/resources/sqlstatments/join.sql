USE booksy;

SELECT * FROM Users
LEFT JOIN User_Roles ON Users.role_id = User_Roles.id
LEFT JOIN Employees ON Users.id = Employees.user_id
LEFT JOIN Businesses ON Employees.business_id = Businesses.id
LEFT JOIN Appointments ON Users.id = Appointments.client_id
LEFT JOIN Procedures ON Appointments.procedure_id = Procedures.id
LEFT JOIN Categories ON Procedures.category_id = Categories.id
LEFT JOIN Offerings ON Employees.id = Offerings.employee_id
LEFT JOIN Payments ON Appointments.id = Payments.appointment_id
LEFT JOIN Reviews ON Appointments.id = Reviews.appointment_id
LEFT JOIN Notifications ON Appointments.id = Notifications.appointment_id;

SELECT * FROM Users LEFT JOIN User_Roles ON Users.role_id = User_Roles.id;
SELECT * FROM Businesses LEFT JOIN Employees ON Businesses.id = Employees.business_id WHERE Businesses.id = 4;
SELECT * FROM Appointments LEFT JOIN Procedures ON Appointments.procedure_id = Procedures.id;
SELECT * FROM Payments LEFT JOIN Appointments ON Payments.appointment_id = Appointments.id;
SELECT * FROM Reviews LEFT JOIN Users ON Reviews.appointment_id = Users.id WHERE Reviews.rating > 4.00;

SELECT * FROM Appointments RIGHT JOIN Payments ON Appointments.id = Payments.appointment_id;
SELECT * FROM Employees RIGHT JOIN Businesses ON Employees.business_id = Businesses.id;
SELECT * FROM Offerings RIGHT JOIN Procedures ON Offerings.procedure_id = Procedures.id;
SELECT * FROM Notifications RIGHT JOIN Users ON Notifications.appointment_id = Users.id;
SELECT * FROM Categories RIGHT JOIN Procedures ON Categories.id = Procedures.category_id;

SELECT * FROM Users INNER JOIN Employees ON Users.id = Employees.user_id;
SELECT * FROM Businesses INNER JOIN Employees ON Businesses.id = Employees.business_id;
SELECT * FROM Appointments INNER JOIN Procedures ON Appointments.procedure_id = Procedures.id;
SELECT * FROM Payments INNER JOIN Appointments ON Payments.appointment_id = Appointments.id;
SELECT * FROM Reviews INNER JOIN Users ON Reviews.appointment_id = Users.id;

SELECT * FROM Users FULL OUTER JOIN User_Roles ON Users.role_id = User_Roles.id;
SELECT * FROM Employees FULL OUTER JOIN Businesses ON Employees.business_id = Businesses.id;
SELECT * FROM Appointments FULL OUTER JOIN Procedures ON Appointments.procedure_id = Procedures.id;
SELECT * FROM Payments FULL OUTER JOIN Appointments ON Payments.appointment_id = Appointments.id;
SELECT * FROM Reviews FULL OUTER JOIN Users ON Reviews.appointment_id = Users.id;