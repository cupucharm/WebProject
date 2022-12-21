<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 회원 관리</title>
<link rel="stylesheet" href="<c:url value='/css/MemberListPage.css'/>">
<script type="text/javascript" src="../js/memberList.js"></script>
</head>
<body>

	<c:if test="${not empty message}">
		<script type='text/javascript'>
			alert('${message}');
		</script>
	</c:if>

	<div class="main">
		<a class="logo" href="/webProjectSJ">TALK</a>

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
					<c:set var="i" value="${pageVO.currentPage*10-10+1}"></c:set>

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
							<td><a href="#" class="useYns"
								data-uid="${memberList.user_id}"
								data-ucondition="${memberList.user_condition}">${memberList.user_condition}</a>
							<td><c:if test="${memberList.user_condition eq '비활성화' }">
									<a href="#" id="deleteMember" class="deleteUids"
										data-uid="${memberList.user_id}">회원탈퇴</a>
								</c:if></td>
							<c:set var="i">${i+1}</c:set>
						</tr>
					</c:forEach>
				</tbody>


			</table>
			<div class="paginationDiv">
				<ul class="pagination">
					<c:if test="${ pageVO.prev }">
						<li><a class="bt prev"
							href="/webProjectSJ/Member/adminPage.do?pageNum=1"><<</a></li>
						<li><a class="bt prev"
							href="/webProjectSJ/Member/adminPage.do?pageNum=${ pageVO.startPage - 1 }"><</a></li>
					</c:if>
					<c:forEach begin="${pageVO.startPage}" end="${pageVO.endPage}"
						var="num">
						<li><a
							<c:if test="${num eq pageVO.currentPage }">style="background-color:#6667AB; color:white;"</c:if>
							href="/webProjectSJ/Member/adminPage.do?pageNum=${num}">${num}</a></li>
					</c:forEach>
					<c:if test="${ pageVO.next }">
						<li><a class="bt next"
							href="/webProjectSJ/Member/adminPage.do?pageNum=${ pageVO.endPage + 1  }">></a></li>
						<li><a class="bt next"
							href="/webProjectSJ/Member/adminPage.do?pageNum=${ pageVO.realEnd  }">>></a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>