<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SooJin : 채팅방 목록</title>
<link rel="stylesheet" href="css/ChatListPage.css">
<!-- <script type="text/javascript" src="../js/chatList.js"></script> -->

</head>
<body>
	<c:choose>
		<c:when test="${not empty login_id}">
			<div id="makeChatRoom">
				<h3>채팅방 생성하기</h3>
				<label for="chatRoomName">채팅방 이름 </label> <input type="text"
					id="chatRoomName">
				<button id="newChatRoomBtn">채팅방 생성하기</button>
			</div>

			<div id="cahtRoomList">
				<h3>채팅방 목록</h3>
				<div id="showChatRoomList"></div>
			</div>
		</c:when>
		<c:otherwise>
			<c:redirect url="LoginPage.jsp" ></c:redirect>
		</c:otherwise>
	</c:choose>


	<script>
		function openChatRoom(chatRoomName){
			window.open("../chat/add?p="+chatRoomName,
					"popupname",
					"toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=yes,copyhistory=0, width=500, height=820"
			);
		}
	</script>





	<script>
	let showChatRoomList = document.querySelector("#showChatRoomList");
	let chatRoomName = document.querySelector("#chatRoomName");
	let inCount = 0;
	
	let newChatRoomBtn = document.querySelector("#newChatRoomBtn");
	newChatRoomBtn.onclick = () => {
		ChatRoomList();
	};
	
	function ChatRoomList() {
		showChatRoomList.innerHTML += "채팅방 이름 : <a href='#' onclick='openChatRoom(chatRoomName.value)'>" + chatRoomName.value + "</a>" + " | 참여 인원 수 : " + inCount + "<br>";
	
	}
/*	
	function popup() {
		var url = "../page/ChattingPage.jsp";
		var name = "chatting_popup";
		var option = "toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=yes,copyhistory=0, width=500, height=820"
		window.open(url, name, option);
	}
	*/
</script>
	<script>
	let roomName = "${p}";
	const webSocket = new WebSocket("ws://localhost:8880/webProjectSJ/broadsocket/noUse?list");
	
	// 콘솔 텍스트 에리어 오브젝트
    const showChatRoomList = document.getElementById("showChatRoomList");
	
    webSocket.onopen = function(message) {
    };
    webSocket.onclose = function(message) {
    };
    webSocket.onerror = function(message) {
    };
    webSocket.onmessage = function(message) {
        // 채팅방 목록출력
        let showChatRoomList = document.getElementById("showChatRoomList");        
        let json = JSON.parse(message.data);

        const map = json.roomNameAndCount;
        let html = "";
        for (const key of Object.keys(map)) {
            const value = map[key];

            html += "채팅방 이름 : <a href='#' onclick='openChatRoom(" + key + ")'>"+key+"</a> | 참여 인원 수 : " + value;
            html += "<br/>";
        }
        showChatRoomList.value += message.data + "\n";
    };
    function sendMessage() {
       
    }
    function disconnect() {
        webSocket.close();
    }
 
</script>
</body>
</html>
