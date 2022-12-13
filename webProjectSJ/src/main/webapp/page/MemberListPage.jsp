<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 회원 관리</title>
<link rel="stylesheet"
	href="<c:url value='/page/css/MemberListPage.css'/>">
	<script type="text/javascript" src="../js/memberList.js"></script> 
</head>
<body>

	<c:if test="${not empty message}">
		<script type='text/javascript'>alert('${message}');</script>
	</c:if>

	<div class="main">
		<a class="logo" href="../page/MainPage.jsp">TALK</a>

		<div id="search">

			<input type="text" id="memberSearch" readonly="readonly"
				value="아이디로 회원 찾기"> <input type="text" id="searchInput"
				name="searchInput">
			<button id="searchBtn">검색</button>
		</div>

		<div class="board">
			<table class="boardTable">
				<thead class="boardThead">
					<tr>
						<td>번호</td>
						<td>아이디</td>
						<td>이름</td>
						<td>비밀번호</td>
						<td>휴대폰</td>
						<td>이메일</td>
						<td>성별</td>
						<td>생년월일</td>
						<td>활성여부</td>
						<td>회원탈퇴</td>
					</tr>
				</thead>
				<tbody class="boardTbody">
				<tbody>
					<c:set var="i" value="${boardPageVO.currentPage*10-10+1}"></c:set>

					<c:forEach var="memberList" items="${listMembers}">
						<tr>
							<td>${i}</td>
							<td>${memberList.user_id}</td>
							<td>${memberList.user_name}</td>
							<td>${memberList.user_pwd}</td>
							<td>${memberList.user_phone}</td>
							<td>${memberList.user_email}</td>
							<td>${memberList.user_sex}</td>
							<td>${memberList.user_birth}</td>
							<td><button id="memberCondition"
									onclick="location.href='/webProjectSJ/Member/memberCondition?user_id=${memberList.user_id}'">${memberList.user_condition}</button></td>
							<td><c:if test="${memberList.user_condition eq '비활성화' }">
									<button id="deleteMember"
										onclick="location.href='/webProjectSJ/Member/memberAdminDelete?user_id=${memberList.user_id}'">회원탈퇴</button>
								</c:if></td>
							<c:set var="i">${i+1}</c:set>
						</tr>
					</c:forEach>
				</tbody>


			</table>
			<div class="paginationDiv">
				<ul class="pagination">
					<c:if test="${ boardPageVO.prev }">
						<li><a class="bt prev"
							href="/webProjectSJ/Board/AdminPage?pageNum=${ boardPageVO.startPage - 1 }"><</a></li>
					</c:if>
					<c:forEach begin="${boardPageVO.startPage}"
						end="${boardPageVO.endPage}" var="num">
						<li><a <c:if test="${num eq boardPageVO.currentPage }">style="background-color:#6667AB; color:white;"</c:if> href="/webProjectSJ/Member/AdminPage?pageNum=${num}">${num}</a></li>
					</c:forEach>
					<c:if test="${ boardPageVO.next }">
						<li><a class="bt next"
							href="/webProjectSJ/Member/AdminPage?pageNum=${ boardPageVO.endPage + 1  }">></a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>