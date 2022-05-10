insert into role (id, name) values(1, 'ADMIN');
insert into role (id, name) values(2, 'USER');

insert into user (id, email_adress, password) values(1, 'admin@loftshop.pl', '$2a$10$KYBbrw7k4AhzSkBwZN89i.me1UyrJ2b/KQhJp4MQVs9l8je5J8NAS');
insert into user (id, email_adress, password) values(2, 'user@loftshop.pl', '$2a$10$KYBbrw7k4AhzSkBwZN89i.me1UyrJ2b/KQhJp4MQVs9l8je5J8NAS');

insert into user_role (user_id, role_id) values(1,1);
insert into user_role (user_id, role_id) values(2,2);
