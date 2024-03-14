package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.AddressDTO;
import model.dto.CartDTO;
import model.util.JDBCUtil;

public class AddressDAO {

	private Connection conn; // DB와의 연결을 담당
	private PreparedStatement pstmt; // CRUD 수행을 담당

	private static final String SELECTALL = "SELECT APK, ZONECODE, JIBUNADDRESS, ROADADDRESS, DETAIL, ANAME FROM ADDRESS WHERE MID = ?";
	private static final String SELECTONE = "SELECT ZONECODE, JIBUNADDRESS, ROADADDRESS, DETAIL, ANAME FROM ADDRESS WHERE APK = ?";

	private static final String INSERT = "INSERT INTO ADDRESS (APK, MID, ZONECODE, JIBUNADDRESS, ROADADDRESS, DETAIL, ANAME) VALUES ((SELECT NVL(MAX(APK),0)+1 FROM ADDRESS),?, ?, ?, ?, ?, ?)";
													
	private static final String UPDATE = "UPDATE ADDRESS SET ZONECODE=?, JIBUNADDRESS=?, ROADADDRESS=?, DETAIL=?, ANAME=? WHERE APK=?";
	private static final String DELETE = "DELETE FROM ADDRESS WHERE APK=?";

	public ArrayList<AddressDTO> selectAll(AddressDTO aDTO) {
		ArrayList<AddressDTO> datas = new ArrayList<AddressDTO>();

		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(SELECTALL);
			pstmt.setString(1, aDTO.getMid());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				AddressDTO data = new AddressDTO();
				data.setApk(rs.getInt("APK"));
				data.setZonecode(rs.getString("ZONECODE"));
				data.setJibunaddress(rs.getString("JIBUNADDRESS"));
				data.setRoadaddress(rs.getString("ROADADDRESS"));
				data.setDetail(rs.getString("DETAIL"));
				data.setAname(rs.getString("ANAME"));
				datas.add(data);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return datas;
	}
	
	public AddressDTO selectOne(AddressDTO aDTO) {
		
		AddressDTO data = null;

		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(SELECTONE);
			pstmt.setInt(1, aDTO.getApk());
			
			ResultSet rs=pstmt.executeQuery();
			
			if(rs.next()) {
				data = new AddressDTO();
				data.setZonecode(rs.getString("ZONECODE"));
				data.setJibunaddress(rs.getString("JIBUNADDRESS"));
				data.setRoadaddress(rs.getString("ROADADDRESS"));
				data.setDetail(rs.getString("DETAIL"));
				data.setAname(rs.getString("ANAME"));
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return data;
	}
	public boolean insert(AddressDTO aDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, aDTO.getMid());
			pstmt.setString(2, aDTO.getZonecode());
			pstmt.setString(3, aDTO.getJibunaddress());
			pstmt.setString(4, aDTO.getRoadaddress());
			pstmt.setString(5, aDTO.getDetail());
			pstmt.setString(6, aDTO.getAname());

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
	
	public boolean update(AddressDTO aDTO) {
		//UPDATE ADDRESS SET ZONECODE=?, JIBUNADDRESS=?, ROADADDRESS=?, DETAIL=?, ADDRESSNAME=? WHERE APK=?"
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(UPDATE);
			pstmt.setString(1, aDTO.getZonecode());
			pstmt.setString(2, aDTO.getJibunaddress());
			pstmt.setString(3, aDTO.getRoadaddress());
			pstmt.setString(4, aDTO.getDetail());
			pstmt.setString(5, aDTO.getAname());
			pstmt.setInt(6, aDTO.getApk());
			
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
	
	public boolean delete(AddressDTO aDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(DELETE);
			pstmt.setInt(1, aDTO.getApk());

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

}
