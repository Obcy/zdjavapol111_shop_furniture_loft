CREATE DATABASE db_loft;
use db_loft;

create table currency_rate
(
id bigint not null primary key,
date datetime null,
currency varchar(255),
code varchar(3)
)