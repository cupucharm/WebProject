package Chat;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChatAction {

	public String chatListForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/ChatListPage.jsp";

	}

	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		String roomName = request.getParameter("p");
		request.setAttribute("p", roomName);
		request.setAttribute("login_id", session.getAttribute("login_id"));
//		RequestDispatcher dispatch = request.getRequestDispatcher("../page/ChattingPage.jsp");
//		dispatch.forward(request, response);
		
		return "/ChattingPage.jsp";
	}

}