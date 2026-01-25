-- DB CALENDAR
create database DB_CALENDAR;
use DB_CALENDAR;

-- user_member
create table user_member(
  no int auto_increment primary key,
  id varchar(20) not null,
  pw varchar(100) not null,
  mail varchar(50) not null,
  phone varchar(50) not null,
  authority_no tinyint default 1,
  reg_date datetime default now(),
  mod_date datetime default now()
);

-- user_authority
create table user_authority(
no int auto_increment primary key,
role_name varchar(20) not null
);
insert into user_authority(role_name) value("PRE_USER");
insert into user_authority(role_name) value("USER");