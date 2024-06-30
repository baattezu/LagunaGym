CREATE TABLE user_info (
                           id SERIAL PRIMARY KEY,
                           first_name VARCHAR(50),
                           last_name VARCHAR(50),
                           email VARCHAR(50) UNIQUE,
                           phone_number VARCHAR(20),
                           birth_date date,
                           membership_id integer
);
