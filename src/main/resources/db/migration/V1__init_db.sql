-- Create table for lecturers
CREATE TABLE IF NOT EXISTS lecturers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    department VARCHAR(255)
);

-- Create table for rooms
CREATE TABLE IF NOT EXISTS rooms (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    floor INTEGER NOT NULL,
    building VARCHAR(255) NOT NULL
);

-- Create table for room_schedules
CREATE TABLE IF NOT EXISTS room_schedules (
    id SERIAL PRIMARY KEY,
    room_id INTEGER NOT NULL,
    lecturer_id INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (room_id) REFERENCES rooms (id) ON DELETE CASCADE,
    FOREIGN KEY (lecturer_id) REFERENCES lecturers (id) ON DELETE CASCADE
);

-- Create table for room_schedule_weekdays
CREATE TABLE IF NOT EXISTS room_schedule_weekdays (
    id SERIAL PRIMARY KEY,
    room_schedule_id INTEGER NOT NULL,
    weekday VARCHAR(20) NOT NULL,
    FOREIGN KEY (room_schedule_id) REFERENCES room_schedules (id) ON DELETE CASCADE
);

-- Insert mock data into lecturers
INSERT INTO lecturers (first_name, last_name, email, password, department)
VALUES
('John', 'Doe', 'john.doe@example.com', 'hashed_password_1', 'Computer Science'),
('Jane', 'Smith', 'jane.smith@example.com', 'hashed_password_2', 'Mathematics')
ON CONFLICT (email) DO NOTHING;

-- Insert mock data into rooms
INSERT INTO rooms (name, number, floor, building)
VALUES
('Room A', 101, 1, 'Main Building'),
('Room B', 102, 1, 'Main Building')
ON CONFLICT (id) DO NOTHING;

-- Insert mock data into room_schedules
INSERT INTO room_schedules (room_id, lecturer_id, start_date, end_date, start_time, end_time)
VALUES
(1, 1, '2023-10-01', '2023-10-31', '10:00:00', '12:00:00'),
(2, 2, '2023-10-01', '2023-10-31', '14:00:00', '16:00:00')
ON CONFLICT (id) DO NOTHING;

-- Insert mock data into room_schedule_weekdays
INSERT INTO room_schedule_weekdays (room_schedule_id, weekday)
VALUES
(1, 'MONDAY'),
(1, 'WEDNESDAY'),
(2, 'TUESDAY'),
(2, 'THURSDAY')
ON CONFLICT (id) DO NOTHING;