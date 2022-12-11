<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 회원가입</title>
<link rel="stylesheet" href="css/RegisterPage.css">
<script type="text/javascript" src="../js/register.js"></script>
</head>
<body>
	<div class="main">
		<form action="../Member/register" method="POST" class="register" onsubmit="return false;">

			<a class="logo" href="MainPage.jsp">TALK</a>

			<div class="textForm">
				<input name="user_id" type="text" id="user_id" placeholder="아이디"
					required="required" autocomplete="none">
			</div>
			<div id="uid_valid_msg"></div>

			<div class="textForm">
				<input name="user_name" type="text" id="user_name" placeholder="이름"
					autofocus="autofocus" required="required" autocomplete="none">
			</div>
			<div class="textForm">
				<input name="user_pwd" id="user_pwd" type="password" class="user_pwd"
					placeholder="비밀번호" required="required">
			</div>
			
			<div class="textForm">
				<input name="pwdConfirm" id="pwdConfirm" type="password" class="user_pwd"
					placeholder="비밀번호 확인" required="required">
			</div>
			<div id="pwd_valid_msg"></div>
			
			<div class="textForm">
				<input name="user_phone" type="number" id="user_phone"
					placeholder="전화번호" required="required">
			</div>

			<div class="textForm">
				<input name="user_email" type="text" id="user_email"
					placeholder="이메일" required="required">
			</div>


			<div class="textForm">
				<input type="radio" name="user_sex" id="user_sex" value="F" /> 여자 <input
					type="radio" name="user_sex" id="user_sex" value="M" /> 남자
			</div>
			<div class="textForm">
				<input name="user_birth" type="date" id="user_birth"
					data-placeholder="생년월일" required="required" aria-required="true">
			</div>


			<input type="submit" id="registerBtn" class="registerBtn" value="회 원 가 입" />
		</form>
	</div>
</body>
</html>