-- DB springBootDB
create database springBootDB;
use springBootDB;

-- user_member
create table user_member(
  no int auto_increment primary key,
  id varchar(20) not null,
  pw varchar(100) not null,
  mail varchar(50) not null,
  phone varchar(50) not null,
  reg_date datetime default now(),
  mod_date datetime default now()
);

