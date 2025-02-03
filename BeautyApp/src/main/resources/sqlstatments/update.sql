USE booksy;

UPDATE Users SET email = 'john.smith@newmail.com' WHERE id = 20;
UPDATE Employees SET rating = 4.7 WHERE id = 12;
UPDATE Procedures SET description = 'Advanced massage therapy' WHERE id = 27;
UPDATE Appointments SET status = 'rescheduled' WHERE id = 6;
UPDATE Businesses SET address = 'ul. Nowowiejska 45' WHERE id = 8;
UPDATE Payments SET status = 'failed' WHERE id = 3;
UPDATE Notifications SET message = 'Your appointment has been updated.' WHERE id = 1;
UPDATE Reviews SET rating = 4.2 WHERE id = 1;
UPDATE Users SET phone = +48741456456 WHERE id = 15;
UPDATE Procedures SET name = "haircut with wash" WHERE id = 1;