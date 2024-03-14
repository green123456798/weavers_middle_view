package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.CartDTO;
import model.util.JDBCUtil;

public class CartDAO {
	
	private Connection conn; // DB와의 연결을 담당
	private PreparedStatement pstmt; // CRUD 수행을 담당

	private static final String SELECTALL = "SELECT C.CPK, C.MID, C.PPK, C.CNT, P.PNAME, P.PRICE, P.IMG FROM CART C JOIN PRODUCT P ON (C.PPK=P.PPK) WHERE C.MID=?";
	private static final String SELECTONE = "SELECT C.CPK, C.MID, C.PPK, C.CNT, P.PNAME, P.PRICE, P.IMG FROM CART C JOIN PRODUCT P ON (C.PPK=P.PPK) WHERE C.MID=? AND C.PPK=?";

	private static final String INSERT = "INSERT INTO CART (CPK, MID, PPK, CNT) VALUES ((SELECT NVL(MAX(CPK),0)+1 FROM CART),?,?,?)";
	private static final String UPDATE_ADD = "UPDATE CART SET CNT=CNT+? WHERE MID=? AND PPK=?";
	private static final String UPDATE_UP = "UPDATE CART SET CNT=CNT+1 WHERE MID=? AND PPK=?";
	private static final String UPDATE_DOWN = "UPDATE CART SET CNT=CNT-1 WHERE MID=? AND PPK=?";
	
	private static final String DELETE_ONE = "DELETE FROM CART WHERE MID=? AND PPK=?";
	private static final String DELETE_ALL = "DELETE FROM CART WHERE MID=?";
	
	public ArrayList<CartDTO> selectAll(CartDTO cDTO) {
		ArrayList<CartDTO> datas=new ArrayList<CartDTO>();

		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(SELECTALL);
			pstmt.setString(1, cDTO.getMid());

			ResultSet rs=pstmt.executeQuery();

			while(rs.next()) {
				CartDTO data=new CartDTO();
				data.setCpk(rs.getInt("CPK"));
				data.setMid(rs.getString("MID"));
				data.setCnt(rs.getInt("CNT"));
				data.setPpk(rs.getInt("PPK"));
				data.setPname(rs.getString("PNAME"));
				data.setPrice(rs.getInt("PRICE"));
				data.setImg(rs.getString("IMG"));
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
	public CartDTO selectOne(CartDTO cDTO) {
		CartDTO data = null;

		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(SELECTONE);
			pstmt.setString(1, cDTO.getMid());
			pstmt.setInt(2, cDTO.getPpk());
			
			//System.out.println("없는 상품 user : "+cDTO.getMid());
			//System.out.println("없는 상품 번호 : "+cDTO.getPpk());
			

			ResultSet rs=pstmt.executeQuery();
			
			if(rs.next()) {
				data = new CartDTO();
				data.setCpk(rs.getInt("CPK"));
				data.setMid(rs.getString("MID"));
				data.setCnt(rs.getInt("CNT"));
				data.setPpk(rs.getInt("PPK"));
				data.setPname(rs.getString("PNAME"));
				data.setPrice(rs.getInt("PRICE"));
				data.setImg(rs.getString("IMG"));
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
	
	public boolean insert(CartDTO cDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, cDTO.getMid());
			pstmt.setInt(2, cDTO.getPpk());
			pstmt.setInt(3, cDTO.getCnt());
			
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
	
	public boolean update(CartDTO cDTO) {
		conn=JDBCUtil.connect();
		try {
			
			if(cDTO.getSearchCondition().equals("수량증가")) {
				pstmt=conn.prepareStatement(UPDATE_UP);
				
				pstmt.setString(1, cDTO.getMid());
				pstmt.setInt(2, cDTO.getPpk());
			} else if(cDTO.getSearchCondition().equals("수량감소")) {
				pstmt=conn.prepareStatement(UPDATE_DOWN);
				
				pstmt.setString(1, cDTO.getMid());
				pstmt.setInt(2, cDTO.getPpk());
			} else {
				pstmt=conn.prepareStatement(UPDATE_ADD);
				
				pstmt.setInt(1, cDTO.getCnt());
				pstmt.setString(2, cDTO.getMid());
				pstmt.setInt(3, cDTO.getPpk());
			}
			
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
	public boolean delete(CartDTO cDTO) {
		conn=JDBCUtil.connect();
		try {
			
			if(cDTO.getSearchCondition().equals("요소삭제")) {
				pstmt=conn.prepareStatement(DELETE_ONE);
				pstmt.setString(1, cDTO.getMid());
				pstmt.setInt(2, cDTO.getPpk());
			}
			else if(cDTO.getSearchCondition().equals("전체삭제")) {
				pstmt=conn.prepareStatement(DELETE_ALL);
				pstmt.setString(1, cDTO.getMid());
			}
			
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
