CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE,
                       email VARCHAR(50) UNIQUE,
                       password VARCHAR(100)
);
