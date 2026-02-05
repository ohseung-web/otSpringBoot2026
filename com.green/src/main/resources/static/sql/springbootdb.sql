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

-- board
-- create table board(
-- num int auto_increment primary key,
-- writer varchar(20),
-- subject varchar(30),
-- writerPw varchar(20),
-- reg_date datetime default now(),
-- readcount int default 0,
-- content varchar(1000)
-- );

drop table board;

create table board(
num int auto_increment primary key,
writer varchar(20),
subject varchar(30),
writerPw varchar(20),
reg_date datetime default now(),
readcount int default 0,
content varchar(1000),
id varchar(20)
);

INSERT INTO board (writer, subject, writerPw, reg_date, readcount, content, id) VALUES
('kim',   '스프링 공부중입니다', '1234', NOW(), 3, '스프링부트 너무 재밌어요', 'kim1111'),
('오티',  '겨울이야',           '1234', NOW(), 5, '날씨가 너무 춥네요', 'kjb1030'),
('관리자','공지사항입니다',      '1234', NOW(), 10, '필독 공지입니다', 'admin1234'),
('young', 'MyBatis 질문',        '1234', NOW(), 2, 'mapper가 헷갈려요', 'young111'),
('kim',   '게시판 테스트',       '1234', NOW(), 1, '더미 데이터입니다', 'kim2222'),
('yoon',  '자바 어렵다',         '1234', NOW(), 7, '그래도 해야죠', 'y5555'),
('kim',   '스프링 MVC',          '1234', NOW(), 4, '구조 이해중', 'kim1111'),
('오티',  '눈 오는 날',          '1234', NOW(), 6, '눈이 많이 옵니다', 'kjb1030'),
('관리자','서버 점검 안내',      '1234', NOW(), 12,'오늘 밤 점검', 'admin1234'),
('young', '타임리프 질문',       '1234', NOW(), 2, 'th:each 어렵네요', 'young111'),
('kim',   '게시판 CRUD',         '1234', NOW(), 3, 'CRUD 연습중', 'kim2222'),
('yoon',  'DB 연결 성공',        '1234', NOW(), 8, '드디어 성공', 'y5555'),
('kim',   '세션이 뭐예요?',      '1234', NOW(), 5, '로그인 질문', 'kim1111'),
('오티',  '주말 계획',           '1234', NOW(), 1, '집에서 코딩', 'kjb1030'),
('관리자','공지사항2',           '1234', NOW(), 9, '두번째 공지', 'admin1234'),
('young', '에러 해결',           '1234', NOW(), 4, 'Null 에러 해결함', 'young111'),
('kim',   '페이징 구현',         '1234', NOW(), 6, 'limit 사용', 'kim2222'),
('yoon',  'CSS 수정',            '1234', NOW(), 2, '테이블 정렬', 'y5555'),
('kim',   '로그인 기능',         '1234', NOW(), 7, '세션 사용', 'kim1111'),
('관리자','수업 자료 업로드',    '1234', NOW(), 15,'PPT 확인하세요', 'admin1234');