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
</head>
<body>

	<div class="main">
		<div class="logDiv">
			<a class="logo" href="../page/MainPage.jsp">TALK</a>
		</div>
		<h1 id="login_name">${login_id}님</h1>

		<div id="pwdCheckDiv" style="display: none">
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

		<div class="memberInfoDiv" id="memberInfoDiv">
			<table class="memberInfo">
				<tbody>
					<tr>
						<td><input type="hidden" id="user_id"
							value=${memberInfo.user_id } readonly="readonly"></td>
							<td><input type="hidden" id="user_pwd"
							value=${memberInfo.user_pwd } readonly="readonly"></td>
</tr>
					<tr>
						<td></td>
						<td><div id="pwd_msg"></div></td>
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

		<!-- <div id="update_msg"></div> -->
		
				<div>
			<button id="myBoardList" class="mylistBtn" onclick="location.href='/webProjectSJ/Board/myBoardList'">내가 쓴 글 목록</button>
		</div>
		

		<div class="changeMemberDiv" id="changeMemberDiv">
			<button id="updateMemberBtn" class="changeMemberBtn" 
			onclick="location.href='/webProjectSJ/Member/memberEdit?user_id=${memberInfo.user_id}'">회원 정보
				수정</button>
				
				<button id="deleteMemberBtn" class="changeMemberBtn" 
			onclick="location.href='/webProjectSJ/page/MemberDeletePage.jsp'">회원 탈퇴</button>
			<button id="cancel" class="changeMemberBtn" onclick="location.href='/webProjectSJ/page/MainPage.jsp'">취소</button>
	
		</div>
		
		

	</div>

</body>
</html>