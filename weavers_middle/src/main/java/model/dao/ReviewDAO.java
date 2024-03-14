package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.dto.ReviewDTO;
import model.util.JDBCUtil;

public class ReviewDAO {
	
	private Connection conn; // DB와의 연결을 담당
	private PreparedStatement pstmt; // CRUD 수행을 담당

	private static final String SELECTALL_MAIN = "SELECT R.RPK, R.CONTENT, R.SCOPE, R.IMG, R.REGDATE, M.NICKNAME, M.GPK FROM REVIEW R JOIN MEMBER M ON(R.MID = M.MID) JOIN BUYPRODUCT B ON (R.BPK = B.BPK) WHERE B.PPK=? ORDER BY R.REGDATE DESC";
	private static final String SELECTALL_MY = "SELECT B.BPK, P.PPK, P.PNAME, P.IMG PIMG, R.RPK, R.CONTENT, R.SCOPE, R.IMG, R.REGDATE FROM REVIEW R JOIN BUYPRODUCT B ON (R.BPK = B.BPK) JOIN PRODUCT P ON (B.PPK = P.PPK) WHERE R.MID=? ORDER BY R.REGDATE DESC";
	
	private static final String SELECTONE = "SELECT R.RPK, P.PPK, P.PNAME, P.IMG PIMG, R.CONTENT, R.SCOPE, R.IMG, R.REGDATE FROM REVIEW R JOIN BUYPRODUCT B ON (R.BPK = B.BPK) JOIN PRODUCT P ON (B.PPK = P.PPK) WHERE R.BPK = ?";
	
	private static final String INSERT = "INSERT INTO REVIEW (RPK,MID,BPK,CONTENT,SCOPE,IMG)"
											+ " VALUES ((SELECT NVL(MAX(RPK),0)+1 FROM REVIEW),?,?,?,?,?)";
	private static final String UPDATE = "UPDATE REVIEW SET CONTENT=?, SCOPE=?, IMG=? WHERE RPK=?";
	private static final String DELETE = "";

	public ArrayList<ReviewDTO> selectAll(ReviewDTO rDTO) {
		ArrayList<ReviewDTO> datas = new ArrayList<ReviewDTO>();

		conn=JDBCUtil.connect();
		try {
			
			if(rDTO.getSearchCondition().equals("메인리뷰출력")) {
				pstmt=conn.prepareStatement(SELECTALL_MAIN);
				pstmt.setInt(1, rDTO.getPpk());

				ResultSet rs=pstmt.executeQuery();

				while(rs.next()) {
					ReviewDTO data=new ReviewDTO();
					data.setRpk(rs.getInt("RPK"));
					data.setContent(rs.getString("CONTENT"));
					data.setScope(rs.getInt("SCOPE"));
					data.setImg(rs.getString("IMG"));
					data.setRegdate(rs.getDate("REGDATE"));
					data.setNickname(rs.getString("NICKNAME"));
					data.setGpk(rs.getInt("GPK"));
					
					datas.add(data);
				}
				rs.close();
			}
			if(rDTO.getSearchCondition().equals("개인리뷰출력")) {
				pstmt=conn.prepareStatement(SELECTALL_MY);
				pstmt.setString(1, rDTO.getMid());

				ResultSet rs=pstmt.executeQuery();

				while(rs.next()) {
					ReviewDTO data=new ReviewDTO();
					data.setBpk(rs.getInt("BPK"));
					data.setRpk(rs.getInt("RPK"));
					data.setPpk(rs.getInt("PPK"));
					data.setPname(rs.getString("PNAME"));
					data.setPimg(rs.getString("PIMG"));
					data.setContent(rs.getString("CONTENT"));
					data.setScope(rs.getInt("SCOPE"));
					data.setImg(rs.getString("IMG"));
					data.setRegdate(rs.getDate("REGDATE"));
					
					datas.add(data);
				}
				System.out.println(datas);
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return datas;
	}
	public ReviewDTO selectOne(ReviewDTO rDTO) {
		
		ReviewDTO data = null;

		conn=JDBCUtil.connect();
		try {
				pstmt=conn.prepareStatement(SELECTONE);
				pstmt.setInt(1, rDTO.getBpk());

				ResultSet rs=pstmt.executeQuery();

				while(rs.next()) {
					data = new ReviewDTO();					
					data.setRpk(rs.getInt("RPK"));
					data.setPpk(rs.getInt("PPK"));
					data.setPimg(rs.getString("PIMG"));
					data.setPname(rs.getString("PNAME"));
					data.setContent(rs.getString("CONTENT"));
					data.setScope(rs.getInt("SCOPE"));
					data.setImg(rs.getString("IMG"));
					data.setRegdate(rs.getDate("REGDATE"));
					
				}
				rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return data;
	}

	public boolean insert(ReviewDTO rDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, rDTO.getMid());
			pstmt.setInt(2, rDTO.getBpk());
			pstmt.setString(3, rDTO.getContent());
			pstmt.setInt(4, rDTO.getScope());
			pstmt.setString(5, rDTO.getImg());
			 
			
			int rs=pstmt.executeUpdate();
			if(rs<=0) {
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
	public boolean update(ReviewDTO rDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(UPDATE);
			pstmt.setString(1, rDTO.getContent());
			pstmt.setInt(2, rDTO.getScope());
			pstmt.setString(3, rDTO.getImg());
			pstmt.setInt(4, rDTO.getRpk());
			
			int rs=pstmt.executeUpdate();
			if(rs<=0) {
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
	public boolean delete(ReviewDTO rDTO) {
		
		return false;
	}
	
	
}
