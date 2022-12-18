<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 비밀번호 찾기</title>
<link rel="stylesheet" href="<c:url value='/page/css/SearchPwPage.css'/>">
<script type="text/javascript" src="../js/search.js"></script>
</head>
<body>
	<div class="main">
		<a class="logo" href="<c:url value='/'/>">TALK</a>
<hr>
		<div class="searchPwDiv">
			<!-- <table class="searchPw">
				<tbody>
				<tr>
						<th>아이디</th>
						<td><input type="text" class="searchPwCondition"
							name="user_id" id="user_id"></td>
					</tr>
					<tr>
						<th>이름</th>
						<td><input type="text" class="searchPwCondition"
							name="user_name" id="user_name"></td>
					</tr>
					<tr>
						<th>전화번호</th>
						<td><input type="number" class="searchPwCondition"
							name="user_phone" id="user_phone"></td>
					</tr>
				</tbody>
			</table> -->
			<input name="user_id" class="searchPwCondition" type="text"
					id="user_id" placeholder="아이디" required="required"
					autocomplete="none">
			<input name="user_name" class="searchPwCondition" type="text"
					id="user_name" placeholder="이름" required="required"
					autocomplete="none"> <input name="user_phone"
					class="searchPwCondition" type="number" id="user_phone"
					placeholder="전화번호" required="required" autocomplete="none">
			
		</div>


		<div class="searchPwBtnDiv">
			<div>
				<input type="submit" name="searchPwBtn" id="searchPwBtn"
					class="searchPwBtn" value="비밀번호 찾기">
			</div>
		</div>


		<div id="checkPw"></div>

		<div class="changePage">
			<button id="searchPw" class="changePageBtn"
				onclick="location.href='searchIdForm.do'">아이디 찾기</button>
			<button id="login" class="changePageBtn"
				onclick="location.href='loginForm.do'">로그인</button>
			<button id="searchPw" class="changePageBtn"
				onclick="location.href='registerForm.do'">회원가입</button>
		</div>
	</div>
</body>
</html>