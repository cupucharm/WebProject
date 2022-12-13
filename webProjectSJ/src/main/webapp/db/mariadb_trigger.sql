--Log Trigger
/* 트리거 - member */
/* 회원 탈퇴 트리거 */
drop trigger delete_member_Trigger;
create trigger delete_member_Trigger
after
DELETE on tb_member
for each ROW 
begin 
	insert into tb_member_log(user_id, content)
	values(old.user_id, '회원 탈퇴'); 
end

/*회원 가입 트리거*/
drop trigger insert_member_Trigger;
create trigger insert_member_Trigger
after
INSERT on tb_member
for each ROW 
begin 
	insert into tb_member_log(user_id, content)
	values(new.user_id, '회원 가입'); 
end

/*회원 정보 변경*/
drop trigger update_member_Trigger;
create trigger update_member_Trigger
after
UPDATE on tb_member
for each ROW 
begin 
	insert into tb_member_log(user_id, content)
	values(old.user_id, '회원 정보 수정');
end


/* 트리거 - board */
/* 게시글 삭제 트리거 */
drop trigger delete_board_Trigger;
create trigger delete_board_Trigger
after
DELETE on tb_board
for each ROW 
begin 
	insert into tb_board_log(bwriter, bno, content)
	values(old.bwriter, old.bno, '게시글 삭제'); 
end

/* 게시글 등록 트리거 */
drop trigger insert_board_Trigger;
create trigger insert_board_Trigger
after
INSERT on tb_board
for each ROW 
begin 
	insert into tb_board_log(bwriter, bno, content)
	values(new.bwriter, new.bno, '게시글 삭제'); 
end

/* 게시글 수정 트리거 */
drop trigger update_board_Trigger;
create trigger update_board_Trigger
after
UPDATE on tb_board
for each ROW 
begin 
	insert into tb_board_log(bwriter, bno, content)
	values(old.bwriter, old.bno, '게시글 수정');
end
