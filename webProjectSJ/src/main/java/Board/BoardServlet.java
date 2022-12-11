package Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import Board.BoardDAO;
import Board.BoardPageVO;
import Member.MemberDAO;
import Member.MemberVO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Board/*")
public class BoardServlet extends HttpServlet {
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

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		if (request.getRequestURI().equals("/webProjectSJ/Board/boardList")) {

			int pageNum = 1;

			if (request.getParameter("pageNum") != null) {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			}

			BoardDAO boardDAO = new BoardDAO();
			int total = boardDAO.getTotal();

			List<BoardVO> list = boardDAO.listBoards(pageNum);

			BoardPageVO boardPageVO = new BoardPageVO(pageNum, total);

			request.setAttribute("listBoards", list);
			request.setAttribute("boardPageVO", boardPageVO);

			System.out.println(list.toString());

			RequestDispatcher dispatch = request.getRequestDispatcher("../page/BoardListPage.jsp");
			dispatch.forward(request, response);
		} else if (request.getRequestURI().equals("/webProjectSJ/Board/boardView")) {
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				int num = Integer.parseInt(request.getParameter("num"));
				int page = Integer.parseInt(request.getParameter("page"));

				BoardDAO dao = new BoardDAO();
				dao.countUp(bno);
				BoardVO boardVO = dao.getBoardVO(bno);
				request.setAttribute("boardVO", boardVO);
				request.setAttribute("num", num);
				request.setAttribute("page", page);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String login_id = (String) session.getAttribute("login_id");

			if (login_id != null) {
				session.setAttribute("me", login_id);
			}
			RequestDispatcher dispatch = request.getRequestDispatcher("../page/BoardViewPage.jsp");
			dispatch.forward(request, response);

		} else if (request.getRequestURI().equals("/webProjectSJ/Board/boardUpload")) {

			try {
				
				BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
				String jsonStr = in.readLine();

				JSONObject jsonResult = new JSONObject(jsonStr);
				
				String btitle = jsonResult.getString("btitle");
				String bwriter = jsonResult.getString("bwriter");
				String bcategory = jsonResult.getString("bcategory");
				String bcontents = jsonResult.getString("bcontents");

				if (btitle != null && btitle.length() > 0 && bcategory != null && bcategory.length() > 0
						&& bcontents != null && bcontents.length() > 0) {
					BoardDAO dao = new BoardDAO();

					Boolean boardInsert = dao.insertBoard(btitle, bwriter, bcategory, bcontents);

					if (boardInsert) {
						jsonResult.put("status", true);
						jsonResult.put("message", "게시글이 정상적으로 등록되었습니다.");
					} else {
						jsonResult.put("status", false);
						jsonResult.put("message", "게시글 작성을 실패했습니다.");
					}
					jsonResult.put("url", "/webProjectSJ/Board/boardList");

				} else {
					jsonResult.put("status", false);
					jsonResult.put("message", "게시글 작성을 실패했습니다. 빈칸을 채워주세요.");
					jsonResult.put("url", "/webProjectSJ/Board/boardList");
				}
				out.println(jsonResult.toString());

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (request.getRequestURI().equals("/webProjectSJ/Board/editBoard")) {
			try {
				int bno = Integer.parseInt(request.getParameter("realBno"));
				int num = Integer.parseInt(request.getParameter("num"));
				int page = Integer.parseInt(request.getParameter("page"));

				BoardDAO dao = new BoardDAO();
				BoardVO boardVO = dao.getBoardVO(bno);

				request.setAttribute("boardVO", boardVO);
				request.setAttribute("realBno", bno);
				request.setAttribute("num", num);
				request.setAttribute("page", page);

				RequestDispatcher dispatch = request.getRequestDispatcher("../page/BoardEditPage.jsp");
				dispatch.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (request.getRequestURI().equals("/webProjectSJ/Board/updateBoard")) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
				String jsonStr = in.readLine();

				JSONObject jsonResult = new JSONObject(jsonStr);
				System.out.println(jsonResult);

				int realBno = Integer.parseInt(jsonResult.getString("realBno"));
				int num = Integer.parseInt(jsonResult.getString("num"));
				int page = Integer.parseInt(jsonResult.getString("page"));
				String btitle = jsonResult.getString("btitle");
				String bcontents = jsonResult.getString("bcontents");

				BoardDAO dao = new BoardDAO();
				Boolean success = dao.updateBoard(realBno, btitle, bcontents);

				if (success) {
					jsonResult.put("status", true);
					jsonResult.put("message", "게시글 수정이 완료되었습니다.");
					jsonResult.put("url", "boardView?bno=" + realBno + "&num=" + num + "&page=" + page);
				} else {
					jsonResult.put("status", false);
					jsonResult.put("message", "게시글 수정을 실패했습니다.");
					jsonResult.put("url", "boardView?bno=" + realBno + "&num=" + num + "&page=" + page);
				}

				out.println(jsonResult.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (request.getRequestURI().equals("/webProjectSJ/Board/deleteBoard")) {
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				String page = request.getParameter("page");

				BoardDAO dao = new BoardDAO();
				Boolean success = dao.deleteBoard(bno);
				JSONObject jsonResult = new JSONObject();

				if (success) {
					jsonResult.put("status", success);
					jsonResult.put("message", "게시글이 삭제되었습니다.");
					jsonResult.put("url", "/webProjectSJ/Board/boardList?pageNum=" + page);
				} else {
					jsonResult.put("status", success);
					jsonResult.put("message", "게시글 수정을 실패했습니다.");
					jsonResult.put("url", "/webProjectSJ/Board/boardList?pageNum=" + page);
				}

				out.println(jsonResult.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (request.getRequestURI().equals("/webProjectSJ/Board/myBoardList")) {
			try {
				String user_id = (String) session.getAttribute("login_id");

				BoardDAO dao = new BoardDAO();
				List<BoardVO> list = dao.selectBoardList(user_id);
				
				int pageNum = 1;
				int total = dao.getTotal();
				
				BoardPageVO boardPageVO = new BoardPageVO(pageNum, total);

				request.setAttribute("listBoards", list);
				request.setAttribute("boardPageVO", boardPageVO);

				RequestDispatcher dispatch = request.getRequestDispatcher("../page/BoardListPage.jsp");
				dispatch.forward(request, response);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
