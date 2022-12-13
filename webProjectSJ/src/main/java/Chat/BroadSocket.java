package Chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.json.JSONObject;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/broadsocket/{roomName}", configurator = HttpSessionConfigurator.class)
public class BroadSocket {

	// WebSocketSession으로 키로 설정해서 HttpSession을 넣기
	private Map<Session, EndpointConfig> configs = Collections.synchronizedMap(new HashMap<>());

	private static List<Session> sessionUsers = Collections.synchronizedList(new ArrayList<>());

	private static Map<String, HashSet<String>> roomNameAndUser = new ConcurrentHashMap<>();
	private static Map<String, Integer> roomNameAndCount = new ConcurrentHashMap<>();
	private static HashSet<String> userList = new HashSet<>();
	private static Map<String, Session> userAndSocketSeession = new HashMap<>();

	// WebSocket으로 브라우저가 접속하면 요청되는 함수
	@OnOpen
	public void handleOpen(@PathParam("roomName") String roomName, Session userSession, EndpointConfig config)
			throws IOException {

//		// 채팅방 인원수 초기화
//				int count = roomNameAndCount.getOrDefault(roomName, 0);
//				roomNameAndCount.put(roomName, count + 1);
//				HashSet<String> userList = roomNameAndUser.getOrDefault(roomName, new HashSet<>());

		String queryString = userSession.getQueryString();
		if ("list".equals(queryString)) {
			// 여기는 리스트

			// 채팅방 목록을 가져옴
			// 채팅방 이름, 카운트 수를 front로 넘길 준비를 함.
			// HashSet으로 front로 넘김

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("roomNameAndCount", roomNameAndCount);
			System.out.println("여기보세요" + String.valueOf(jsonObject));
			try {
				// client로 데이터를 넘기는 부분
				userSession.getBasicRemote().sendText(String.valueOf(jsonObject));
				return;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		HttpSession session = (HttpSession) config.getUserProperties().get(HttpSessionConfigurator.Session);
		String userId = (String) session.getAttribute("login_id");

		// 이쪽으로넘오여모녀 채팅이 시작된거심
		// 이쪽에서룸네임을 가져와서 +1

		// 채팅방 인원수 초기화
		int count = roomNameAndCount.getOrDefault(roomName, 0);
		roomNameAndCount.put(roomName, count + 1);
		HashSet<String> userList = roomNameAndUser.getOrDefault(roomName, new HashSet<>());

		System.out.println("1111111111" + roomName);
		System.out.println("--------" + roomNameAndCount.get(roomName));

		// 페이지 새로그침 시 웹 소켓 세션 갱
		// Hash의 경우에는 기존에 key값을 put하면 계속 갱신됨
		// map.put(kingle, 11); map.put(kingle, 22);
		userAndSocketSeession.put(userId, userSession);

		// 채팅방이 없으면 -> 채팅방을 생성
		// 채팅방이 있으면 -> 채팅방에 참가 -> 참가할 때에는 자신의 아이디로 참가를 해야함.

		// 채팅방이 없으면 채팅방 추가
		if (roomNameAndUser.get(roomName) == null) {
			userList.add(userId); // 본인을 추가
			roomNameAndUser.put(roomName, userList);
			// -> 채팅방이 생성되고, 본인이 채팅방에 들어감
		} else {
			// 기존에 채팅방 안에 있는 사람들의 목록을 가져옴
			userList = roomNameAndUser.get(roomName);
			// 유저 리스트에 본인이 없으면 추가
			if (!userList.contains(userId)) {
				userList.add(userId);
			}
		}

		roomNameAndUser.put(roomName, userList);

	}

	// WebSocket으로 메시지가 오면 요청되는 함수
	@OnMessage
	public void handleMessage(@PathParam("roomName") String roomName, String message, Session userSession)
			throws IOException {
		// 채팅방에 있는 아이디들이 hashSet에 담김
		HashSet<String> userList = roomNameAndUser.get(roomName);
		Iterator<String> it = userList.iterator();
		while (it.hasNext()) {
			String userId = it.next();
			Session userSocketSession = userAndSocketSeession.get(userId);

			if (userSession.equals(userSocketSession))
				continue;
			try {
				userSocketSession.getBasicRemote().sendText(message);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
	@OnClose
	public void handleClose(@PathParam("roomName") String roomName, Session userSession) {
		// session 리스트로 접속 끊은 세션을 제거한다.

		// 콘솔에 접속 끊김 로그를 출력한다.
		System.out.println("client is now disconnected...");

		userAndSocketSeession.remove(userSession);
		roomNameAndCount.put(roomName, roomNameAndCount.get(roomName) - 1);
		if (roomNameAndCount.get(roomName) == 0) {
			roomNameAndCount.remove(roomName);
			roomNameAndUser.remove(roomName);
		}

		sessionUsers.remove(userSession);
		if (configs.containsKey(userSession)) {
			configs.remove(userSession);
		}
	}

	@OnError
	public void handleError(Throwable e, Session userSession) {
		e.printStackTrace();
	}
}