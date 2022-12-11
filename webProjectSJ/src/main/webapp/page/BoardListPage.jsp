<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 게시판</title>
<link rel="stylesheet" href="<c:url value='/page/css/BoardListPage.css'/>">
</head>
<body>
	<div class="main">
		<a class="logo" href="../page/MainPage.jsp">TALK</a>

		<div id="search">
		<select name="searchSelect" id="searchSelect">
						<option value="제목 + 내용" selected="selected">제목 + 내용</option>
						<option value="제목">제목</option>
						<option value="내용">내용</option>
						<option value="작성자">작성자</option>
					</select>
					<input type="text" id="searchInput" name="searchInput" >
					<button id="searchBtn">검색</button>
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
					<c:set var="i" value="${boardPageVO.currentPage*10-10+1}"></c:set>

					<c:forEach var="boardList" items="${listBoards}">
						<tr>
							<td>${i}</td>
							<td>${boardList.bcategory}</td>
							<td><a href="/webProjectSJ/Board/boardView?bno=${boardList.bno}&num=${i}&page=${boardPageVO.currentPage}">${boardList.btitle}</a></td>
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
				<c:if test="${ boardPageVO.prev }">
                  <li><a class="bt prev" href="/webProjectSJ/Board/boardList?pageNum=${ boardPageVO.startPage - 1 }"><</a></li>
               </c:if>
					<c:forEach begin="${boardPageVO.startPage}" end="${boardPageVO.endPage}" var="num">
						<li><a href="/webProjectSJ/Board/boardList?pageNum=${num}">${num}</a></li>
					</c:forEach>
					<c:if test="${ boardPageVO.next }">
                  <li><a class="bt next" href="/webProjectSJ/Board/boardList?pageNum=${ boardPageVO.endPage + 1  }">></a></li>
               </c:if>
				</ul>
			</div>

			<div class="bt_wrap">
			<c:if test="${ isLogon }">
				<a href="/webProjectSJ/page/BoardWritePage.jsp" class="on">게시글 작성</a>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>