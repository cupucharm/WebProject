<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 게시글 작성</title>
<link rel="stylesheet"
	href="<c:url value='/css/BoardWritePage.css'/>">

<script type="text/javascript" src="../js/boardWrite.js"></script>
<script
	src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/ckeditor.js"></script>
<script
	src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/translations/ko.js"></script>
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

	<div class="logo" id="logoDiv">
		<a class="logo" href="MainPage.jsp">TALK</a>
	</div>
	<form action="/webProjectSJ/Board/boardUpload" method="POST"
		class="register" id="boardUpload" enctype="multipart/form-data">
		<div class="main">


			<div class="boardHeadDiv">
				<div class="inline">
					<h3 class="boardHead">제목</h3>
					<input type="text" name="btitle" id="btitle" class="boardHead">
				</div>
				<div class="inline">
					<h3 class="boardHead">작성자</h3>
					<input type="text" name="bwriter" id="bwriter" class="boardHead"
						readonly="readonly" value="${login_id}">

					<h3 class="boardHead">카테고리</h3>
					<select name="bcategory" id="bcategory">
						<option value="" selected="selected">카테고리 선택</option>
						<option value="공지사항">공지사항</option>
						<option value="일반게시판">일반게시판</option>
						<option value="Q&A">Q&A</option>
					</select>
				</div>
			</div>
			<div class="boardBody">
				<textarea name="bcontents" id="bcontents" placeholder="내용을 입력하세요."></textarea>
			</div>
			<div class="uploadFile">
				<!-- <input type="button" value="첨부파일 추가하기" id="addFileListBtn" name="addFileListBtn" multiple="multiple">
				<div id = "file_list"></div> -->
				<!-- 첨부파일 : <input type="file" name="filename1" id="filename1" multiple="multiple"><br /> -->

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
		</div>
		<div class="boardFoot">
			<input type="submit" id="uploadBtn" value="게시글 등록">

			<button onclick="location.href='/webProjectSJ/Board/boardList.do'">취소</button>
		</div>
	</form>

</body>
</html>