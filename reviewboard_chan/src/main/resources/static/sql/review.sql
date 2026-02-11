create table review(
num int auto_increment primary key,
title varchar(50) not null,
content varchar(300) not null,
writer varchar(20) not null,
star int not null,
regDate datetime default now(),
readcount int not null default(0)
);