package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.WishListDTO;
import model.util.JDBCUtil;

public class WishListDAO {
	
	private Connection conn; // DB와의 연결을 담당
	private PreparedStatement pstmt; // CRUD 수행을 담당

//	private static final String SELECTALL = "SELECT W.WPK, W.MID, W.PPK, P.IMG, P.PNAME "
//												+ "FROM WISHLIST W JOIN PRODUCT P "
//												+ "ON (W.PPK=P.PPK) "
//												+ "WHERE MID=?";
	private static final String SELECTALL = "SELECT W.WPK, W.MID, W.PPK, P.IMG, P.PNAME FROM WISHLIST W JOIN PRODUCT P ON (W.PPK=P.PPK) WHERE MID=?";
	private static final String SELECTONE = "SELECT WPK, MID, PPK FROM WISHLIST WHERE MID=? AND PPK=?";

	private static final String INSERT = "INSERT INTO WISHLIST VALUES ((SELECT NVL(MAX(WPK),0)+1 FROM WISHLIST),?,?)";
	private static final String UPDATE = "";
	private static final String DELETE = "DELETE FROM WISHLIST WHERE MID=? AND PPK=?";

	public ArrayList<WishListDTO> selectAll(WishListDTO wDTO) {
		ArrayList<WishListDTO> datas=new ArrayList<WishListDTO>();

		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(SELECTALL);
			pstmt.setString(1, wDTO.getMid());
			ResultSet rs=pstmt.executeQuery();

			while(rs.next()) {
				WishListDTO data=new WishListDTO();
				data.setWpk(rs.getInt("WPK"));
				data.setMid(rs.getString("MID"));
				data.setPpk(rs.getInt("PPK"));
				data.setImg(rs.getString("IMG"));
				data.setPname(rs.getString("PNAME"));
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
	
	public WishListDTO selectOne(WishListDTO wDTO) {
		WishListDTO data = null;

		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(SELECTONE);
			pstmt.setString(1, wDTO.getMid());
			pstmt.setInt(2, wDTO.getPpk());
			
			ResultSet rs=pstmt.executeQuery();

			if(rs.next()) {
				data=new WishListDTO();
				data.setWpk(rs.getInt("WPK"));
				data.setMid(rs.getString("MID"));
				data.setPpk(rs.getInt("PPK"));
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return data;
	}
	
	public boolean insert(WishListDTO wDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, wDTO.getMid());
			pstmt.setInt(2, wDTO.getPpk());
			
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
	
	private boolean update(WishListDTO wDTO) {
		return false;
	}
	
	public boolean delete(WishListDTO wDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(DELETE);
			pstmt.setString(1, wDTO.getMid());
			pstmt.setInt(2, wDTO.getPpk());
			
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
