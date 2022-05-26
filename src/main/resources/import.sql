insert into role (id, name) values(1, 'ADMIN');
insert into role (id, name) values(2, 'USER');

insert into user (id, email_address, password) values(1, 'admin@loftshop.pl', '$2a$10$KYBbrw7k4AhzSkBwZN89i.me1UyrJ2b/KQhJp4MQVs9l8je5J8NAS');
insert into user (id, email_address, password) values(2, 'user@loftshop.pl', '$2a$10$KYBbrw7k4AhzSkBwZN89i.me1UyrJ2b/KQhJp4MQVs9l8je5J8NAS');

insert into user_role (user_id, role_id) values(1,1);
insert into user_role (user_id, role_id) values(2,2);

insert into category (name, id) values ('Komody / Konsole', 1);
insert into category (name, id) values ('Stoły', 2);
insert into category (name, id, parent_id) values ('Stoły rozkładane', 3, 2);
insert into category (name, id, parent_id) values ('Stoliki', 4, 2);
insert into category (name, id, parent_id) values ('Szafki RTV', 5, 2);
insert into category (name, id) values ('Regały', 2);

insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Konsola', 'Konsola z półką to idealne rozwiązanie dla tych, którzy cenią sobie wysoką jakość oraz niezwykłą trwałość, a także świetne wykonanie,idealnie sprawdzi się w każdym salonie.', '/images/konsola-z-polka.jpg', 1, 1450.00, '', 'HOME');
insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Konsola z półką', 'Konsola z półką to idealne rozwiązanie dla tych, którzy cenią sobie wysoką jakość oraz niezwykłą trwałość, a także świetne wykonanie,idealnie sprawdzi się w każdym salonie.', '/images/konsola-z-polka2.jpg', 1, 1750.00, '', 'HOME');
insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Stół rozkładany I-40×40', 'Stół rozkładany I-40×40 posiada blat z litego drewna dębowego o grubości 40mm.', '/images/stol-rozkladany.jpg', 3, 3600.00, '', 'HOME');
insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Stół rozkładany I-80×40', 'Stół rozkładany I – 80×40 posiada blat z litego drewna dębowego o grubości 40mm.', '/images/stol-rozkladany1.jpg', 3, 3650.00, '', 'HOME');
insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Stolik kawowy', 'Stolik kawowy  to idealne rozwiązanie dla tych, którzy cenią sobie wysoką jakość oraz niezwykłą trwałość, a także świetne wykonanie. Stolik ten idealnie sprawdzi się w każdym salonie.', '/images/stolik.jpg', 4, 2150.00, '', 'HOME');
insert into product (title, description, thumbnail, category_id, price, author, product_type) values ('Szafka RTV', 'Prezentowana szafka RTV została wykonana z litego jesionu wykończonego naturalnym olejowoskiem.', '/images/szafka-rtv.jpg', 5, 2150.00, '', 'HOME');


#INSERT INTO shopping_cart (id, user_id) VALUES (1, 1);
#INSERT INTO shopping_cart_item (id, quantity, product_id, shopping_cart_id) VALUES (1, 2, 1, 1);

