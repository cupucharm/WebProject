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

	public MemberDAO() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/webproSJDB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원가입
	public int insertMember(MemberVO memberVO) throws SQLException {
		try {
			Connection con = dataFactory.getConnection();
			String query = "insert into tb_member(user_id, user_name, user_pwd, user_phone, user_email, user_sex, user_birth) values(?,?,?,?,?,?,?)";

			pstmt = con.prepareStatement(query);
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
				pstmt.close();
				conn.close();
			} catch (Exception e) {

			}
		}
	}

	// 로그인 : 회원 존재 여부 확인 && 비밀번호 일치 하는지
	public MemberVO isExisted(String user_id, String user_pwd) {
		MemberVO member = null;
		try {
			conn = dataFactory.getConnection();

			String query = "select * from tb_member where user_id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();

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
		}
		return member;
	}

	// 관리자 위한 회원 목록 가져오기
	public List<MemberVO> listMembers() {
		List<MemberVO> list = new ArrayList();
		try {
			conn = dataFactory.getConnection();

			String query = "select * from tb_member";

			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberVO member = new MemberVO(rs.getString("user_id"), rs.getString("user_name"),
						rs.getString("user_pwd"), rs.getString("user_phone"), rs.getString("user_email"),
						rs.getString("user_sex"), rs.getString("user_birth"), rs.getString("user_condition"));
				list.add(member);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 마이페이지
	public MemberVO memberInfo(String user_id) {
		try {
			conn = dataFactory.getConnection();

			String query = "select * from tb_member where user_id =?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				MemberVO member = new MemberVO(rs.getString("user_id"), rs.getString("user_name"),
						rs.getString("user_pwd"), rs.getString("user_phone"), rs.getString("user_email"),
						rs.getString("user_sex"), rs.getString("user_birth"), rs.getString("user_condition"));
				return member;
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// id 중복 확인
	public MemberVO checkMember(String user_id) {
		try {
			conn = dataFactory.getConnection();
			String query = "select * from tb_member where user_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();
			MemberVO memberVO = null;

			if (rs.next()) {
				memberVO = new MemberVO(rs.getString("user_id"), rs.getString("user_name"), rs.getString("user_pwd"),
						rs.getString("user_phone"), rs.getString("user_email"), rs.getString("user_sex"),
						rs.getString("user_birth"), rs.getString("user_condition"));
			}
			rs.close();

			return memberVO;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	// id 찾기
	public String searchId(String user_name, String user_phone) {
		String user_id = null;

		try {
			conn = dataFactory.getConnection();
			String query = "select user_id from tb_member where user_name = ? and user_phone=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_name);
			pstmt.setString(2, user_phone);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user_id = rs.getString("user_id");
			}
			rs.close();
			return user_id;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
			}
		}
		return user_id;
	}

	// 비밀번호 찾기
	public String searchPw(String user_id, String user_name, String user_phone) {
		String user_pwd = null;

		try {
			conn = dataFactory.getConnection();
			String query = "select user_pwd from tb_member where user_id = ? and user_name = ? and user_phone=?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_name);
			pstmt.setString(3, user_phone);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user_pwd = rs.getString("user_pwd");
			}
			rs.close();
			return user_pwd;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
			}
		}
		return user_pwd;
	}

	// 회원 정보 수정
	public boolean updateMember(String user_pwd, String user_name, String user_phone, String user_email,
			String user_id) {
		int num = 0;

		try {
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
				pstmt.close();
				conn.close();
			} catch (Exception e) {
			}
		}
		return num > 0;
	}

	// 회원정보 변경 pwd 확인
	public String getPwd(String user_id) {
		String user_pwd = null;

		try {
			conn = dataFactory.getConnection();
			String query = "select user_pwd from tb_member where user_id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user_id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				user_pwd = rs.getString("user_pwd");
			}
			rs.close();
			return user_pwd;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (Exception e) {
			}
		}
		return user_pwd;
	}

	// 회원탈퇴
	public Boolean deleteMember(String user_id, String user_pwd) {
		Boolean result = false;
		try {
			Connection con = dataFactory.getConnection();
			String query = "delete from tb_member where user_id=? and user_pwd=?";

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, user_id);
			pstmt.setString(2, user_pwd);
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
}
