USE booksy;

ALTER TABLE Users ADD COLUMN birth_date DATE;
ALTER TABLE Employees MODIFY COLUMN rating DECIMAL(3,2);
ALTER TABLE Appointments ADD CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES Users(id);
ALTER TABLE Payments CHANGE COLUMN status payment_status VARCHAR(50);
ALTER TABLE Reviews DROP COLUMN comment;