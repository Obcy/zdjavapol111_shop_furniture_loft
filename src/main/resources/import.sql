insert into role (id, name) values(1, 'ADMIN');
insert into role (id, name) values(2, 'USER');

insert into user (id, email_address, password) values(1, 'admin@loftshop.pl', '$2a$10$KYBbrw7k4AhzSkBwZN89i.me1UyrJ2b/KQhJp4MQVs9l8je5J8NAS');
insert into user (id, email_address, password) values(2, 'user@loftshop.pl', '$2a$10$KYBbrw7k4AhzSkBwZN89i.me1UyrJ2b/KQhJp4MQVs9l8je5J8NAS');

insert into user_role (user_id, role_id) values(1,1);
insert into user_role (user_id, role_id) values(2,2);

insert into category (name, id) values ('Meble ogrodowe', 1);
insert into category (name, id) values ('Salon', 2);
insert into category (name, id, parent_id) values ('Kuchnia', 3, 1);

insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Fotel', 'Do ogrodu', '', 1, 200.5, '', 'GARDEN_FURNITURE');
insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Stół', 'Do ogrodu', '', 2, 2000.5, '', 'GARDEN_FURNITURE');
insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Huśtawka', 'Do ogrodu', '', 3, 20.5, '', 'GARDEN_FURNITURE');

INSERT INTO shopping_cart (id, user_id) VALUES (1, 1);
INSERT INTO shopping_cart_item (id, quantity, total_item_price, product_id, shopping_cart_id) VALUES (1, 2, 401.00, 1, 1);

