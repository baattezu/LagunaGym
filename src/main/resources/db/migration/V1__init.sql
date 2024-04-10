
create table users (
    id serial,
    username varchar(255),
    f_name varchar(255),
    l_name varchar(255),
    password varchar(255),
    primary key (id)
                    );
create table roles (
    id serial primary key ,
    role_title varchar(77)
);
create table users_roles(
    user_id int not null ,
    role_id int not null,
    constraint fk_user_id_01 foreign key (user_id) references users (id),
    constraint fk_role_id_02 foreign key (role_id) references roles (id)
);
insert into users (username,password)
values ('fall','$2a$12$6ASimk2Wy9BGsZ5JeuEcZegdYzFmbsNgoAuGUIHaYSfcwlyOa5Es.')
     , ('down','$2a$12$6ASimk2Wy9BGsZ5JeuEcZegdYzFmbsNgoAuGUIHaYSfcwlyOa5Es.');

insert into roles (role_title) values ('ADMIN'), ('USER');
insert into users_roles (user_id, role_id)
values (1,1),(1,2),(2,2);

create table subscription (
    id serial,
    title varchar(255),
    price float(53) not null,
    primary key (id));

-- Inserting values into the person table

-- Inserting values into the subscription table
INSERT INTO subscription (price, id, title) VALUES
(10.99, 1, 'Basic Subscription'),
(19.99, 2, 'Premium Subscription'),
(15.99, 3, 'Standard Subscription');