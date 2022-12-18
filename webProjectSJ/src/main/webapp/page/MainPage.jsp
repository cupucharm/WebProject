<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : where to go</title>
<link rel="stylesheet" href="<c:url value='/page/css/MainPage.css'/>">
</head>
<body>
	<div class="main">
		<div class="fulllogo">
			<a class="logo" href="">TALK</a>
		</div>
		<div class="check">

			<a class="choose" id="chat" href="ChatListPage.jsp">채팅</a> <a
				class="choose" id="board" href="/webProjectSJ/Board/boardList">게시판</a>

			<c:choose>
				<c:when test="${admin eq 'admin'}">
					<a class="choose" class="status" href="Member/adminPage.do">회원관리</a>
					<a class="choose" class="status" href="<c:url value='/Member/logout.do'/>">로그아웃</a>
				</c:when>
				<c:when test="${not empty login_id}">
					<a class="choose" class="status" href="Member/myPage.do">마이페이지</a>
					<a class="choose" class="status" href="Member/logout.do">로그아웃</a>
				</c:when>
				<c:otherwise>
					<a class="choose" class="status" href="<c:url value='/Member/loginForm.do'/>">로그인</a>
					<a class="choose" class="status" href="<c:url value='/Member/registerForm.do'/>">회원가입</a>
				</c:otherwise>
			</c:choose>

		</div>
	</div>
</body>
</html>