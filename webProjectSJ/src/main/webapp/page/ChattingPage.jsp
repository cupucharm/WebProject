<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : Chatting</title>
<link rel="stylesheet" href="page/css/ChattingPage.css">
<!-- <script type="text/javascript" src="../js/chat.js"></script> -->
</head>
<body>
	<h1>채팅 중</h1>
	<h4></h4>
	<form>
		<input id="user" type="text" value="${ login_id }" readonly="readonly">

		<textarea id="messageTextArea" rows="10" cols="50"></textarea>

		<input id="textMessage" type="text" autocomplete="none"> <input
			onclick="sendMessage()" value="Send" type="button"> <input
			id="disconnectBtn" value="Disconnect" type="button">
	</form>
	<br />

	<script>
		let roomName = "${p}";
		let login_id = "${login_id}"
		const webSocket = new WebSocket(
				"ws://localhost:8880/webProjectSJ/broadsocket/" + roomName);

		// 콘솔 텍스트 에리어 오브젝트
		const messageTextArea = document.getElementById("messageTextArea");

		// WebSocket 서버와 접속이 되면 호출되는 함수
		webSocket.onopen = function(message) {
			console.log("onopen");
			// 콘솔 텍스트에 메시지를 출력한다.
			messageTextArea.value += "Server connect...\n";
		};
		// WebSocket 서버와 접속이 끊기면 호출되는 함수
		webSocket.onclose = function(message) {
			// 콘솔 텍스트에 메시지를 출력한다.
			messageTextArea.value += "Server Disconnect...\n";
		};
		// WebSocket 서버와 통신 중에 에러가 발생하면 요청되는 함수
		webSocket.onerror = function(message) {
			// 콘솔 텍스트에 메시지를 출력한다.
			messageTextArea.value += "error...\n";
		};
		/// WebSocket 서버로 부터 메시지가 오면 호출되는 함수
		webSocket.onmessage = function(message) {
			// 콘솔 텍스트에 메시지를 출력한다.
			messageTextArea.value += message.data + "\n";
		};
		// Send 버튼을 누르면 호출되는 함수
		function sendMessage() {
			// 유저명 텍스트 박스 오브젝트를 취득
			const user = document.getElementById("user").textContent;
			// 송신 메시지를 작성하는 텍스트 박스 오브젝트를 취득
			const message = document.getElementById("textMessage");
			// 콘솔 텍스트에 메시지를 출력한다.
			messageTextArea.value += user + login_id + " => " + message.value
					+ "\n";
			// WebSocket 서버에 메시지를 전송(형식 「{{유저명}}메시지」)
			console.log(message.value);
			webSocket.send(user + login_id + " => " + message.value);

			// 송신 메시지를 작성한 텍스트 박스를 초기화한다.
			message.value = "";
		}
		// Disconnect 버튼을 누르면 호출되는 함수
		function disconnect() {
			// WebSocket 접속 해제
			webSocket.close();
		}
	</script>
</body>
</html>





