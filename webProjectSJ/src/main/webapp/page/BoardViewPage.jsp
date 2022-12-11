<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 게시글</title>
<link rel="stylesheet"
	href="<c:url value='/page/css/BoardViewPage.css'/>">
<script type="text/javascript" src="../js/boardDelete.js"></script>
</head>
<body>
	<div class="main">
		<div class="logo" id="logoDiv">
			<a class="logo" href="../page/MainPage.jsp">TALK</a>
		</div>
		<div class="boardHeadDiv">
			<div class="inline">
				<input type="hidden" id="realBno" value="${boardVO.bno})">
				<h3 id="bno" class="boardHead">no.${num}</h3>
				<h3 id="bcategory" class="boardHead">${boardVO.bcategory}</h3>
				<h3 id="bhit" class="boardHead">조회수</h3>
				<input type="text" name="hit" id="hit" class="boardHead"
					readonly="readonly" value="${boardVO.bhit}">
			</div>
			<div class="boardHeadDiv">
				<h3 id="btitle" class="boardHead">제목</h3>
				<input type="text" name="title" id="title" class="boardHead"
					readonly="readonly" value="${boardVO.btitle}">
			</div>
			<div class="inline">
				<h3 id="bwriter" class="boardHead">작성자</h3>
				<input type="text" name="writer" id="writer" class="boardHead"
					readonly="readonly" value="${boardVO.bwriter}">
				<h3 id="bdate" class="boardHead">작성일</h3>
				<input type="text" name="date" id="date" class="boardHead"
					readonly="readonly" value="${boardVO.bdate}">
			</div>
		</div>
		<div class="boardBody">
			<div id="editor">${boardVO.bcontents}</div>
		</div>


		<div class="boardFoot">


			<c:if test="${ boardVO.bwriter eq me}">
				<input type="button" id="editBoardBtn" value="수정"
					onclick="location.href='/webProjectSJ/Board/editBoard?realBno=${boardVO.bno}&num=${num}&page=${page}'">
				<button data-bno="${boardVO.bno}" data-page="${page}" type="button" id="deleteBoardBtn"
					value="삭제">삭제</button>
			</c:if>
			<input type="button" value="돌아가기"
				onclick="location.href='/webProjectSJ/Board/boardList?pageNum=${page}'">



		</div>
	</div>
</body>
</html>