CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    role varchar(50)
);

