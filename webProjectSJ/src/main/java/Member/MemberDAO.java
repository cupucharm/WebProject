package Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	private ResultSet rs;

	private void open() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/webproSJDB");
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

	// 회원가입
	public int insertMember(MemberVO memberVO) throws SQLException {
		try {
			open();
			conn = dataFactory.getConnection();
			String query = "insert into tb_member(user_id, user_name, user_pwd, user_phone, user_email, user_sex, user_birth) values(?,?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberVO.getUser_id());
			pstmt.setString(2, memberVO.getUser_name());
			pstmt.setString(3, memberVO.getUser_pwd());
			pstmt.setString(4, memberVO.getUser_phone());
			pstmt.setString(5, memberVO.getUser_email());
			pstmt.setString(6, memberVO.getUser_sex());
			pstmt.setString(7, memberVO.getUser_birth());

			return pstmt.executeUpdate();

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

	// 로그인 : 회원 존재 여부 확인 && 비밀번호 일치 하는지
	public MemberVO isExisted(String user_id, String user_pwd) {
		MemberVO member = null;
		try {
			open();
			conn = dataFactory.getConnection();

			String query = "select * from tb_member where user_id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("user_pwd").equals(user_pwd)) {
					return new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));
				} else
					return member;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return member;
	}

	// 관리자 위한 회원 목록 가져오기
	public List<MemberVO> listMembers() {
		List<MemberVO> list = new ArrayList();
		try {
			open();
			conn = dataFactory.getConnection();

			String query = "select * from tb_member";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberVO member = new MemberVO(rs.getString("user_id"), rs.getString("user_name"),
						rs.getString("user_pwd"), rs.getString("user_phone"), rs.getString("user_email"),
						rs.getString("user_sex"), rs.getString("user_birth"), rs.getString("user_condition"));
				list.add(member);
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

	// 마이페이지
	public MemberVO memberInfo(String user_id) {
		try {
			open();
			conn = dataFactory.getConnection();

			String query = "select * from tb_member where user_id =?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				MemberVO member = new MemberVO(rs.getString("user_id"), rs.getString("user_name"),
						rs.getString("user_pwd"), rs.getString("user_phone"), rs.getString("user_email"),
						rs.getString("user_sex"), rs.getString("user_birth"), rs.getString("user_condition"));
				return member;
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
		return null;
	}

	// id 중복 확인
	public MemberVO checkMember(String user_id) {
		try {
			open();
			conn = dataFactory.getConnection();
			String query = "select * from tb_member where user_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			MemberVO memberVO = null;

			if (rs.next()) {
				memberVO = new MemberVO(rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_pwd"),
						rs.getString("user_phone"), rs.getString("user_email"), rs.getString("user_sex"),
						rs.getString("user_birth"), rs.getString("user_condition"));
			}

			return memberVO;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// id 찾기
	public String searchId(String user_name, String user_phone) {
		String user_id = null;

		try {
			open();
			conn = dataFactory.getConnection();
			String query = "select user_id from tb_member where user_name = ? and user_phone=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_name);
			pstmt.setString(2, user_phone);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_id = rs.getString("user_id");
			}
			return user_id;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user_id;
	}

	// 비밀번호 찾기
	public String searchPw(String user_id, String user_name, String user_phone) {
		String user_pwd = null;

		try {
			open();
			conn = dataFactory.getConnection();
			String query = "select user_pwd from tb_member where user_id = ? and user_name = ? and user_phone=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_name);
			pstmt.setString(3, user_phone);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_pwd = rs.getString("user_pwd");
			}
			return user_pwd;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user_pwd;
	}

	// 회원 정보 수정
	public boolean updateMember(String user_pwd, String user_name, String user_phone, String user_email,
			String user_id) {
		int num = 0;

		try {
			open();
			conn = dataFactory.getConnection();
			String query = "update tb_member set user_pwd =?, user_name =?, user_phone=?, user_email=? where user_id=?";

			System.out.println(user_pwd + " " + user_name + " " + user_phone + " " + user_email + " " + user_id);

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_pwd);
			pstmt.setString(2, user_name);
			pstmt.setString(3, user_phone);
			pstmt.setString(4, user_email);
			pstmt.setString(5, user_id);
			num = pstmt.executeUpdate();
			System.out.println(num);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return num > 0;
	}

	// 회원정보 변경 pwd 확인
	public String getPwd(String user_id) {
		String user_pwd = null;

		try {
			open();
			conn = dataFactory.getConnection();
			String query = "select user_pwd from tb_member where user_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				user_pwd = rs.getString("user_pwd");
			}
			return user_pwd;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return user_pwd;
	}

	// 회원탈퇴
	public Boolean deleteMember(String user_id, String user_pwd) {
		Boolean result = false;
		try {
			open();
			conn = dataFactory.getConnection();
			String query = "delete from tb_member where user_id=? and user_pwd=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pwd);
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

	public List<MemberVO> listMembers(int pageNum) {
		List<MemberVO> list = new ArrayList<>();
		try {
			open();
			conn = dataFactory.getConnection();

			String query = "SELECT * from (SELECT * ,@ROWNUM:=@ROWNUM+1 as rowNum FROM (SELECT @ROWNUM:=0) AS R, tb_member) t where user_condition !='admin' limit 10 offset ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, pageNum * 10 - 10);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberVO member = new MemberVO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));
				list.add(member);
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

	public Boolean isLoginCan(String user_id) {
		try {
			open();
			conn = dataFactory.getConnection();

			String query = "select user_condition from tb_member where user_id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (rs.getString("user_condition").equals("비활성화")) {
					return false;
				} else
					return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public Boolean changeCondition(String user_id, String condition) {
		try {
			open();
			conn = dataFactory.getConnection();

			String updateCondition = null;
			if (condition.equals("비활성화")) {
				updateCondition = "활성화";
			} else if (condition.equals("활성화")) {
				updateCondition = "비활성화";
			}

			String query = "update tb_member set user_condition='" + updateCondition + "' where user_id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			int success = pstmt.executeUpdate();

			return success > 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public String getCondition(String user_id) {
		try {
			open();
			conn = dataFactory.getConnection();

			String query = "select user_condition from tb_member where user_id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getString("user_condition");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Boolean memberAdminDelete(String user_id) {
		try {
			open();
			conn = dataFactory.getConnection();
			String query = "delete from tb_member where user_id=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);

			return pstmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public List<MemberVO> selectByUid(String searchInput) {
		List<MemberVO> list = new ArrayList();
		try {
			open();
			conn = dataFactory.getConnection();
			String query = null;

			query = "select * from tb_member where user_id like ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + searchInput + "%");

			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberVO member = new MemberVO(rs.getString("user_id"), rs.getString("user_name"),
						rs.getString("user_pwd"), rs.getString("user_phone"), rs.getString("user_email"),
						rs.getString("user_sex"), rs.getString("user_birth"), rs.getString("user_condition"));
				list.add(member);
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
}