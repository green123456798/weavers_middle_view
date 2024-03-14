package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.BuyProductDTO;
import model.util.JDBCUtil;

public class BuyProductDAO {
	
	private Connection conn; // DB와의 연결을 담당
	private PreparedStatement pstmt; // CRUD 수행을 담당

	private static final String SELECTALL = "SELECT B.BPK, B.CNT, S.SPK, S.REGDATE, S.DELIVERYADDRESS, P.PNAME, P.PRICE, P.IMG, P.PPK, NVL2(R.RPK, 1, 0) REVIEWCHECK FROM BUYPRODUCT B JOIN SERIAL S ON(B.SPK= S.SPK) JOIN PRODUCT P ON(B.PPK=P.PPK) LEFT JOIN REVIEW R ON (B.BPK = R.BPK) WHERE S.MID=? ORDER BY B.SPK DESC";
												
	private static final String SELECTALL_RECENT = "SELECT B.CNT, S.SPK, S.REGDATE, P.PNAME, P.PRICE, P.IMG, P.PPK FROM SERIAL S JOIN BUYPRODUCT B ON(B.SPK= S.SPK) JOIN PRODUCT P ON(B.PPK=P.PPK) WHERE S.MID= ? AND S.SPK = (SELECT MAX(SPK) FROM SERIAL)";
	
	private static final String SELECTONE = "SELECT B.CNT, S.SPK, S.REGDATE, P.PNAME, P.PRICE, P.IMG, P.PPK FROM BUYPRODUCT B JOIN SERIAL S ON B.SPK=S.SPK JOIN PRODUCT P ON B.PPK=P.PPK WHERE B.BPK = ?";

	private static final String INSERT = "INSERT INTO BUYPRODUCT VALUES((SELECT NVL(MAX(BPK),0)+1 FROM BUYPRODUCT),?,?,(SELECT MAX(SPK) FROM SERIAL))";
	private static final String UPDATE = "";
	private static final String DELETE = "";

	public ArrayList<BuyProductDTO> selectAll(BuyProductDTO bDTO) {
		ArrayList<BuyProductDTO> datas=new ArrayList<BuyProductDTO>();

		conn=JDBCUtil.connect();
		try {
			
			if(bDTO.getSearchCondition().equals("전체구매내역")) {
				//System.out.println("로그 1 전체 구매 내역 들어왔는지");
				pstmt=conn.prepareStatement(SELECTALL);
				pstmt.setString(1, bDTO.getMid());
				//System.out.println("mid : " + sDTO.getMid());
				
				ResultSet rs=pstmt.executeQuery();

				while(rs.next()) {
					
					//System.out.println("로그 2 rs.next 들어왔는지" );
					
					BuyProductDTO data=new BuyProductDTO();
					data.setBpk(rs.getInt("BPK"));
					data.setCnt(rs.getInt("CNT"));
					data.setSpk(rs.getInt("SPK"));
					data.setRegdate(rs.getDate("REGDATE"));
					data.setDeliveryAddress(rs.getString("DELIVERYADDRESS"));
					data.setPname(rs.getString("PNAME"));
					data.setPrice(rs.getInt("PRICE"));
					data.setImg(rs.getString("IMG"));
					data.setPpk(rs.getInt("PPK"));
					data.setReviewCheck(rs.getInt("REVIEWCHECK"));
					datas.add(data);
					
					//System.out.println("[로그 3] datas : " + datas);
				}
				
				rs.close();
				}
		
			else {
				pstmt=conn.prepareStatement(SELECTALL_RECENT);
				pstmt.setString(1, bDTO.getMid());
			

			ResultSet rs=pstmt.executeQuery();

			while(rs.next()) {
				BuyProductDTO data=new BuyProductDTO();
				data.setCnt(rs.getInt("CNT"));
				data.setSpk(rs.getInt("SPK"));
				data.setRegdate(rs.getDate("REGDATE"));
				data.setPname(rs.getString("PNAME"));
				data.setPrice(rs.getInt("PRICE"));
				data.setImg(rs.getString("IMG"));
				data.setPpk(rs.getInt("PPK"));
				datas.add(data);
			}
			
			rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}

		return datas;
	}
	
		public BuyProductDTO selectOne(BuyProductDTO bDTO) {
		BuyProductDTO data = null;

		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(SELECTONE);
			pstmt.setInt(1, bDTO.getBpk());

			ResultSet rs=pstmt.executeQuery();

			while(rs.next()) {
				data=new BuyProductDTO();
				data.setCnt(rs.getInt("CNT"));
				data.setSpk(rs.getInt("SPK"));
				data.setRegdate(rs.getDate("REGDATE"));
				data.setPname(rs.getString("PNAME"));
				data.setPrice(rs.getInt("PRICE"));
				data.setImg(rs.getString("IMG"));
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
	
	public boolean insert(BuyProductDTO bDTO) {
		conn=JDBCUtil.connect();
		try {
			pstmt=conn.prepareStatement(INSERT);
			pstmt.setInt(1, bDTO.getPpk());
			pstmt.setInt(2, bDTO.getCnt());
			
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
	
	private boolean update(BuyProductDTO bDTO) {
		
		return false;
	}
	private boolean delete(BuyProductDTO bDTO) {
		
		return false;
	}
	
	
}
