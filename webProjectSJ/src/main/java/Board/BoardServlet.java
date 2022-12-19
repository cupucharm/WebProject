package Board;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

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
		// PrintWriter out = response.getWriter();

		if (request.getRequestURI().equals("/webProjectSJ/Board/boardList")) {

			int pageNum = 1;

			if (request.getParameter("pageNum") != null) {
				pageNum = Integer.parseInt(request.getParameter("pageNum"));
			}

			BoardDAO boardDAO = new BoardDAO();
			int total = boardDAO.getTotal();

			List<BoardVO> list = boardDAO.listBoards(pageNum);

			PageVO pageVO = new PageVO(pageNum, total);

			request.setAttribute("listBoards", list);
			request.setAttribute("pageVO", pageVO);

			// System.out.println(list.toString());

			RequestDispatcher dispatch = request.getRequestDispatcher("../page/BoardListPage.jsp");
			dispatch.forward(request, response);
		} else if (request.getRequestURI().equals("/webProjectSJ/Board/boardView")) {
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				int num = Integer.parseInt(request.getParameter("num"));
				int page = Integer.parseInt(request.getParameter("page"));

				BoardDAO boardDao = new BoardDAO();
				BoardFileDAO boardFileDao = new BoardFileDAO();
				boardDao.countUp(bno);
				BoardVO boardVO = boardDao.getBoardVO(bno);
				List<BoardFileVO> boardFile = boardFileDao.list(bno);
				request.setAttribute("boardVO", boardVO);
				request.setAttribute("num", num);
				request.setAttribute("page", page);
				request.setAttribute("boardFile", boardFile);
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

			// try {

			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(request.getInputStream(), "UTF-8"));
			// String jsonStr = in.readLine();
			// JSONObject jsonResult = new JSONObject(jsonStr);

			// 저장소 객체 새성
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// 업로드 파일 임시로 저장할 경로 설정
			factory.setRepository(new File("/Users/choisujin/Documents/upload"));

			// 파일 업로드 객체에 저장소 설정
			ServletFileUpload upload = new ServletFileUpload(factory);

			System.out.println(request.getRemoteAddr());
			// 요청 객체를 파싱한다
			try {
				PrintWriter out = response.getWriter();
				// out = response.getWriter();
				Map<String, List<FileItem>> mapItems = upload.parseParameterMap(request);

				String btitle = new String(mapItems.get("btitle").get(0).getString().getBytes("ISO-8859-1"), "utf-8");
				String bwriter = new String(mapItems.get("bwriter").get(0).getString().getBytes("ISO-8859-1"), "utf-8");
				String bcategory = new String(mapItems.get("bcategory").get(0).getString().getBytes("ISO-8859-1"),
						"utf-8");
				String bcontents = new String(mapItems.get("bcontents").get(0).getString().getBytes("ISO-8859-1"),
						"utf-8");

				JSONObject jsonResult = new JSONObject();

				if (btitle != null && btitle.length() > 0 && bcategory != null && bcategory.length() > 0
						&& bcontents != null && bcontents.length() > 0) {
					try {

						BoardDAO dao = new BoardDAO();
						BoardFileDAO boardFileDAO = new BoardFileDAO();

						int number = dao.insertBoard(btitle, bwriter, bcategory, bcontents);

						// for (int i =0; i < mapItems.)
						// 첨부파일 정보 얻어 저장
						for (FileItem fileItem : mapItems.get("filename1")) {
							if (fileItem.getSize() == 0)
								continue;

							// FileItem fileItem = mapItems.get("filename1").get(0);
							String real_name = "/Users/choisujin/Documents/upload/" + System.nanoTime();
							fileItem.write(new File(real_name));

							BoardFileVO boardFile = new BoardFileVO(0, number, fileItem.getName(), real_name,
									fileItem.getContentType(), LocalDate.now().toString(), fileItem.getSize());

							boardFileDAO.insertBoardFile(boardFile);
							
						}
						jsonResult.put("status", true);
						jsonResult.put("message", "게시글이 정상적으로 등록되었습니다.");
						jsonResult.put("url", "/webProjectSJ/Board/boardList");
					} catch (SQLException e) {
						e.printStackTrace();
						jsonResult.put("status", false);
						jsonResult.put("message", "게시글 작성을 실패했습니다.");
					}

				} else {
					jsonResult.put("status", false);
					jsonResult.put("message", "게시글 작성을 실패했습니다. 빈칸을 채워주세요.");
					jsonResult.put("url", "/webProjectSJ/Board/boardList");
				}

				out.println(jsonResult.toString());
			} catch (Exception e) {
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
				PrintWriter out = response.getWriter();
				BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
				String jsonStr = in.readLine();

				JSONObject jsonResult = new JSONObject(jsonStr);
				// System.out.println(jsonResult);

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
				PrintWriter out = response.getWriter();
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
				List<BoardVO> list = dao.selectMyBoardList(user_id);

				int pageNum = 1;
				int total = dao.getTotal();

				PageVO pageVO = new PageVO(pageNum, total);

				request.setAttribute("listBoards", list);
				request.setAttribute("pageVO", pageVO);

				RequestDispatcher dispatch = request.getRequestDispatcher("../page/BoardListPage.jsp");
				dispatch.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (request.getRequestURI().equals("/webProjectSJ/Board/search")) {
			try {

				String searchCondition = request.getParameter("searchCondition");
				String searchContent = request.getParameter("searchContent");

				BoardDAO dao = new BoardDAO();
				int pageNum = 1;

				if (request.getParameter("pageNum") != null) {
					pageNum = Integer.parseInt(request.getParameter("pageNum"));
				}

				List<BoardVO> list = dao.listBoardsSearch(pageNum, searchCondition, searchContent);

				int total = dao.getTotalBySearch(searchCondition, searchContent);

				PageVO pageVO = new PageVO(pageNum, total);

				request.setAttribute("listBoards", list);
				request.setAttribute("pageVO", pageVO);
				request.setAttribute("searchCondition", searchCondition);
				request.setAttribute("searchContent", searchContent);

				RequestDispatcher dispatch = request.getRequestDispatcher("../page/BoardListSearchPage.jsp");
				dispatch.forward(request, response);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (request.getRequestURI().equals("/webProjectSJ/Board/view")) {
			try {

				String content = request.getParameter("content");

				if (!content.equals("all")) {
					BoardDAO dao = new BoardDAO();

					int pageNum = 1;

					if (request.getParameter("pageNum") != null) {
						pageNum = Integer.parseInt(request.getParameter("pageNum"));
					}

					List<BoardVO> list = dao.listBoardsCategory(pageNum, content);
					int total = dao.getTotalByCategory(content);

					PageVO pageVO = new PageVO(pageNum, total);

					request.setAttribute("listBoards", list);
					request.setAttribute("category", content);
					request.setAttribute("pageVO", pageVO);

					RequestDispatcher dispatch = request.getRequestDispatcher("../page/BoardListCategoryPage.jsp");
					dispatch.forward(request, response);
				} else {
					response.sendRedirect("/webProjectSJ/Board/boardList");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (request.getRequestURI().equals("/webProjectSJ/Board/download.do")) {

			String realFileName = (String) request.getParameter("realFileName");
			String orgfileName = (String) request.getParameter("orgfileName");
			System.out.println("realFileName = " + realFileName);
			OutputStream out = response.getOutputStream();
			File f = new File(realFileName);

			orgfileName = URLEncoder.encode(orgfileName, "utf-8").replaceAll("[+]", " ");
			
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Content-disposition", "attachment; fileName=" + orgfileName);

			FileInputStream in = new FileInputStream(f);
			byte[] buffer = new byte[1024 * 8];
			while (true) {
				int count = in.read(buffer);
				if (count == -1)
					break;
				out.write(buffer, 0, count);
			}
			in.close();
			out.close();

		}

	}

}
