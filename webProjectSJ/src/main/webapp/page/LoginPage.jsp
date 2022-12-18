<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : LoginPage</title>
<%-- ${loginFail} --%>
<link rel="stylesheet" href="<c:url value='/page/css/LoginPage.css'/>">
</head>
<body>
	<div class="main">
	${loginFail}
		<a class="logo" href="<c:url value='/'/>">TALK</a>
		<div class="container">
			<div class="frmLogin">
				<form name="frmLogin" method="post" action="../Member/login.do" encType="UTF-8">
					<input type="text" placeholder="ID" id="user_id" name="user_id" class="account" autofocus="autofocus" required="required"
						autocomplete="none">
					<input type="password" placeholder="Password" id="user_pwd" name="user_pwd"
						class="account" autofocus="autofocus" required="required">
					<input type="submit" value="Login" id="loginbtn" class="loginbtn">
				</form>
			</div>
			<div class="bottomDiv">
				<button id="register" class="accountPlus" onclick="location.href='../Member/registerForm.do'">회원가입</button>
				<button id="searchId" class="accountPlus" onclick="location.href='../Member/searchIdForm.do'">아이디찾기</button>
				<button id="searchPw" class="accountPlus" onclick="location.href='../Member/searchPwForm.do'">비밀번호찾기</button>
			</div>
		</div>
	</div>
</body>
</html>