create sequence person_seq start with 1 increment by 50;
create sequence subscription_seq start with 1 increment by 50;
create table person (
    id bigint not null,
    email varchar(255),
    f_name varchar(255),
    l_name varchar(255),
    password varchar(255),
    primary key (id)
                    );
create table subscription (
    price float(53) not null,
    id bigint not null,
    title varchar(255),
    primary key (id));
-- Inserting values into the person table
INSERT INTO person (id, email, f_name, l_name, password) VALUES
(1, 'john@example.com', 'John', 'Doe', 'password123'),
(2, 'jane@example.com', 'Jane', 'Smith', 'qwerty789'),
(3, 'bob@example.com', 'Bob', 'Johnson', 'pass123');

-- Inserting values into the subscription table
INSERT INTO subscription (price, id, title) VALUES
(10.99, 1, 'Basic Subscription'),
(19.99, 2, 'Premium Subscription'),
(15.99, 3, 'Standard Subscription');