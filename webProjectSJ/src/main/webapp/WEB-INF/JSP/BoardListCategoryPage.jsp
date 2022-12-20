<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 게시판</title>
<link rel="stylesheet"
	href="<c:url value='/css/BoardListPage.css'/>">
<script type="text/javascript" src="../js/boardSearch.js"></script>
</head>
<body>
	<div id="memberInfoDiv">
		<c:choose>
			<c:when test="${not empty login_id}">
				<a class="choose" class="status" href="/webProjectSJ/Member/myPage.do">${login_id}님</a>
				<a class="choose" class="status" href="/webProjectSJ/Member/logout.do">로그아웃</a>
			</c:when>
			<c:otherwise>
				<a class="choose" class="status" href="/webProjectSJ/Member/loginForm.do">로그인</a>
				<a class="choose" class="status" href="/webProjectSJ/Member/registerForm.do">회원가입</a>
			</c:otherwise>
		</c:choose>
			
	</div>

	<div class="main">
		<a class="logo" href="/webProjectSJ">TALK</a>

		<div id="search">
			<select name="searchSelect" id="searchSelect">
				<option value="titleAndContents" selected="selected">제목 +
					내용</option>
				<option value="title">제목</option>
				<option value="contents">내용</option>
				<option value="writer">작성자</option>
			</select> <input type="text" id="searchInput" name="searchInput">
			<button id="searchBtn">검색</button>
		</div>

		<div id="chooseCategoryDiv">
			<button class="categoryBtn" id="all" value="all">전체글보기</button>
			<button class="categoryBtn" id="notice" value="notice">공지사항</button>
			<button class="categoryBtn" id="commom" value="commom">일반게시판</button>
			<button class="categoryBtn" id="Question" value="Question">Q&A</button>
		</div>

		<div class="board">
			<table class="boardTable">
				<thead class="boardThead">
					<tr>
						<td>번호</td>
						<td>분류</td>
						<td>제목</td>
						<td>글쓴이</td>
						<td>작성일</td>
						<td>조회수</td>
					</tr>
				</thead>
				<tbody class="boardTbody">
				<tbody>
					<c:set var="i" value="${pageVO.currentPage*10-10+1}"></c:set>

					<c:forEach var="boardList" items="${listBoards}">
						<tr>
							<td>${i}</td>
							<td>${boardList.bcategory}</td>
							<td style="text-align: left;"><a
								href="/webProjectSJ/Board/boardView.do?bno=${boardList.bno}&num=${i}&page=${pageVO.currentPage}">

									<c:choose>
										<c:when test="${boardList.bno == boardList.bparentNo}">${boardList.btitle}</c:when>
										<c:otherwise>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ➥&nbsp;[답변] ${boardList.btitle}</c:otherwise>
									</c:choose>
							</a></td>
							<td>${boardList.bwriter}</td>
							<td>${boardList.bdate}</td>
							<td>${boardList.bhit}</td>
							<c:set var="i">${i+1}</c:set>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="paginationDiv">
				<ul class="pagination">
					<c:if test="${ pageVO.prev }">
						<li><a class="bt next"
							href="/webProjectSJ/Board/view.do?content=${ category }&pageNum=1"><<</a></li>
						<li><a class="bt prev"
							href="/webProjectSJ/Board/view.do?content=${ category }&pageNum=${ pageVO.startPage - 1 }"><</a></li>
					</c:if>
					<c:forEach begin="${pageVO.startPage}"
						end="${pageVO.endPage}" var="num">
						<li><a
							<c:if test="${num eq pageVO.currentPage }">style="background-color:#6667AB; color:white;"</c:if>
							href="/webProjectSJ/Board/view.do?content=${ category }&pageNum=${num}">${num}</a></li>
					</c:forEach>
					<c:if test="${ pageVO.next }">
						<li><a class="bt next"
							href="/webProjectSJ/Board/view.do?content=${ category }&pageNum=${ pageVO.endPage + 1  }">></a></li>
						<li><a class="bt next"
							href="/webProjectSJ/Board/view.do?content=${ category }&pageNum=${ pageVO.realEnd  }">>></a></li>
					</c:if>
				</ul>
			</div>

			<div class="bt_wrap">
				<c:if test="${ isLogon }">
					<a href="/webProjectSJ/Board/boardWriteForm.do" class="on">게시글
						작성</a>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>