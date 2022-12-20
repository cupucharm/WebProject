package Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	private ResultSet rs;

	private void open() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/webproSJDB");
			conn = dataFactory.getConnection();
			// conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 게시글 작성
	public int insertBoard(String btitle, String bwriter, String bcategory, String bcontents, int parentNo)
			throws SQLException {
		try {
			open();
			String query = "insert into tb_board(btitle, bwriter, bcontents, bcategory, bparentNo) values(?,?,?,?,?)";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, btitle);
			pstmt.setString(2, bwriter);
			pstmt.setString(3, bcontents);
			pstmt.setString(4, bcategory);
			pstmt.setInt(5, parentNo);

			pstmt.executeUpdate();

			pstmt.close();

			query = "select LAST_INSERT_ID()";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			int number = 0;
			if (rs.next()) {
				number = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			// conn.commit();

			return number;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 게시글 목록 가져오기
	public List<BoardVO> listBoards(int pageNum) {
		List<BoardVO> list = new ArrayList<>();
		try {
			open();

			String query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_board order by bparentNo desc, bno) t limit 10 offset ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pageNum * 10 - 10);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVO board = new BoardVO(rs.getInt("bno"), rs.getString("btitle"), rs.getString("bwriter"),
						rs.getString("bdate"), rs.getInt("bhit"), rs.getString("bcontents"), rs.getString("bcategory"),
						rs.getInt("bparentNo"), rs.getInt("blikecount"), rs.getInt("bdislikecount"));
				list.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int getTotal() {
		int result = 0;

		try {
			open();
			String query = "select count(*) as total from tb_board";
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 제목 눌렀을 때 bno에 해당하는 객체 만들어 오기
	public BoardVO getBoardVO(int bno) throws SQLException {
		BoardVO board = null;

		try {
			open();

			String query = "SELECT * from tb_board where bno = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bno);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				board = new BoardVO(rs.getInt("bno"), rs.getString("btitle"), rs.getString("bwriter"),
						rs.getString("bdate"), rs.getInt("bhit"), rs.getString("bcontents"), rs.getString("bcategory"),
						rs.getInt("bparentNo"), rs.getInt("blikecount"), rs.getInt("bdislikecount"));
			}
			return board;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean updateBoard(int realBno, String btitle, String bcontents) {
		Boolean success = false;
		try {
			open();

			String query = "update tb_board set btitle =?, bcontents =?, bdate=now() where bno=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, btitle);
			pstmt.setString(2, bcontents);
			pstmt.setInt(3, realBno);

			success = pstmt.executeUpdate() > 0;

			return success;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	public Boolean deleteBoard(int bno) {
		Boolean result = false;
		try {
			open();
			String query = "delete from tb_board where bno=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bno);
			result = pstmt.executeUpdate() > 0;

			return result;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean countUp(int bno) {
		Boolean success = false;
		try {
			open();

			String query = "update tb_board set bhit=bhit+1 where bno=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bno);

			success = pstmt.executeUpdate() > 0;

			return success;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	public List<BoardVO> selectMyBoardList(String user_id) {
		List<BoardVO> list = new ArrayList();
		try {
			open();

			String query = "select * from tb_board where bwriter=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVO board = new BoardVO(rs.getInt("bno"), rs.getString("btitle"), rs.getString("bwriter"),
						rs.getString("bdate"), rs.getInt("bhit"), rs.getString("bcontents"), rs.getString("bcategory"),
						rs.getInt("bparentNo"), rs.getInt("blikecount"), rs.getInt("bdislikecount"));
				list.add(board);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int getTotalByCategory(String content) {
		int result = 0;

		try {
			open();

			String query = "select count(*) as total from tb_board where bcategory=?";
			pstmt = conn.prepareStatement(query);

			switch (content) {

			case "notice":
				pstmt.setString(1, "공지사항");
				break;

			case "commom":
				pstmt.setString(1, "일반게시판");
				break;

			case "Question":
				pstmt.setString(1, "Q&A");
				break;

			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<BoardVO> listBoardsCategory(int pageNum, String content) {
		List<BoardVO> list = new ArrayList<>();
		try {
			open();

			String query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_board order by bparentNo desc, bno) t where bcategory=? limit 10 offset ?";

			pstmt = conn.prepareStatement(query);

			switch (content) {

			case "notice":
				pstmt.setString(1, "공지사항");
				break;

			case "commom":
				pstmt.setString(1, "일반게시판");
				break;

			case "Question":
				pstmt.setString(1, "Q&A");
				break;

			}
			pstmt.setInt(2, pageNum * 10 - 10);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVO board = new BoardVO(rs.getInt("bno"), rs.getString("btitle"), rs.getString("bwriter"),
						rs.getString("bdate"), rs.getInt("bhit"), rs.getString("bcontents"), rs.getString("bcategory"),
						rs.getInt("bparentNo"), rs.getInt("blikecount"), rs.getInt("bdislikecount"));
				list.add(board);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int getTotalBySearch(String searchCondition, String searchContent) {
		int result = 0;

		try {
			open();

			String query = null;

			switch (searchCondition) {
			case "titleAndContents":
				query = "select count(*) as total from tb_board where bcontents like ? or btitle like ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchContent + "%");
				pstmt.setString(2, "%" + searchContent + "%");
				break;

			case "title":
				query = "select count(*) as total from tb_board where btitle like ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchContent + "%");
				break;

			case "contents":
				query = "select count(*) as total from tb_board where bcontents like ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchContent + "%");
				break;

			case "writer":
				query = "select count(*) as total from tb_board where bwriter like ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchContent + "%");
				break;

			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<BoardVO> listBoardsSearch(int pageNum, String searchCondition, String searchContent) {
		List<BoardVO> list = new ArrayList<>();
		try {
			open();

			String query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_board order by bparentNo desc, bno) t where ?=? limit 10 offset ?";

			pstmt = conn.prepareStatement(query);

			switch (searchCondition) {
			case "titleAndContents":
				query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_board order by bparentNo desc, bno) t where bcontents like ? or btitle like ? limit 10 offset ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchContent + "%");
				pstmt.setString(2, "%" + searchContent + "%");
				pstmt.setInt(3, pageNum * 10 - 10);
				break;

			case "title":
				query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_board order by bparentNo desc, bno) t where btitle like ? limit 10 offset ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchContent + "%");
				pstmt.setInt(2, pageNum * 10 - 10);
				break;

			case "contents":
				query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_board order by bparentNo desc, bno) t where bcontents like ? limit 10 offset ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchContent + "%");
				pstmt.setInt(2, pageNum * 10 - 10);
				break;

			case "writer":
				query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_board order by bparentNo desc, bno) t where bwriter like ? limit 10 offset ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "%" + searchContent + "%");
				pstmt.setInt(2, pageNum * 10 - 10);
				break;

			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVO board = new BoardVO(rs.getInt("bno"), rs.getString("btitle"), rs.getString("bwriter"),
						rs.getString("bdate"), rs.getInt("bhit"), rs.getString("bcontents"), rs.getString("bcategory"),
						rs.getInt("bparentNo"), rs.getInt("blikecount"), rs.getInt("bdislikecount"));
				list.add(board);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public int getParentNo() {
		try {
			open();
			String query = "select MAX(bno) from tb_board";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return (rs.getInt(1) + 1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

}