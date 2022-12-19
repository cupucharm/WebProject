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
