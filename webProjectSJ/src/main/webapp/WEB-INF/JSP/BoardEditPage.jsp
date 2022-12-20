<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 게시글</title>
<link rel="stylesheet" href="<c:url value='/css/BoardViewPage.css'/>">

<script type="text/javascript" src="../js/boardEdit.js"></script>
<script
	src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/ckeditor.js"></script>
<script
	src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/translations/ko.js"></script>
</head>
<body>
	<div class="main">
		<div class="logo" id="logoDiv">
			<a class="logo" href="/webProjectSJ">TALK</a>
		</div>

		<form action="/webProjectSJ/Board/editBoard.do" method="POST"
			class="register" id="boardEdit" enctype="multipart/form-data">
			<div class="boardHeadDiv">
				<div class="inline">
					<input type="hidden" name="realBno" value="${boardVO.bno}">
					<input type="hidden" name="bcategory" value="${boardVO.bcategory}">
					<h3 id="bno" class="boardHead">no.${num}</h3>
					<h3 id="bcategory" class="boardHead">${boardVO.bcategory}</h3>
					<h3 id="bhit" class="boardHead">조회수</h3>
					<input type="text" name="hit" id="hit" class="boardHead"
						readonly="readonly" value="${boardVO.bhit}">
				</div>
				<div id="btitleDiv" class="boardHeadDiv">
					<h3 id="btitle" class="boardHead">제목</h3>
					<input type="text" name="title" id="title" class="boardHead"
						value="${boardVO.btitle}">
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
				<textarea name="bcontents" id="bcontents">${boardVO.bcontents}</textarea>
			</div>

			<div class="uploadFile">


				<c:forEach var="boardFile" items="${boardFile}">
					<input type="text" value="${boardFile.forg_name }">
					<br>
				</c:forEach>

				<table>
					<tbody>
						<tr>
							<th><label>첨부파일</label></th>
							<td><input type="file" name="filename1" id="filename1" /></td>
							<td><input type="button" value="추가" class="insertFile"></td>
						</tr>
					</tbody>
					<tfoot>
						<tr style="display: none">
							<th><label>첨부파일</label></th>
							<td><input type="file" name="filename1" id="filename1" /></td>
							<td style="display: none"><input type="button" value="추가"
								class="insertFile"></td>
							<td><input type="button" value="삭제" class="deleteFile"></td>
						</tr>
					</tfoot>
				</table>
				<br />
			</div>


			<div class="boardFoot">
				<input type="hidden" id="page" value="${page}"> <input
					type="hidden" id="num" value="${num}"> <input type="button"
					id="updateBoardBtn" value="확인"> <input type="button"
					id="backBtn" value="취소"
					onclick="location.href='/webProjectSJ/Board/boardView.do?bno=${boardVO.bno}&num=${num}&page=${page}'">

			</div>
		</form>
	</div>
</body>
</html>