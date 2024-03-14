package controller.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.dao.ProductDAO;
import model.dto.ProductDTO;

//----- 상품목록출력 -----
@WebServlet("/productList.asy")
public class ProductList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductList() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		ProductDTO pDTO = new ProductDTO();							// ProductDTO 객체 생성
		ProductDAO pDAO = new ProductDAO();							// ProductDAO 객체 생성
		
		HttpSession session = request.getSession();					// HttpSession 객체를 통해 세션 관리
		
		String searchCondition = request.getParameter("param");		// 파라미터값을 검색 조건에 저장
		
		if(searchCondition.equals("인기순")) {
			searchCondition = "판매순";
		}
		
		pDTO.setSearchCondition(searchCondition);					// pDTO에 검색조건 저장
		pDTO.setMid((String)session.getAttribute("sessionMid"));	// pDTO에 세션ID 저장 (로그인/로그아웃 상태 확인 > 찜)
		
		ArrayList<ProductDTO> datas = pDAO.selectAll(pDTO);			// selectAll()을 통해 DB에서 가져온 상품목록을 datas에 저장

		response.setContentType("application/json");				// 전송하는 데이터가 json 형식임을 클라이언트에게 알려줌
		response.setCharacterEncoding("UTF-8");
		
		try (PrintWriter out = response.getWriter()) {				// PrintWriter 선언 및 초기화 (응답 전송)
			if (datas == null) {									// 데이터가 없는 경우,
				out.print("[]");									// 빈 배열 반환
			} 
			else {													// 데이터가 있는 경우,
				Gson gson = new Gson();								// Gson 라이브러리로 Gson 객체 생성
				String json = gson.toJson(datas);					// datas 객체를 json 문자열로 변환
				out.print(json);									// json 문자열을 응답
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
