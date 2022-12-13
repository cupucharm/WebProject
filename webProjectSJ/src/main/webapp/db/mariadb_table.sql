--DB Table
--tb_member
create table tb_member (
	user_id varchar(50) primary key,
	user_name varchar(50) not null,
	user_pwd varchar(50) not null,
	user_phone varchar(50) not null,
	user_email varchar(100) not null,
	user_sex varchar(2) not null,
	user_birth varchar(20) not null,
	user_condition varchar(10) default ’활성화’
)

--tb_board
create table tb_board (
	bno int(200) AUTO_INCREMENT primary key,
	btitle varchar(100) not null,
	bwriter varchar(50) not null,
	bdate timestamp default now(),
	bhit int(200) default 0,
	bcontents varchar(300) not null,
	bcategory varchar(50) not null
)

--tb_member_log
drop table tb_member_log;
create table tb_member_log (
	logno int(200) AUTO_INCREMENT primary key,	
	user_id varchar(50) not null,
	content varchar(20) not null,
	log_time timestamp default now()
)

--tb_member_delete
drop table tb_member_delete;
create table tb_member_delete (
	delno int(200) AUTO_INCREMENT primary key,	
	user_id varchar(50) not null,
	user_name varchar(50) not null,
	user_pwd varchar(50) not null,
	user_phone varchar(50) not null,
	user_email varchar(100) not null,
	user_sex varchar(2) not null,
	user_birth varchar(20) not null,
	del_time timestamp default now()
)

--tb_board_delete
drop table tb_board_delete;
create table tb_board_delete (
	delno int(200) AUTO_INCREMENT primary key,	
	bno int(200) not null,
	btitle varchar(200) not null,
	bwriter varchar(50) not null,
	bdate timestamp not null,
	bcontents varchar(300) not null,
	bcategory varchar(50) not null,
	del_time timestamp default now()
)


--Dummy data - PROCEDURE
--tb_member   - 300개

DELIMITER $$
DROP PROCEDURE IF EXISTS memberInsert_f$$
 
CREATE PROCEDURE memberInsert_f()
BEGIN
    DECLARE i INT DEFAULT 1;
        
    WHILE i <= 150 DO
        INSERT INTO tb_member(user_id, user_name, user_pwd, user_phone, user_email, user_sex, user_birth)
          VALUES(concat('user',i), concat('길동',i), concat('pwd',i), '01012341234', 'rlfehd@naver.com', 'F', '1998-07-29');
        set i = i + 1;
    END WHILE;
END$$
DELIMITER $$

CALL memberInsert_f;


DELIMITER $$
DROP PROCEDURE IF EXISTS memberInsert_s$$
 
CREATE PROCEDURE memberInsert_s()
BEGIN
    DECLARE i INT DEFAULT 150;
        
    WHILE i <= 300 DO
        INSERT INTO tb_member(user_id, user_name, user_pwd, user_phone, user_email, user_sex, user_birth)
          VALUES(concat('user',i), concat('순신',i), concat('pwd',i), '01011112222', 'tnstls@naver.com', 'M', '1995-03-13');
        set i = i + 1;
    END WHILE;
END$$
DELIMITER $$

CALL memberInsert_s;



--tb_board   - 300개
DELIMITER $$
DROP PROCEDURE IF EXISTS boardInsert_f$$
 
CREATE PROCEDURE boardInsert_f()
BEGIN
    DECLARE i INT DEFAULT 1;
        
    WHILE i <= 100 DO
        INSERT INTO tb_board(btitle, bwriter, bhit, bcontents , bcategory)
          VALUES(concat('공지',i), concat('user',i), rand() + i, concat('오늘 점심 제순식당 어떰?',rand() + i), '공지사항');
        set i = i + 1;
    END WHILE;
END$$
DELIMITER $$

CALL boardInsert_f;


DELIMITER $$
DROP PROCEDURE IF EXISTS boardInsert_s$$
 
CREATE PROCEDURE boardInsert_s()
BEGIN
    DECLARE i INT DEFAULT 101;
        
    WHILE i <= 200 DO
        INSERT INTO tb_board(btitle, bwriter, bhit, bcontents , bcategory)
          VALUES(concat('얏호',i), concat('user',i), rand() + i, concat('맥북 샀음 ^^',rand() + i), '일반게시판');
        set i = i + 1;
    END WHILE;
END$$
DELIMITER $$

CALL boardInsert_s;


DELIMITER $$
DROP PROCEDURE IF EXISTS boardInsert_t$$
 
CREATE PROCEDURE boardInsert_t()
BEGIN
    DECLARE i INT DEFAULT 201;
        
    WHILE i <= 300 DO
        INSERT INTO tb_board(btitle, bwriter, bhit, bcontents , bcategory)
          VALUES(concat('아이폰 14 프로 어떤가요?',i), concat('user',i), rand() + i, concat('아이폰 14 프로 무슨 색이 예쁜가요?',rand() + i), 'Q&A');
        set i = i + 1;
    END WHILE;
END$$
DELIMITER $$

CALL boardInsert_t;


--탈퇴 테이블 트리거
--회원 탈퇴
drop trigger trg_delete_member;
create trigger trg_delete_member
after
DELETE on tb_member
for each ROW 
begin 
	insert into tb_member_delete(user_id, user_name, user_pwd, user_phone, user_email, user_sex, user_birth)
	values(old.user_id, old.user_name, old.user_pwd, old.user_phone, old.user_email, old.user_sex, old.user_birth); 
end


--게시판 삭제
drop trigger trg_delete_board;
create trigger trg_delete_board
after
DELETE on tb_board
for each ROW 
begin 
	insert into tb_board_delete(bno, btitle, bwriter, bdate, bcontents, bcategory)
	values(old.bno, old.btitle, old.bwriter, old.bdate, old.bcontents, old.bcategory); 
end
