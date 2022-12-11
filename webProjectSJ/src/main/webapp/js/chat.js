/**
 * 
 */

window.onload = () => {

	// 콘솔 텍스트 에리어 오브젝트
	var messageTextArea = document.getElementById("messageTextArea");

	let sendBtn = document.querySelector("#sendBtn");
	sendBtn.onclick = () => {
		sendMessage();
	};

	let disconnectBtn = document.querySelector("#disconnectBtn");
	disconnectBtn.onclick = () => {
		disconnect();
	};

	function connectWebSocket(url, message, open, close, error) {
      // WebSocket 오브젝트 생성 (자동으로 접속 시작한다. - onopen 함수 호출)
      let webSocket = new WebSocket(url);
      // 함수 체크하는 함수
      function call(cb, msg) {
        // cb가 함수 타입인지 확인
        if (cb !== undefined && typeof cb === "function") {
          // 함수 호출
          cb.call(null, msg);
        }
      }
      // WebSocket 서버와 접속이 되면 호출되는 함수
      webSocket.onopen = function() {
        call(open);
      };
      // WebSocket 서버와 접속이 끊기면 호출되는 함수
      webSocket.onclose = function() {
        call(close);
      };
      // WebSocket 서버와 통신 중에 에러가 발생하면 요청되는 함수
      webSocket.onerror = function() {
        call(error);
      };
      // WebSocket 서버로 부터 메시지가 오면 호출되는 함수
      webSocket.onmessage = function(msg) {
        call(message, msg);
      };
      
      return webSocket;
    }
    
    // 연결 발생 때 사용할 callback 함수
    var open = function() {
      messageTextArea.value += "Server connect...\n";
    }
    // 종료 발생 때 사용할 callback 함수
    var close = function() {
      messageTextArea.value += "Server Disconnect...\n";
      setTimeout(function() {
        webSocket = connectWebSocket("ws://localhost:8880/WebSocket/broadsocket", message, open, close, error);
      });
    }
    // 에러 발생 때 사용할 callback 함수
    var error = function() {
      messageTextArea.value += "error...\n";
    }
    // 메세지를 받을 때 사용할 callback 함수
    var message = function(msg) {
      messageTextArea.value += msg.data + "\n";
    };
    
    // 웹 소켓 생성
    var webSocket = connectWebSocket("ws://localhost:8880/WebSocket/broadsocket", message, open, close, error);
 
    // Send 버튼을 누르면 호출되는 함수  
    function sendMessage() {
      var user = document.getElementById("user");
      var message = document.getElementById("textMessage");
      messageTextArea.value += user.value + "(me) => " + message.value + "\n";
      webSocket.send("{{" + user.value + "}}" + message.value);
      message.value = "";
    }
    // Disconnect 버튼을 누르면 호출되는 함수  
    function disconnect() {
      webSocket.close();
    }

}