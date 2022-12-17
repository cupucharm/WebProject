package Member;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Map<String, Object> className2ObjectMap = new HashMap<>();
	Map<String, Object> objectMap = new HashMap<>();
	Map<String, Method> methodMap = new HashMap<>();
	String contextPath;

	public MemberServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
contextPath = config.getServletContext().getContextPath();
		
		String actionNames = config.getInitParameter("actionNames");
		actionNames = actionNames.trim();
		
		try {
		for (String line : actionNames.split("\n")) {
			line = line.trim();
			
			String [] actionInfo = line.split(":");
			//클래스를 로딩한다
			Class<?> cls = Class.forName(actionInfo[1]);
			
			//클래스명이 존재하는지 확인한다 
			if (!className2ObjectMap.containsKey(actionInfo[1])) {
				//클래스를 이용하여 객체를 생성한다
				Object object = cls.getDeclaredConstructor().newInstance();
				
				//생성된 객체를 클래스 명으로 해서 맵에 추가함 
				className2ObjectMap.put(actionInfo[1], object);
				
				//생성된 객체를 URL 명으로 해서 맵에 추가함
				objectMap.put(actionInfo[0], object);
			} else {
				
				//클래스명으로 해서 생성된 객체를 URL 명으로 해서 맵에 추가함
				objectMap.put(actionInfo[0], className2ObjectMap.get(actionInfo[1]));
			}
			Method method = cls.getMethod(actionInfo[2], HttpServletRequest.class, HttpServletResponse.class);
			methodMap.put(actionInfo[0], method);
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String path = request.getRequestURI();
		path = path.substring(contextPath.length());
		Object obj = objectMap.get(path);
		Method method = methodMap.get(path);
		if (obj != null && method != null) {
			try {
				method.invoke(obj, request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
