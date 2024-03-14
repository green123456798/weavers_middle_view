package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.ProductDTO;
import model.util.JDBCUtil;

public class ProductDAO {
   private Connection conn; // DB와의 연결을 담당
   private PreparedStatement pstmt; // CRUD 수행을 담당

//   private static final String SELECTALL_SALES = "SELECT PPK, PNAME, PRICE, IMG FROM PRODUCT ORDER BY SALES DESC";
   private static final String SELECTALL_SALES  = "SELECT P.PPK, P.PNAME, P.PRICE, P.IMG, CASE WHEN W.WPK IS NOT NULL THEN 1 ELSE 0 END AS HasWPK\r\n"
		   												+ " FROM PRODUCT P\r\n"
		   												+ " LEFT JOIN WISHLIST W ON\r\n"
		   												+ " P.PPK = W.PPK AND W.MID = ? \r\n"
		   												+ " ORDER BY P.SALES DESC";
   
//   private static final String SELECTALL_REGDATE = "SELECT PPK, PNAME, PRICE, IMG FROM PRODUCT ORDER BY REGDATE DESC, PPK DESC";
   private static final String SELECTALL_REGDATE = "SELECT P.PPK, P.PNAME, P.PRICE, P.IMG, CASE WHEN W.WPK IS NOT NULL THEN 1 ELSE 0 END AS HasWPK\r\n"
		   												+ " FROM PRODUCT P\r\n"
		   												+ " LEFT JOIN WISHLIST W \r\n"
		   												+ " ON P.PPK = W.PPK AND W.MID = ? \r\n"
		   												+ " ORDER BY REGDATE DESC, PPK DESC";
   
   
//   private static final String SELECTALL_NAME = "SELECT PPK, PNAME, PRICE, IMG FROM PRODUCT ORDER BY PNAME ASC";
//   private static final String SELECTALL_NAME = "SELECT P.PPK, P.PNAME, P.PRICE, P.IMG, CASE WHEN W.WPK IS NOT NULL THEN 1 ELSE 0 END AS HasWPK \r\n"
//		   												+ " FROM PRODUCT P\r\n"
//		   												+ " LEFT JOIN WISHLIST W \r\n"
//		   												+ " ON P.PPK = W.PPK AND W.MID = ?\r\n"
//		   												+ " ORDER BY P.PNAME ASC";
   
   private static final String SELECTALL_WISH = " SELECT ROWNUM, A.HasWPK, A.PPK, A.PNAME, A.PRICE, A.IMG, A.CNT\r\n"
		   											+ " FROM \r\n"
		   											+ " (SELECT CASE WHEN WPK IS NOT NULL THEN 1 ELSE 0 END AS HasWPK,\r\n"
		   											+ " P.PPK, P.PNAME,  P.PRICE, P.IMG, W.CNT \r\n"
		   											+ " FROM PRODUCT P \r\n"
		   											+ " LEFT OUTER JOIN (SELECT PPK, COUNT(PPK) CNT \r\n"
		   											+ " FROM WISHLIST GROUP BY PPK) W ON P.PPK = W.PPK  \r\n"
		   											+ " LEFT OUTER JOIN WISHLIST ON WISHLIST.PPK = P.PPK AND WISHLIST.MID = ?\r\n"
		   											+ " ORDER BY NVL(W.CNT, 0) DESC, P.PPK DESC) A\r\n"
		   											+ "	WHERE ROWNUM <= 8"; //8개만 찜 목록 보여줌 
         
   private static final String SELECTALL_WISH_WISH = "SELECT P.PPK,P.PNAME,P.PRICE,P.IMG, NVL(W.CNT, 0) AS WCNT, \r\n"
		   								+ " CASE WHEN MYW.WPK IS NOT NULL THEN 1 ELSE 0 END AS HasWPK FROM PRODUCT P\r\n"
   										+ " LEFT JOIN ( SELECT   PPK, COUNT(WPK) AS CNT FROM WISHLIST W GROUP BY  PPK) W \r\n"
   										+ " ON  P.PPK = W.PPK LEFT JOIN WISHLIST MYW\r\n"
   										+ " ON P.PPK = MYW.PPK  AND MYW.MID =? ORDER BY WCNT DESC,P.PPK DESC";
   
//   private static final String SELECTALL_SEARCH = "SELECT PPK, PNAME, PRICE,IMG FROM PRODUCT WHERE PNAME LIKE '%'||?||'%' ORDER BY PPK DESC";
   private static final String SELECTALL_SEARCH = "SELECT P.PPK, P.PNAME, P.PRICE, P.IMG, CASE WHEN W.WPK IS NOT NULL THEN 1 ELSE 0 END AS HasWPK \r\n"
		   										+ " FROM PRODUCT P \r\n"
		   										+ " LEFT JOIN WISHLIST W ON\r\n"
		   										+ " P.PPK = W.PPK AND W.MID = ?\r\n"
		   										+ " WHERE P.PNAME LIKE '%'||?||'%'\r\n"
		   										+ " ORDER BY P.PPK DESC";

   
   private static final String SELECTALL_LOWPRICE = "SELECT P.PPK,P.PNAME,P.PRICE,P.IMG, NVL(W.CNT, 0) AS WCNT, \r\n"
			+ "CASE WHEN MYW.WPK IS NOT NULL THEN 1 ELSE 0 END AS HasWPK FROM PRODUCT P\r\n"
			+ "LEFT JOIN ( SELECT   PPK, COUNT(WPK) AS CNT FROM WISHLIST W GROUP BY  PPK) W \r\n"
			+ "ON  P.PPK = W.PPK LEFT JOIN WISHLIST MYW\r\n"
			+ "ON P.PPK = MYW.PPK  AND MYW.MID =? ORDER BY P.PRICE ASC,P.PPK DESC";
   
//   private static final String SELECTONE_DETAIL = "SELECT PPK, PNAME, PRICE, DETAIL, IMG FROM PRODUCT WHERE PPK=?";
   private static final String SELECTONE_DETAIL = "SELECT P.PPK, P.PNAME, P.PRICE, P.DETAIL, P.IMG, CASE WHEN W.WPK IS NOT NULL THEN 1 ELSE 0 END AS HasWPK \r\n"
		   + " FROM PRODUCT P \r\n"
		   + " LEFT JOIN WISHLIST W ON\r\n"
		   + " P.PPK = W.PPK AND W.MID = ?\r\n"
		   + " WHERE P.PPK = ?";
   private static final String SELECTONE = "";

   private static final String INSERT = "";
   private static final String UPDATE = "UPDATE PRODUCT SET SALES = SALES + ? WHERE PPK = ? ";
   private static final String DELETE = "";

   public ArrayList<ProductDTO> selectAll(ProductDTO pDTO) {
      ArrayList<ProductDTO> datas = new ArrayList<ProductDTO>();
      conn = JDBCUtil.connect();
      try {
         if (pDTO.getSearchCondition().equals("판매순")) {
        	 pstmt = conn.prepareStatement(SELECTALL_SALES);   
        	 pstmt.setString(1, pDTO.getMid());
            }
            else if (pDTO.getSearchCondition().equals("신상순")) {
            	pstmt = conn.prepareStatement(SELECTALL_REGDATE);
            	pstmt.setString(1, pDTO.getMid());
            }
//            else if (pDTO.getSearchCondition().equals("이름순")) {
//            	pstmt = conn.prepareStatement(SELECTALL_NAME);
//               pstmt.setString(1, pDTO.getMid());

//            }
            	else if (pDTO.getSearchCondition().equals("추천순")) {
            		//System.out.println("로그 1 : controller에서 seatchCondition 잘 넘어오는지 ");
            		 pstmt = conn.prepareStatement(SELECTALL_WISH);
               pstmt.setString(1, pDTO.getMid());
            
         }else if(pDTO.getSearchCondition().equals("낮은가격순")) {
				pstmt = conn.prepareStatement(SELECTALL_LOWPRICE);
				pstmt.setString(1, pDTO.getMid());
				
			}else if(pDTO.getSearchCondition().equals("이름으로찾기")) {
            	  pstmt = conn.prepareStatement(SELECTALL_SEARCH);           
               pstmt.setString(1, pDTO.getMid());
               pstmt.setString(2, pDTO.getPname());
            
         }
         ResultSet rs = pstmt.executeQuery();
        
         while (rs.next()) {
            ProductDTO data = new ProductDTO();
            data.setPpk(rs.getInt("PPK"));
            data.setPname(rs.getString("PNAME"));
            data.setPrice(rs.getInt("PRICE"));
            data.setImg(rs.getString("IMG"));
            if(pDTO.getMid() != null) {
               data.setWish(rs.getInt("HasWPK"));                     // 로그인 상태
            }
            else {
               data.setWish(0);                                 // 로그아웃 상태
            }
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

   public ProductDTO selectOne(ProductDTO pDTO) {
         
      ProductDTO data = null;
         conn = JDBCUtil.connect();
         try {
             if (pDTO.getSearchCondition().equals("상세페이지")) {
            	 pstmt = conn.prepareStatement(SELECTONE_DETAIL);
                     pstmt.setString(1, pDTO.getMid());
                     pstmt.setInt(2, pDTO.getPpk());
                    
                }
               
               ResultSet rs = pstmt.executeQuery();
               
               if (rs.next()) {
                  data = new ProductDTO();
                  data.setPpk(rs.getInt("PPK"));
                  data.setPname(rs.getString("PNAME"));
                  data.setPrice(rs.getInt("PRICE"));
                  data.setDetail(rs.getString("DETAIL"));
                  data.setImg(rs.getString("IMG"));
                  if(pDTO.getMid() != null) {
                     data.setWish(rs.getInt("HasWPK"));
                  }
                  else {
                     data.setWish(0);
                  }
               }
               rs.close();
         }catch (SQLException e) {
            e.printStackTrace();
         } finally {
            JDBCUtil.disconnect(pstmt, conn);
         }
         return data;
   }
   private boolean insert(ProductDTO pDTO) {
      
      return false;
   }
   public boolean update(ProductDTO pDTO) {
	   conn=JDBCUtil.connect();
		try {
				pstmt=conn.prepareStatement(UPDATE);
				pstmt.setInt(1, pDTO.getSales());
				pstmt.setInt(2, pDTO.getPpk());
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
   private boolean delete(ProductDTO pDTO) {
      
      return false;
   }

}