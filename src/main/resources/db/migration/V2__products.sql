create sequence product_seq start with 1 increment by 50;
create table product(
     id bigint not null,
     title varchar(255),
     description varchar(255),
     price double precision,
     primary key (id)
);
INSERT INTO product (id, title, description, price) VALUES
 (1, 'Milk', 'Calcium+', 650),
 (2, 'Bread', 'Calories First', 300),
 (3, 'Sandwich', 'Yummy', 550);