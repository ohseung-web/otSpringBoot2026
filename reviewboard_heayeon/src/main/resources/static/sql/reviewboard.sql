CREATE TABLE reviewboard(
    num int auto_increment primary key,
    title varchar(100) not null,
    content varchar(500) not null,
    writer varchar(10) not null,
    rating int not null,
    reg_date datetime default now(),
    viewCount int
);
