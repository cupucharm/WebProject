<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 아이디 찾기</title>
<link rel="stylesheet" href="<c:url value='/page/css/SearchIdPage.css'/>">
<script type="text/javascript" src="../js/search.js"></script>
</head>
<body>
	<div class="main">
		<a class="logo" href="<c:url value='/page/MainPage.jsp'/>">TALK</a>
		<hr>
		<div class="searchIdDiv">
				<input name="user_name" class="searchIdCondition" type="text"
					id="user_name" placeholder="이름" required="required"
					autocomplete="none"> <input name="user_phone"
					class="searchIdCondition" type="number" id="user_phone"
					placeholder="전화번호" required="required" autocomplete="none">
		</div>


		<div class="searchIdBtnDiv">
			<div>
				<input type="submit" name="searchIdBtn" id="searchIdBtn"
					class="searchIdBtn" value="아이디 찾기">
			</div>
		</div>


		<div id="checkId"></div>

		<div class="changePage">
			<button id="searchPw" class="changePageBtn"
				onclick="location.href='SearchPwPage.jsp'">비밀번호 찾기</button>
			<button id="login" class="changePageBtn"
				onclick="location.href='LoginPage.jsp'">로그인</button>
			<button id="searchPw" class="changePageBtn"
				onclick="location.href='RegisterPage.jsp'">회원가입</button>
		</div>
	</div>
</body>
</html>