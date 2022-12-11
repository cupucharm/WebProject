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

import Member.MemberVO;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public BoardDAO() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/webproSJDB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 게시글 작성
	public boolean insertBoard(String btitle, String bwriter, String bcategory, String bcontents) throws SQLException {
		try {
			Connection con = dataFactory.getConnection();
			String query = "insert into tb_board(btitle, bwriter, bcontents, bcategory) values(?,?,?,?)";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, btitle);
			pstmt.setString(2, bwriter);
			pstmt.setString(3, bcontents);
			pstmt.setString(4, bcategory);

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {

			}
		}
	}

	// 게시글 목록 가져오기
	public List<BoardVO> listBoards(int pageNum) {
		List<BoardVO> list = new ArrayList<>();
		try {
			conn = dataFactory.getConnection();

			String query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_board order by rowNum desc) t limit 10 offset ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pageNum * 10 - 10);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVO board = new BoardVO(rs.getInt("bno"), rs.getString("btitle"), rs.getString("bwriter"),
						rs.getString("bdate"), rs.getInt("bhit"), rs.getString("bcontents"), rs.getString("bcategory"));
				list.add(board);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getTotal() {
		int result = 0;

		try {
			conn = dataFactory.getConnection();
			String query = "select count(*) as total from tb_board";
			pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("total");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// 제목 눌렀을 때 bno에 해당하는 객체 만들어 오기
	public BoardVO getBoardVO(int bno) throws SQLException {
		BoardVO board = null;

		try {
			conn = dataFactory.getConnection();

			String query = "SELECT * from tb_board where bno = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bno);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				board = new BoardVO(rs.getInt("bno"), rs.getString("btitle"), rs.getString("bwriter"),
						rs.getString("bdate"), rs.getInt("bhit"), rs.getString("bcontents"), rs.getString("bcategory"));
//				return board;
			}
			rs.close();
			return board;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		// return board;
	}

	public Boolean updateBoard(int realBno, String btitle, String bcontents) {
		Boolean success = false;
		try {
			conn = dataFactory.getConnection();

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
				pstmt.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		return success;
	}

	public Boolean deleteBoard(int bno) {
		Boolean result = false;
		try {
			Connection con = dataFactory.getConnection();
			String query = "delete from tb_board where bno=?";

			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bno);
			result = pstmt.executeUpdate() > 0;

			return result;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		return result;
	}

	public boolean countUp(int bno) {
		Boolean success = false;
		try {
			conn = dataFactory.getConnection();

			String query = "update tb_board set bhit=bhit+1 where bno=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bno);

			success = pstmt.executeUpdate() > 0;

			return success;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {

			}
		}
		return success;
	}

	public List<BoardVO> selectBoardList(String user_id) {
		List<BoardVO> list = new ArrayList();
		try {
			conn = dataFactory.getConnection();

			String query = "select * from tb_board where bwriter=?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  user_id);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVO board = new BoardVO(rs.getInt("bno"), rs.getString("btitle"),
						rs.getString("bwriter"), rs.getString("bdate"), rs.getInt("bhit"),
						rs.getString("bcontents"), rs.getString("bcategory"));
				list.add(board);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// id를 검색해서 게시글 가져오기
//	public MemberVO viewMember(String user_id) {
//		try {
//			// connDB();
//			conn = dataFactory.getConnection();
//			String query = "select * from tb_member where bwriter = ?";
//			
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, user_id);
//			ResultSet rs = pstmt.executeQuery();
//			MemberVO memberVO = null;
//			
//			if (rs.next()) {
//				memberVO = new MemberVO(
//						rs.getString("id"),	
//						rs.getString("pwd"),	
//						rs.getString("name"),	
//						rs.getString("email"),	
//						rs.getDate("joinDate"));
//			}
//			rs.close();
//			
//			return memberBean;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				pstmt.close();
//				conn.close();
//			} catch (Exception e) {}
//		}
//		return null;	
//	}
}
