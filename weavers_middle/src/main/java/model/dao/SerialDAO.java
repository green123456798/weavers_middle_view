package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.SerialDTO;
import model.util.JDBCUtil;

public class SerialDAO {
	
	private Connection conn; // DB와의 연결을 담당
	private PreparedStatement pstmt; // CRUD 수행을 담당

	private static final String SELECTALL = "";
	private static final String SELECTONE = "";

	private static final String INSERT = "INSERT INTO SERIAL (SPK, MID, DELIVERYADDRESS ) VALUES ((SELECT NVL(MAX(SPK),0)+1 FROM SERIAL), ?, ?)";
	private static final String UPDATE = "";
	private static final String DELETE = "";
	
	private ArrayList<SerialDTO> selectAll(SerialDTO sDTO) {
		return null;
	}
	
	private SerialDTO selectOne(SerialDTO sDTO) {
		return null;
	}
	
	public boolean insert(SerialDTO sDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setString(1, sDTO.getMid());
			pstmt.setString(2, sDTO.getDeliveryAddress());
			
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
	
	private boolean update(SerialDTO sDTO) {
		
		return false;
	}
	private boolean delete(SerialDTO sDTO) {
		
		return false;
	}

}
