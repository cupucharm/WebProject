<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : MyPage</title>
<link rel="stylesheet" href="<c:url value='/page/css/MyPage.css'/>">
<script type="text/javascript" src="../js/memberDelete.js"></script>
</head>
<body>

	<div class="main">
		<div class="logDiv">
			<a class="logo" href="/webProjectSJ">TALK</a>
		</div>
		<h1 id="login_name">${login_id}님</h1>

		<div id="pwdCheckDiv">
			<table class="pwdCheckTable">
				<tr>
					<td></td>
					<td><input type="text" id="before_pwd" placeholder="이전 비밀번호"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="button" id="before_update_pwdBtn"
						value="비밀번호 확인"></td>
				</tr>
			</table>
		</div>
		<div id="pwd_msg"></div>

		<div class="memberInfoDiv" id="memberInfoDiv">


			<div class="changeMemberDiv" id="changeMemberDiv">
				<button id="deleteMemberBtn" class="changeMemberBtn">회원 탈퇴</button>
				<button id="cancel" class="changeMemberBtn"
					onclick="location.href='/webProjectSJ/Member/myPage.do'">취소</button>
			</div>

		</div>
	</div>
</body>
</html>