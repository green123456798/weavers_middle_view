package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.MemberDTO;
import model.util.JDBCUtil;

public class MemberDAO {

	private Connection conn; // DB와의 연결을 담당
	private PreparedStatement pstmt; // CRUD 수행을 담당

	private static final String SELECTALL = "";
	private static final String SELECTONE_LOGIN = "SELECT MID, MPW, NAME, BIRTH,  PHONE, NICKNAME, EMAIL, MARKETING,GPK "
			+ "	FROM MEMBER WHERE MID=? AND MPW=?";
	private static final String SELECTONE_NICKNAME = "SELECT MID, MPW, NAME, BIRTH, PHONE, NICKNAME, EMAIL, MARKETING, GPK "
			+ "   FROM MEMBER WHERE NICKNAME=?";
	private static final String SELECTONE_MEMBER = "SELECT M.MID, M.MPW, M.NAME, M.BIRTH, M.PHONE, M.NICKNAME, M.EMAIL, M.MARKETING, M.GPK, G.GRADE"
			+ "  FROM MEMBER M JOIN GRADE G " + "   ON (M.GPK = G.GPK) WHERE M.MID=?";
	private static final String SELECTONE_REGISTER = "SELECT MID, MPW, NAME, BIRTH, PHONE, NICKNAME, EMAIL, MARKETING, GPK "
			+ "   FROM MEMBER WHERE MID=?";

	private static final String INSERT = "INSERT INTO MEMBER (MID,MPW,NAME,BIRTH,PHONE,NICKNAME,EMAIL,MARKETING) VALUES (?,?,?,?,?,?,?,?)";

	private static final String UPDATE = "UPDATE MEMBER SET EMAIL=?, NICKNAME=?, PHONE=?, MPW=? WHERE MID=?";

	private static final String UPDATE_UNREGISTER = "UPDATE MEMBER SET GPK=5 WHERE MID=?";

	private static final String DELETE = "";

	private ArrayList<MemberDTO> selectAll(MemberDTO mDTO) {
		return null;
	}

	public MemberDTO selectOne(MemberDTO mDTO) {
		MemberDTO data = null;

		conn = JDBCUtil.connect();
		try {

			if (mDTO.getSearchCondition().equals("로그인")) {
				pstmt = conn.prepareStatement(SELECTONE_LOGIN);
				pstmt.setString(1, mDTO.getMid());
				pstmt.setString(2, mDTO.getMpw());

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					data = new MemberDTO();

					data.setMid(rs.getString("MID"));
					data.setMpw(rs.getString("MPW"));
					data.setName(rs.getString("NAME"));
					// System.out.println("[로그 1] rs ==="+ rs.getString("BIRTH"));

					data.setBirth(rs.getDate("BIRTH"));
					data.setPhone(rs.getString("PHONE"));
					data.setNickname(rs.getString("NICKNAME"));
					data.setEmail(rs.getString("EMAIL"));
					data.setMarketing(rs.getInt("MARKETING"));
					data.setGpk(rs.getInt("GPK"));

				}

				rs.close();
			} else if (mDTO.getSearchCondition().equals("닉네임중복체크")) {

				pstmt = conn.prepareStatement(SELECTONE_NICKNAME);
				pstmt.setString(1, mDTO.getNickname());

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					data = new MemberDTO();
					data.setMid(rs.getString("MID"));
					data.setMpw(rs.getString("MPW"));
					data.setName(rs.getString("NAME"));
					data.setBirth(rs.getDate("BIRTH"));
					data.setPhone(rs.getString("PHONE"));
					data.setNickname(rs.getString("NICKNAME"));
					data.setEmail(rs.getString("EMAIL"));
					data.setMarketing(rs.getInt("MARKETING"));
					data.setGpk(rs.getInt("GPK"));
				}
				rs.close();
			} else if (mDTO.getSearchCondition().equals("회원정보")) {
				pstmt = conn.prepareStatement(SELECTONE_MEMBER);
				pstmt.setString(1, mDTO.getMid());

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {

					data = new MemberDTO();
					data.setMid(rs.getString("MID"));
					data.setMpw(rs.getString("MPW"));
					data.setName(rs.getString("NAME"));
					data.setBirth(rs.getDate("BIRTH"));
					data.setPhone(rs.getString("PHONE"));
					data.setNickname(rs.getString("NICKNAME"));
					data.setEmail(rs.getString("EMAIL"));
					data.setMarketing(rs.getInt("MARKETING"));
					data.setGpk(rs.getInt("GPK"));
					data.setGrade(rs.getString("GRADE"));
				}
				rs.close();
			} else if (mDTO.getSearchCondition().equals("ID중복검사")) {

				pstmt = conn.prepareStatement(SELECTONE_REGISTER);
				pstmt.setString(1, mDTO.getMid());

				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					data = new MemberDTO();
					data.setMid(rs.getString("MID"));
					data.setMpw(rs.getString("MPW"));
					data.setName(rs.getString("NAME"));
					data.setBirth(rs.getDate("BIRTH"));
					data.setPhone(rs.getString("PHONE"));
					data.setNickname(rs.getString("NICKNAME"));
					data.setEmail(rs.getString("EMAIL"));
					data.setMarketing(rs.getInt("MARKETING"));
					data.setGpk(rs.getInt("GPK"));
				}
				rs.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return data;
	}

	public boolean insert(MemberDTO mDTO) {
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(INSERT);
			pstmt.setString(1, mDTO.getMid());
			pstmt.setString(2, mDTO.getMpw());
			pstmt.setString(3, mDTO.getName());
			// System.out.println("[로그1] 생일 : " + mDTO.getBirth() );
			pstmt.setDate(4, new java.sql.Date(mDTO.getBirth().getTime()));
			pstmt.setString(5, mDTO.getPhone());
			pstmt.setString(6, mDTO.getNickname());
			pstmt.setString(7, mDTO.getEmail());
			pstmt.setInt(8, mDTO.getMarketing());
			int rs = pstmt.executeUpdate();
			if (rs <= 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean update(MemberDTO mDTO) {

		conn = JDBCUtil.connect();
		try {
			if (mDTO.getSearchCondition().equals("정보수정")) {
				pstmt = conn.prepareStatement(UPDATE);
				pstmt.setString(1, mDTO.getEmail());
				pstmt.setString(2, mDTO.getNickname());
				pstmt.setString(3, mDTO.getPhone());
				pstmt.setString(4, mDTO.getMpw());
				pstmt.setString(5, mDTO.getMid());
			} else {
				pstmt = conn.prepareStatement(UPDATE_UNREGISTER);
				pstmt.setString(1, mDTO.getMid());
			}

			int rs = pstmt.executeUpdate();
			if (rs <= 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	private boolean delete(MemberDTO mDTO) {

		return false;
	}

}