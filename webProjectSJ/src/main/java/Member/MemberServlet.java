package Member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;

import Board.BoardDAO;
import Board.BoardPageVO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Member/*")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		System.out.println(request.getRequestURI());

		if (request.getRequestURI().equals("/webProjectSJ/Member/login")) {

			String user_id = request.getParameter("user_id");
			String user_pwd = request.getParameter("user_pwd");

			MemberDAO dao = new MemberDAO();
			MemberVO memberVO = dao.isExisted(user_id, user_pwd);

			if (memberVO != null) {
				session.setAttribute("isLogon", true);
				session.setAttribute("login_id", user_id);
				session.setAttribute("login_pwd", user_pwd);
				session.setAttribute("login_name", memberVO.getUser_name());

				response.sendRedirect("../page/MainPage.jsp");
			} else {
				request.setAttribute("loginFail", "<script>alert('로그인 실패했습니다. 다시 로그인해주세요.');</script>");

				RequestDispatcher dispatch = request.getRequestDispatcher("../page/LoginPage.jsp");
				dispatch.forward(request, response);
			}

		} else if (request.getRequestURI().equals("/webProjectSJ/Member/logout")) {
			session = request.getSession();
			session.invalidate();

			response.sendRedirect("../page/MainPage.jsp");

		} else if (request.getRequestURI().equals("/webProjectSJ/Member/MyPage")) {
			MemberDAO dao = new MemberDAO();
			MemberVO memberInfo = dao.memberInfo((String) session.getAttribute("login_id"));

			request.setAttribute("memberInfo", memberInfo);
			String login_name = (String) session.getAttribute("login_name");

			request.setAttribute("session_login_name", login_name);

			RequestDispatcher dispatch = request.getRequestDispatcher("../page/MyPage.jsp");
			dispatch.forward(request, response);
		} else if (request.getRequestURI().equals("/webProjectSJ/Member/dupUidCheck")) {
			String user_id = request.getParameter("user_id");

			MemberDAO memberDAO = new MemberDAO();
			MemberVO memberVO = memberDAO.checkMember(user_id);
			JSONObject jsonResult = new JSONObject();

			if (user_id != null && user_id.length() > 0) {

				if (memberVO == null) {
					jsonResult.put("status", true);
					jsonResult.put("message", "[ " + user_id + " ] " + "사용 가능한 아이디입니다.");
				} else {
					jsonResult.put("status", false);
					jsonResult.put("message", "[ " + user_id + " ] " + "이미 사용 중인 아이디입니다.");
				}
			} else {
				jsonResult.put("status", false);
				jsonResult.put("message", "아이디를 확인하세요.");
			}
			out.println(jsonResult.toString());
		} else if (request.getRequestURI().equals("/webProjectSJ/Member/register")) {

			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String jsonStr = in.readLine();

			JSONObject jsonMember = new JSONObject(jsonStr);

			String user_id = (String) jsonMember.get("user_id");
			String user_name = (String) jsonMember.get("user_name");
			String user_pwd = (String) jsonMember.get("user_pwd");
			String pwdConfirm = (String) jsonMember.get("pwdConfirm");
			String user_phone = (String) jsonMember.get("user_phone");
			String user_email = (String) jsonMember.get("user_email");
			String user_sex = (String) jsonMember.get("user_sex");
			String user_birth = (String) jsonMember.get("user_birth");

			JSONObject jsonResult = new JSONObject();

			try {

				if (user_id != null && user_id.length() > 0 && user_name != null && user_name.length() > 0
						&& user_pwd != null && user_pwd.length() > 0) {

					if (user_pwd.equals(pwdConfirm)) {
						MemberDAO memberDAO = new MemberDAO();
						memberDAO.insertMember(new MemberVO(user_id, user_name, user_pwd, user_phone, user_email,
								user_sex, user_birth, "t"));
						jsonResult.put("status", true);
						jsonResult.put("url", "/webProjectSJ/page/LoginPage.jsp");
						jsonResult.put("message", user_id + "님 회원가입을 축하드립니다!");
					} else {
						jsonResult.put("status", false);
						jsonResult.put("message", "비밀번호를 확인해주세요.");
					}
				} else {
					jsonResult.put("status", false);
					jsonResult.put("message", "빈칸을 입력하세요.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
				jsonResult.put("status", false);
				jsonResult.put("message", "[ " + user_id + " ] " + "이미 사용 중인 아이디입니다.");
			}
			out.println(jsonResult.toString());

		} else if (request.getRequestURI().equals("/webProjectSJ/Member/pwdCheck")) {
			String user_pwd = request.getParameter("user_pwd");
			String pwdConfirm = request.getParameter("pwdConfirm");

			JSONObject jsonResult = new JSONObject();

			if (user_pwd.equals(pwdConfirm)) {
				jsonResult.put("status", true);
				jsonResult.put("message", "비밀번호가 일치합니다.");
			} else {
				jsonResult.put("status", false);
				jsonResult.put("message", "비밀번호가 불일치합니다.");
			}
			out.println(jsonResult.toString());
		} else if (request.getRequestURI().equals("/webProjectSJ/Member/searchId")) {

			String user_name = request.getParameter("user_name");
			String user_phone = request.getParameter("user_phone");

			JSONObject jsonResult = new JSONObject();

			MemberDAO dao = new MemberDAO();
			String user_id = dao.searchId(user_name, user_phone);

			if (user_id != null) {
				jsonResult.put("status", true);
				jsonResult.put("id", user_id);
				jsonResult.put("message", "회원님의 id는 " + user_id + "입니다.");
			} else {
				jsonResult.put("status", false);
				jsonResult.put("message", "일치하는 회원정보가 없습니다.");
			}
			out.println(jsonResult.toString());
		} else if (request.getRequestURI().equals("/webProjectSJ/Member/searchPw")) {

			String user_id = request.getParameter("user_id");
			String user_name = request.getParameter("user_name");
			String user_phone = request.getParameter("user_phone");

			JSONObject jsonResult = new JSONObject();

			MemberDAO dao = new MemberDAO();
			String user_pwd = dao.searchPw(user_id, user_name, user_phone);

			if (user_pwd != null && user_pwd.length() > 0) {
				jsonResult.put("status", true);
				jsonResult.put("pwd", user_pwd);
				jsonResult.put("message", "회원님의 비밀번호는 " + user_pwd + "입니다.");
			} else {
				jsonResult.put("status", false);
				jsonResult.put("message", "일치하는 회원정보가 없습니다.");
			}
			out.println(jsonResult.toString());
		} else if (request.getRequestURI().equals("/webProjectSJ/Member/updateMember")) {

			String user_pwd = request.getParameter("user_pwd");
			String user_name = request.getParameter("user_name");
			String user_phone = request.getParameter("user_phone");
			String user_email = request.getParameter("user_email");
			String user_id = (String) session.getAttribute("login_id");
			String session_name = (String) session.getAttribute("login_name");

			JSONObject jsonResult = new JSONObject();

			MemberDAO dao = new MemberDAO();
			Boolean update = dao.updateMember(user_pwd, user_name, user_phone, user_email, user_id);

			if (update) {
				jsonResult.put("status", true);
				jsonResult.put("message", "회원 정보가 수정되었습니다.");
			} else {
				jsonResult.put("status", false);
				jsonResult.put("message", "회원 정보 수정을 실패했습니다.");
			}
			out.println(jsonResult.toString());

		} else if (request.getRequestURI().equals("/webProjectSJ/Member/pwdUpdateCheck")) {
			String user_pwd = request.getParameter("user_pwd");
			String user_id = (String) session.getAttribute("login_id");

			JSONObject jsonResult = new JSONObject();

			MemberDAO dao = new MemberDAO();
			String before_pwd = dao.getPwd(user_id);

			if (user_pwd.equals(before_pwd)) {
				jsonResult.put("status", true);
				jsonResult.put("message", "비밀번호가 일치합니다.");
			} else {
				jsonResult.put("status", false);
				jsonResult.put("message", "비밀번호가 불일치합니다.");
			}
			out.println(jsonResult.toString());
		} else if (request.getRequestURI().equals("/webProjectSJ/Member/memberDelete")) {
			String user_id = (String) session.getAttribute("login_id");
			String user_pwd = (String) session.getAttribute("login_pwd");

			JSONObject jsonResult = new JSONObject();
			try {
				MemberDAO dao = new MemberDAO();
				Boolean delete = dao.deleteMember(user_id, user_pwd);

				if (delete) {
					jsonResult.put("status", true);
					jsonResult.put("message", "회원 탈퇴를 성공했습니다. 안녕히 가세요.");
					session.invalidate();
				} else {
					jsonResult.put("status", false);
					jsonResult.put("message", "회원 탈퇴를 실패했습니다.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.println(jsonResult.toString());
		} else if (request.getRequestURI().equals("/webProjectSJ/Member/memberEdit")) {

			String user_id = request.getParameter("user_id");

			MemberDAO memberDAO = new MemberDAO();
			MemberVO member = memberDAO.memberInfo(user_id);

			request.setAttribute("memberInfo", member);

			RequestDispatcher dispatch = request.getRequestDispatcher("../page/MemberEditPage.jsp");
			dispatch.forward(request, response);

		}
	}
}
