<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : MyPage</title>
<link rel="stylesheet" href="../page/css/MyPage.css">
<script type="text/javascript" src="../js/memberEdit.js"></script>
</head>
<body>

	<div class="main">
		<div class="logDiv">
			<a class="logo" href="../page/MainPage.jsp">TALK</a>
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
					<td><input type="button" id="before_update_pwdBtn" value="비밀번호 확인"></td>
				</tr>
			</table>
		</div>
		
		
		<div id="update_msg"></div>

		<div class="memberInfoDiv" id="memberInfoDiv">
			<table class="memberInfo">
				<tbody>
					<tr>
						<td><input type="hidden" id="user_id"
							value=${memberInfo.user_id } readonly="readonly"></td>

						<td></td>
					</tr>


					<tr>
						<th id="user_pwd_th">비밀번호</th>
						<td><input type="text" id="user_pwd" placeholder="*****"
							readonly="readonly"></td>
					</tr>
					<tr>
						<th id="user_name_th">이름</th>
						<td><input type="text" id="user_name"
							value=${memberInfo.user_name } readonly="readonly"></td>
					</tr>
					<tr>
						<th id="user_phone_th">휴대폰</th>
						<td><input type="text" id="user_phone"
							value=${memberInfo.user_phone } readonly="readonly"></td>
					</tr>
					<tr>
						<th id="user_email_th">이메일</th>
						<td><input type="text" id="user_email"
							value=${memberInfo.user_email } readonly="readonly"></td>
					</tr>
					<tr>
						<th id="user_sex_th">성별</th>
						<td><input type="text" id="user_sex"
							value=${memberInfo.user_sex } readonly="readonly"></td>
					</tr>
					<tr>
						<th id="user_birth_th">생년월일</th>
						<td><input type="text" id="user_birth"
							value=${memberInfo.user_birth } readonly="readonly"></td>
					</tr>
				</tbody>
			</table>
		</div>


		<div class="changeMemberDiv" id="changeMemberDiv">
			<input type="button" id="updateMemberBtn" class="changeMemberBtn" value="회원 정보 수정"> 
			<input type="button" id="deleteMemberBtn" class="changeMemberBtn" value="취소" onclick="history.back()">
		</div>


	</div>

</body>
</html>