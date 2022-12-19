package Member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import Board.BoardDAO;
import Board.PageVO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MemberAction {

	public JSONObject register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		String jsonStr = in.readLine();

		JSONObject jsonMember = new JSONObject(jsonStr);

		String user_id = jsonMember.getString("user_id");
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
							user_sex, user_birth, "활성화"));
					jsonResult.put("status", true);
					jsonResult.put("url", "/webProjectSJ/Member/loginForm.do");
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
		return jsonResult;
	}

	public String registerForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/RegisterPage.jsp";
	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);
		
		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");

		MemberDAO dao = new MemberDAO();
		MemberVO memberVO = dao.isExisted(user_id, user_pwd);
		Boolean login_can = dao.isLoginCan(user_id);

		if (memberVO != null && login_can) {
			session.setAttribute("isLogon", true);
			session.setAttribute("login_id", user_id);
			session.setAttribute("login_pwd", user_pwd);
			session.setAttribute("login_name", memberVO.getUser_name());

			if (memberVO.getUser_condition().equals("admin")) {
				session.setAttribute("admin", "admin");
			}

			response.sendRedirect("/webProjectSJ");
		} else if (!login_can) {
			request.setAttribute("loginFail", "<script>alert('로그인할 수 없는 계정입니다.');</script>");

			RequestDispatcher dispatch = request.getRequestDispatcher("loginForm.do");
			dispatch.forward(request, response);

		} else {
			request.setAttribute("loginFail", "<script>alert('로그인 실패했습니다. 다시 로그인해주세요.');</script>");
			
			RequestDispatcher dispatch = request.getRequestDispatcher("loginForm.do");
			dispatch.forward(request, response);
		}

	}

	public String loginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		return "/LoginPage.jsp";
	}

	public void logout(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);
		
		session = request.getSession();
		session.invalidate();

		response.sendRedirect("/webProjectSJ");

	}

	public String myPage(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);
		
		MemberDAO dao = new MemberDAO();
		MemberVO memberInfo = dao.memberInfo((String) session.getAttribute("login_id"));

		request.setAttribute("memberInfo", memberInfo);
		String login_name = (String) session.getAttribute("login_name");

		request.setAttribute("session_login_name", login_name);

		return "/MyPage.jsp";
	}

	public JSONObject dupUidCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		return jsonResult;
	}

	public JSONObject pwdCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		return jsonResult;
	}

	public JSONObject searchId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		return jsonResult;
	}
	
	public String searchIdForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/SearchIdPage.jsp";
	}

	public JSONObject searchPw(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		return jsonResult;
	}
	
	public String searchPwForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/SearchPwPage.jsp";
	}

	public JSONObject updateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);
		
		
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
			jsonResult.put("url", "/webProjectSJ/Member/myPage.do");
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", "회원 정보 수정을 실패했습니다.");
		}
		return jsonResult;
	}

	public JSONObject pwdUpdateCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);
		
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
		return jsonResult;
	}

	public JSONObject memberDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);
		
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
		return jsonResult;
	}
	
	public String memberDeleteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/MemberDeletePage.jsp";
	}

	public String memberEdit(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String user_id = request.getParameter("user_id");

		MemberDAO memberDAO = new MemberDAO();
		MemberVO member = memberDAO.memberInfo(user_id);

		request.setAttribute("memberInfo", member);

		return "/MemberEditPage.jsp";
	}

	public String adminPage(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		int pageNum = 1;

		if (request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}

		MemberDAO memberDAO = new MemberDAO();

		List<MemberVO> list = memberDAO.listMembers(pageNum);
		int total = memberDAO.getTotal();

		PageVO pageVO = new PageVO(pageNum, total);

		request.setAttribute("listMembers", list);
		request.setAttribute("pageVO", pageVO);

		return "/MemberListPage.jsp";
	}

	public void memberCondition(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String user_id = request.getParameter("user_id");

		MemberDAO dao = new MemberDAO();
		String condition = dao.getCondition(user_id);
		if (condition != null) {
			dao.changeCondition(user_id, condition);
		}
		response.sendRedirect("adminPage.do");
	}

	public String memberAdminDelete(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String user_id = request.getParameter("user_id");

		MemberDAO dao = new MemberDAO();
		Boolean success = dao.memberAdminDelete(user_id);

		if (success) {
			request.setAttribute("message", user_id + "님이 삭제되었습니다.");
		} else {
			request.setAttribute("message", user_id + "님 삭제를 실패했습니다.");
		}

		return "adminPage.do";
	}

	public String searchAdmin(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		String searchInput = request.getParameter("searchInput");

		MemberDAO dao = new MemberDAO();
		List<MemberVO> list = dao.selectByUid(searchInput);

		BoardDAO boardDao = new BoardDAO();
		int pageNum = 1;
		int total = boardDao.getTotal();

		PageVO pageVO = new PageVO(pageNum, total);

		request.setAttribute("listMembers", list);
		request.setAttribute("pageVO", pageVO);

		return "/MemberListPage.jsp";
	}
}
