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

--tb_board 답변 게시판, 좋아요 등 컬럼 추가
alter table tb_board add column bparentNo int;
alter table tb_board add column blikecount int default 0;
alter table tb_board add column bdislikecount int default 0;
update tb_board set bparentNo = bno;

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