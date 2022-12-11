/**
 * 
 */
window.onload = () => {

	let showChatRoomList = document.querySelector("#showChatRoomList");
	let chatRoomName = document.querySelector("#chatRoomName");
	let inCount = 0;

	let newChatRoomBtn = document.querySelector("#newChatRoomBtn");
	newChatRoomBtn.onclick = () => {
		ChatRoomList();
	};

	function ChatRoomList() {
		showChatRoomList.innerHTML += "<a href='#' onclick='popup()'>" + chatRoomName.value + "</a>" + " | people : " + inCount + "<br>";

	}

	function popup() {
		var url = "../page/ChattingPage.jsp";
		var name = chatRoomName;
		var option = "width = 500, height = 500, top = 100, left = 200, location = no"
		window.open(url, name, option);
	}
}