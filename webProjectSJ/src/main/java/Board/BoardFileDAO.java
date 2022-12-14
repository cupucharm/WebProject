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

public class BoardFileDAO {
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
			//conn.setAutoCommit(false);
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

	// 게시물에 속한 첨부파일 목록
	public List<BoardFileVO> list(int fbno) {
		List<BoardFileVO> list = new ArrayList<>();
		try {
			open();

			String query = "SELECT * from tb_board_file where fbno =?";
			

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fbno);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardFileVO boardFile = new BoardFileVO(rs.getInt("fid"), rs.getInt("fbno"), rs.getString("forg_name"),
						rs.getString("freal_name"), rs.getString("fcontent_type"), rs.getString("fdate"),
						rs.getLong("flength"));
				list.add(boardFile);
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

	// 게시글 작성
	public void insertBoardFile(BoardFileVO boardFile) throws SQLException {

		open();
		String query = "insert into tb_board_file(fbno, forg_name, freal_name, fcontent_type, flength) values(?,?,?,?,?)";

		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, boardFile.getFbno());
		pstmt.setString(2, boardFile.getForg_name());
		pstmt.setString(3, boardFile.getFreal_name());
		pstmt.setString(4, boardFile.getFcontent_type());
		pstmt.setLong(5, boardFile.getFlength());
		pstmt.executeUpdate();
		close();
		//conn.commit();

	}

}